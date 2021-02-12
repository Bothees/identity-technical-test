package com.identity.test.runners;


import com.identity.test.helpers.BasePage;
import com.identity.test.helpers.InputFileReader;
import com.identity.test.helpers.OutputFileReader;
import com.identity.test.helpers.TestContext;
import com.identity.test.page_objects.FreeCarCheckPage;
import com.identity.test.page_objects.ResultsPage;
import io.cucumber.core.gherkin.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.testng.CucumberOptions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class RunTestSuite extends BasePage {

    private FreeCarCheckPage freeCarCheckPage = PageFactory.initElements(BasePage.driver,FreeCarCheckPage.class);
    private static BasePage driver = new BasePage();
    InputFileReader inputFileReader = new InputFileReader();
    TestContext testContext = TestContext.getInstance();
    ResultsPage resultsPage = new ResultsPage();
    OutputFileReader outputFileReader = new OutputFileReader();

    @Test
    public void testRunner() throws IOException {
        List<String> regNumbers = inputFileReader.readInputFile();
        List<Map<String,String>> actualResults= new ArrayList<>();


        for(String number: regNumbers){
            getDriver().navigate().to(testContext.getProperty("base.url"));
            freeCarCheckPage.searchRegistrationNumber(number);
            freeCarCheckPage.clickFreeCarCheckButton();
            if(isAlertPresent()){
                String alertText = BasePage.driver.switchTo().alert().getText();
                BasePage.driver.switchTo().alert().accept();
            }else {
                actualResults.add(resultsPage.getResults());
            }
        }
        List<Map<String,String>> expectedResults = outputFileReader.readOutputFile();
        for (int i=expectedResults.size()-1; i>=0 ;i--) {
            if (actualResults.get(i).containsKey("Registration")) {
                assertThat("Registration number is not found ", actualResults.get(i).get("Registration"), is(expectedResults.get(i).get("Registration".toUpperCase())));
            }
            if (actualResults.get(i).containsKey("Make")) {
                assertThat("Make  is not found ", actualResults.get(i).get("Make"), is(expectedResults.get(i).get("Make").toUpperCase()));
            }
            if (actualResults.get(i).containsKey("Model")) {
                assertThat("Model is not found ", actualResults.get(i).get("Model"), is(expectedResults.get(i).get("Model").toUpperCase()));
            }
            if (actualResults.get(i).containsKey("Colour")) {
                assertThat("Colour is not found ", actualResults.get(i).get("Colour"), is(expectedResults.get(i).get("Colour").toUpperCase()));
            }
            if (actualResults.get(i).containsKey("Year")) {
                assertThat("Year is not found ", actualResults.get(i).get("Year"), is(expectedResults.get(i).get("Year").toUpperCase()));
            }
        }
    }

    @BeforeClass
    public static void setupDriver(){
        driver.initialiseDriver();
    }

   @AfterClass
    public static void destroyDriver(){
        if(BasePage.driver != null){
            BasePage.driver.quit();
            BasePage.driver = null;
        }
    }

}
