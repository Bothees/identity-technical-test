package com.identity.test.page_objects;

import com.identity.test.helpers.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResultsPage extends BasePage {

    public Map<String,String> getResults() {
        Map<String, String> output = new HashMap<>();
        String locator = "//div[5]/div[1]/div/span/div[2]/dl[%d]";
        for (int i = 1; i < 6; i++) {
            WebElement element = BasePage.driver.findElement(By.xpath(String.format(locator, i)));
            output.put(element.findElement(By.tagName("dt")).getText(), element.findElement(By.tagName("dd")).getText());
            System.out.println(element.findElement(By.tagName("dt")).getText());
            System.out.println(element.findElement(By.tagName("dd")).getText());
        } return output;
    }
}