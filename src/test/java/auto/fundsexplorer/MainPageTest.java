package auto.fundsexplorer;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MainPageTest {

    private FirefoxDriver driver;
    private MainPage fundsExplorer;

    @BeforeTest
    public void loadPage() {
        driver = startDriver();
        driver.navigate().to("https://www.fundsexplorer.com.br/ranking");
    }

    @AfterTest
    public void quitPage(){
        driver.quit();
    }

    private String trataMoeda(String valor){
        valor = valor.replace("R$","");
        valor = valor.replace("\\.","");
        return valor;
    }
    private String trataPercentual(String valor){
        valor = valor.replaceAll("%","").replace(",",".");
        valor = String.valueOf(Double.parseDouble(valor)/100);
        valor = valor.replaceAll("\\.", ",");
        return valor;
    }

    /*

    0-FUNDOS *
    1-SETOR *
    2-PREÇO ATUAL (R$) *
    3-LIQUIDEZ DIÁRIA (R$) *
    4-P/VP
    5-ÚLTIMO DIVIDENDO *
    6-DIVIDEND YIELD
    7-DY (3M) ACUMULADO
    8-DY (6M) ACUMULADO
    9-DY (12M) ACUMULADO *
    10-DY (3M) MÉDIA
    11-DY (6M) MÉDIA
    12-DY (12M) MÉDIA
    13-DY ANO
    14-VARIAÇÃO PREÇO
    15-RENTAB. PERÍODO
    16-RENTAB. ACUMULADA
    17-PATRIMÔNIO LÍQUIDO *
    18-VPA
    19-P/VPA *
    20-DY PATRIMONIAL
    21-VARIAÇÃO PATRIMONIAL
    22-RENTAB. PATR. PERÍODO
    23-RENTAB. PATR. ACUMULADA
    24-VACÂNCIA FÍSICA *
    25-VACÂNCIA FINANCEIRA *
    26-QUANT. ATIVOS *
     */

    private String tratarValor(String valor, int coluna, int linha){
        String valorFinal = valor;
        valorFinal = valorFinal.replace("N/A", "0");
        if(linha>=0){
            Boolean moeda = ((coluna == 2)||(coluna == 3)||(coluna == 17));
            Boolean percentual = (((coluna >= 6)&&(coluna <= 16))||((coluna >= 20)&&(coluna <= 23)));
            if(moeda)valorFinal = trataMoeda(valorFinal);
            if(percentual)valorFinal = trataPercentual(valorFinal);
        }
        return valorFinal;
    }


    @Test
    public void login() throws IOException {
        FileWriter fw = new FileWriter("C:/Temp/fii_resumo.txt", false);
        FileWriter fw2 = new FileWriter("C:/Temp/fii.txt", false);
        fundsExplorer = new MainPage(driver);
        while(!fundsExplorer.link.isDisplayed()){
            System.out.println("Aguardando a tabela carregar!");
        }

        AtomicReference<String> linhaOut = new AtomicReference<>("");
        AtomicReference<String> linhaOut2 = new AtomicReference<>("");
        if(fundsExplorer.link.isDisplayed()){
            if(fundsExplorer.tableRankingContainer.isDisplayed()){
                //tbody
                WebElement tbody = fundsExplorer.tableRankingContainer.findElement(By.xpath("//tbody"));
                if(tbody.isDisplayed()){
                    List<WebElement> colunas =  tbody.findElements(By.xpath("//td"));
                    AtomicInteger linha = new AtomicInteger(0);
                    AtomicInteger j = new AtomicInteger();
                    colunas.forEach(coluna -> {
                        //Resumo
                        if((j.get() ==0)||(j.get() ==1)||(j.get() ==2)||(j.get() ==3)||(j.get() ==5)||(j.get() ==9)||(j.get() ==17)||(j.get() ==19)||(j.get() ==24)||(j.get() ==25)||(j.get() ==26))
                            linhaOut.set(linhaOut.get().concat(tratarValor(coluna.getText(), j.get(), linha.get())));

                        //Completo
                        linhaOut2.set(linhaOut2.get().concat(tratarValor(coluna.getText(), j.get(), linha.get())));

                        if(j.get() ==26){
                            linhaOut.set(linhaOut.get().concat("\n"));
                            linhaOut2.set(linhaOut2.get().concat("\n"));
                            j.set(0);
                            linha.getAndIncrement();
                        }else{
                            linhaOut.set(linhaOut.get().concat("\t"));
                            linhaOut2.set(linhaOut2.get().concat("\t"));
                            j.getAndIncrement();
                        }
                    });
                }
            }
        }
        fw.write(linhaOut.get());
        fw.flush();
        fw.close();

        fw2.write(linhaOut2.get());
        fw2.flush();
        fw2.close();
    }


    private FirefoxDriver startDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.setImplicitWaitTimeout(Duration.ofSeconds(30));
        return new FirefoxDriver(options);
    }
}
