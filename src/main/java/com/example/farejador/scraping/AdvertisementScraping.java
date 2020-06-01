package com.example.farejador.scraping;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.farejador.Advertisemens;
import com.example.farejador.WebDriverControl;
import com.example.farejador.models.Ape;
import com.example.farejador.repository.ApeRepository;

@Component
public class AdvertisementScraping {

    private static final String CEP = "//*[@id=\"content\"]/div[2]/div/div[2]/div[1]/div[30]/div/div[1]/div[2]/div[1]/div/dd";
    private static final String VALOR = "//*[@id=\"content\"]/div[2]/div/div[2]/div[1]/div[4]/div/div[1]/div/h2";
    private static final String DESCRICAO = "//*[@id=\"content\"]/div[2]/div/div[2]/div[1]/div[6]/h1";

    private final WebDriverControl webDriverControl;
    private final Advertisemens advertisemens;
    private final ApeRepository apeRepository;

    @Autowired
    public AdvertisementScraping(WebDriverControl webDriverControl, Advertisemens advertisemens, ApeRepository apeRepository) {
        this.webDriverControl = webDriverControl;
        this.advertisemens = advertisemens;
        this.apeRepository = apeRepository;
    }

    public void execute() throws Exception {
        int cont = 0;

        System.out.println("TOTAL LINKS :" + advertisemens.getLinks().size());

//        List<String> linksMock = Arrays.asList("https://sc.olx.com.br/florianopolis-e-regiao/imoveis/apartamento-para-alugar-com-3-dormitorios-em-ingleses-florianopolis-cod-14212-660701611",
//                "https://sc.olx.com.br/florianopolis-e-regiao/imoveis/apartamento-com-2-dormitorios-para-aluguel-temporada-80-m-por-r-300-dia-ingleses-fl-637407660",
//                "https://sc.olx.com.br/florianopolis-e-regiao/imoveis/cobertura-temporada-com-vista-mar-para-8-pessoas-ingleses-florianopolis-sc-658007133",
//                "https://sc.olx.com.br/florianopolis-e-regiao/imoveis/apto-impecavel-aluguel-anual-fica-na-dante-de-pata-a-500-metros-da-praia-ingleses-750839231");

        for (String link : advertisemens.getLinks()) {
//        for (String link : linksMock) {
            cont++;
            System.out.println("NUMERO: " + cont);
            webDriverControl.getDriver().get(link);
            Thread.sleep(3000);

            try {
                saveApe(link);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveApe(String link) {
        Ape ape = new Ape();
        ape.setLink(link);

        if (filter()) {
            ape.setFavoriteSystem(true);
        }

        apeRepository.save(ape);
    }

    private boolean filter() {
        return filterCep() && filterValueMoney() && filterDescription();
    }

    private boolean filterCep() {
        String cep = webDriverControl.getDriver().findElement(By.xpath(CEP)).getText();
        int lastDigitsCep = Integer.parseInt(cep.substring(5, 8));

        return lastDigitsCep >= 500 && lastDigitsCep <= 585;
    }

    private boolean filterValueMoney() {
        String valueMoney = webDriverControl.getDriver().findElement(By.xpath(VALOR)).getText();
        String value = valueMoney.replace("R$ ", "").replace(".", "");
        return Integer.parseInt(value) > 1000;
    }

    private boolean filterDescription() {
        String description = webDriverControl.getDriver().findElement(By.xpath(DESCRICAO)).getText();
        return !description.contains("temporada") &&
               !description.contains("TEMPORADA") &&
               !description.contains("Temporada");
    }
}
