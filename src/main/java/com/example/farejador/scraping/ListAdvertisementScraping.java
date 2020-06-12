package com.example.farejador.scraping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.farejador.enums.IntentionEnum;
import com.example.farejador.models.Neighborhood;
import com.example.farejador.models.Region;
import com.example.farejador.repository.ApeRepository;

@Component
public class ListAdvertisementScraping {

    private static final String HOST = "https://www.olx.com.br/";

    private static final String BOTAO_IMOVEIS = "//*[@id=\"___gatsby\"]/div[4]/div[1]/div[2]/div[2]/div/div/ul/li[1]/a/span/img";
    private static final String LINK_DDD_48 = "//*[@id=\"content\"]/div/div[2]/div[1]/div[2]/div/ul[1]/li[2]/a";
    private static final String BOTAO_FILTRAR_POR_BAIRROS = "//*[@id=\"content\"]/div/div[2]/div[1]/div[2]/div/a";
    private static final String BOTAO_FILTRAR_NA_MODAL = "/html/body/div[2]/div/div/div/a[1]";
    private static final String LISTA_DE_ANUNCIOS = "//*[@id=\"ad-list\"]";
    private static final String PROXIMA_PAGINA = "//*/a[text()='Próxima pagina']";
    private static final String ULTIMA_PAGINA = "//*/a[text()='Última pagina']";
    private static final String LISTA_PAGINACAO = "//*[@id=\"content\"]/div/div[2]/div[12]/ul";
    private static final String LI_ANUNCIO = "//*[@id=\"ad-list\"]/li[%s]/a";

    private final WebDriverControl webDriverControl;
    private final Pagination pagination;
    private final ApeRepository apeRepository;

    @Autowired
    public ListAdvertisementScraping(WebDriverControl webDriverControl, Pagination pagination, ApeRepository apeRepository) {
        this.webDriverControl = webDriverControl;
        this.apeRepository = apeRepository;
        this.pagination = pagination;
    }

    public void execute(Region region, IntentionEnum intentionEnum, Advertisemens advertisemens) throws Exception {
        webDriverControl.getDriver().get(HOST);
        Thread.sleep(1000);

        isVisibilityandClick(BOTAO_IMOVEIS);
        isVisibilityandClick(intentionEnum.getXpath());
        isVisibilityandClick(LINK_DDD_48);
        isVisibilityandClick(region.getXpath());
        isVisibilityandClick(BOTAO_FILTRAR_POR_BAIRROS);

        Thread.sleep(2000);
        selectNeighborhood(region.getNeighborhoods());
        Thread.sleep(2000);

        isVisibilityandClick(BOTAO_FILTRAR_NA_MODAL);

        setLastPage();
        Thread.sleep(2000);
        createListAdvertisemens(advertisemens);
    }

    private void selectNeighborhood(List<Neighborhood> neighborhood) throws InterruptedException {
        List<String> xpaths = neighborhood.stream()
                .map(Neighborhood::getXpath).collect(Collectors.toList());

        for (String xpath : xpaths) {
            Thread.sleep(2000);
            webDriverControl.getDriver()
                    .findElement(By.xpath(xpath))
                    .click();
        }
    }

    private void isVisibilityandClick(String xpath) throws InterruptedException {
        webDriverControl.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        webDriverControl.getDriver().findElement(By.xpath(xpath)).click();
        Thread.sleep(2000);
    }

    private void setLastPage() throws InterruptedException {
        Thread.sleep(2000);
        webDriverControl.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ULTIMA_PAGINA)));
        webDriverControl.getDriver().findElement(By.xpath(ULTIMA_PAGINA)).click();
        Thread.sleep(2000);

        WebElement ulPagination = webDriverControl.getDriver().findElement(By.xpath(LISTA_PAGINACAO));
        List<WebElement> elementsPagination = ulPagination.findElements(By.tagName("li"));

        int indexOfLastPage = elementsPagination.size() - 2;
        int indexOfFirstPage = elementsPagination.size() - 1;

        String lastPage = elementsPagination.get(indexOfLastPage).getText();

        pagination.setLastPage(Integer.valueOf(lastPage));
        elementsPagination.get(indexOfFirstPage).click();
    }

    private void createListAdvertisemens(Advertisemens advertisemens) throws InterruptedException {
        List<String> linksAdvertisemens = new ArrayList<>();

        for (int page = 1; page <= pagination.getLastPage(); page++) {
            List<WebElement> advertisemensWebElement = findAdvertisemens();
            populateAdvertisemensLinks(linksAdvertisemens, advertisemensWebElement);
            toNextPage(page);
        }

        advertisemens.setLinks(linksAdvertisemens);
    }

    private void populateAdvertisemensLinks(List<String> linksAdvertisemens, List<WebElement> advertisemens) {
        for (int i = 1; i <= advertisemens.size(); i++) {
            try {
                String linkAdvertisemen = findLinkAdvertisemen(i);
                if (!apeRepository.existsByLink(linkAdvertisemen)) {
                    linksAdvertisemens.add(linkAdvertisemen);
                }
            } catch (NoSuchElementException ignored) {
            }
        }
    }

    private String findLinkAdvertisemen(int i) {
        String format = String.format(LI_ANUNCIO, i);
        return webDriverControl.getDriver().findElement(By.xpath(format)).getAttribute("href");
    }

    // Busca lista de anuncios e coloca em uma lista de WebElement
    private List<WebElement> findAdvertisemens() throws InterruptedException {
        Thread.sleep(2000);
        WebElement ulListdvertisemens = webDriverControl.getDriver().findElement(By.xpath(LISTA_DE_ANUNCIOS));
        List<WebElement> advertisemensElement = ulListdvertisemens.findElements(By.tagName("li"));
        Thread.sleep(2000);
        return advertisemensElement;
    }


    private void toNextPage(int page) throws InterruptedException {
        Thread.sleep(2000);
        if (page != pagination.getLastPage()) {
            webDriverControl.getDriver().findElement(By.xpath(PROXIMA_PAGINA)).click();
        }
    }
}
