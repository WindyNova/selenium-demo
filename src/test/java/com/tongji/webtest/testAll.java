package com.tongji.webtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author
 * @Date 2022/11/8
 * @Description
 */
public class testAll {
    private static WebDriver driver;
    private String projectName;

    @BeforeClass
    public void beforeClass() throws InterruptedException {

       String localGeckoDriverPath = "D:\\Share\\geckodriver.exe"; //新版火狐浏览器需要GeckoDriver
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe"); //指定firefox的路径
        System.setProperty("webdriver.gecko.driver" , localGeckoDriverPath);
        driver = new FirefoxDriver(options);
        driver.manage().timeouts()
                .implicitlyWait(30, TimeUnit.SECONDS);
    }


    @AfterTest  // make sure this runs!
    public void closeBrowser()
    {
        driver.quit();
    }

        @Test
        public void testAll() throws Exception {

            driver.get("http://192.168.87.129/testlink/login.php"); //测试的网站
        Assert.assertEquals("Login",driver.getTitle());


    }

    @Test(dependsOnMethods = "testAll")
    public void testCreateProject() throws InterruptedException {
        driver.findElement(By.id("tl_login")).clear();
        driver.findElement(By.id("tl_login")).sendKeys("admin");
        driver.findElement(By.id("tl_password")).clear();
        driver.findElement(By.id("tl_password")).sendKeys("admin");
        driver.findElement(By.id("tl_login_button")).click();
        Thread.sleep(3000);
        driver.switchTo().frame("mainframe"); //此步必做，因为有iframe，切换frame让Selenium检测元素

        driver.findElement(By.name("tprojectName")).clear();
        projectName = "Project" + (new Random().nextInt(10000));
        driver.findElement(By.name("tprojectName")).sendKeys(projectName);
        driver.findElement(By.name("tcasePrefix")).clear();
        driver.findElement(By.name("tcasePrefix")).sendKeys(projectName);
        driver.findElement(By.name("doActionButton")).click();

    }
    @Test(dependsOnMethods="testCreateProject")
    public void testDeleteProject() throws InterruptedException {
        driver. get("http://192.168.87.129/testlink/lib/project/projectView.php");
        Thread. sleep(3000);
        String xpathDeleteButton = "/html/body/div/div[2]/form/div/table/tbody/tr/td[9]/img";
        driver.findElement( By.xpath(xpathDeleteButton)).click();
        Thread.sleep(1000);
        driver.findElement (By.id("ext-gen20")). click();


    }

}
