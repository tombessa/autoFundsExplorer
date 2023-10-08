package auto.fundsexplorer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
  @FindBy(xpath = "//table")
  public WebElement tableRankingContainer;


  @FindBy(xpath = "//tbody")
  public WebElement tbody;

  @FindBy(xpath = "//tr")
  public List<WebElement> tr;

  @FindBy(xpath = "//td")
  public List<WebElement> td;

  @FindBy(xpath = "//a[@href='/aazq11']")
  public WebElement link;



  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}