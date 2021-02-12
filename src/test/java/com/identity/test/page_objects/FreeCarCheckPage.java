package com.identity.test.page_objects;

import com.identity.test.helpers.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class FreeCarCheckPage extends BasePage {

    public void searchRegistrationNumber(String regNumber) {
        BasePage.driver.findElement(By.cssSelector("input[id='vrm-input']")).sendKeys(regNumber);
    }

    public void clickFreeCarCheckButton(){
        BasePage.driver.findElement(By.tagName("button")).click();
    }

}
