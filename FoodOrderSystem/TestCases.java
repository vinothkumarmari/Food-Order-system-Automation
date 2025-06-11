import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

import java.io.FileReader;
import com.opencsv.CSVReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TestCases{
    public static void main(String[] args) throws InterruptedException{

        Scanner scanner = new Scanner(System.in);
        //System.setProperty("webdriver.gecko.driver", "E:\\selinium\\Drivers\\geckodriver.exe");
        //WebDriver driverFirefox = new FirefoxDriver();

        //System.setProperty("webdriver.edge.driver", "E:\\selinium\\Drivers\\msedgedriver.exe");
        //WebDriver driverEdge = new EdgeDriver();

        System.setProperty("webdriver.chrome.driver", "E:\\selinium\\Drivers\\chromedriver.exe");
        WebDriver driverChrome = new ChromeDriver();
        

        //WorkSimultenously t1 = new WorkSimultenously(driverEdge);
        //t1.start();
        //t1.join();
        WorkSimultenously t3 = new WorkSimultenously(driverChrome);
        t3.start();
        t3.join();
        //WorkSimultenously t2 = new WorkSimultenously(driverFirefox);
        //t2.start();
        //t2.join();
    }
}


class WorkSimultenously extends Thread{
    WebDriver driver;
    WorkSimultenously(WebDriver driver){
        this.driver = driver;
    }
    
    public void run() {
        driver.get("http://localhost:8082");
        // driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS) ;
		driver.manage().window().maximize();
        CSVReader csvReader;


        // ------------------------SignUp Testcases check----------------------------------------------------------------

        // System.out.println("\n\n======================================================SignUp Testcase Details============================================\nTestcase Description -> To check user should use the valid credentials for sign up.");
        
        
        // WebElement SignUpBtnLocator = driver.findElement(By.xpath("//*[@id='sign-up-btn']"));
        // WebElement phoneNumberLocator = driver.findElement(By.xpath("/html/body/div/div[1]/div/form[2]/div[1]/input"));
        // WebElement usernameLocator = driver.findElement(By.xpath("/html/body/div/div[1]/div/form[2]/div[2]/input"));
        // WebElement emailLocator = driver.findElement(By.xpath("/html/body/div/div[1]/div/form[2]/div[3]/input"));
        // WebElement passwordLocator = driver.findElement(By.xpath("/html/body/div/div[1]/div/form[2]/div[4]/input"));
        
        // try {
        //     csvReader = new CSVReader(new FileReader("E:\\selinium\\FoodOrderSystem\\SignUp.csv"));
        //     String[] csvCell;
        //     int i=0, ignore=0;
        //     while((csvCell = csvReader.readNext()) != null){
        //         if(ignore!=0){
        //             try {
        //                 Thread.sleep(1000);
        //                 SignUpBtnLocator.click();
        //                 Thread.sleep(1000);
        //                 phoneNumberLocator.sendKeys(csvCell[0]);
        //                 Thread.sleep(1000);
        //                 usernameLocator.sendKeys(csvCell[1]);
        //                 Thread.sleep(1000);
        //                 emailLocator.sendKeys(csvCell[2]);
        //                 Thread.sleep(1000);
        //                 passwordLocator.sendKeys(csvCell[3] + Keys.ENTER);   
        //                 Thread.sleep(1000);
                
        //                 driver.findElement(By.xpath("//*[@id='sign-up-btn']"));
        //                 System.out.println("\n**********(SIGN" + i +")*************\nTest case credentials :--\n Phone Number : " + csvCell[0] + "\n Username : " + csvCell[1] + "\n Email : " + csvCell[2] + "\n Password : " + csvCell[3] + "\nTest case Result : Pass, Successfully SignedUp\n");
                        
        //             }catch(NoSuchElementException e){
        //                 System.out.println("\n**********(SIGN" + i +")*************\nTest case credentials :--\n Phone Number : " + csvCell[0] + "\n Username : " + csvCell[1] + "\n Email : " + csvCell[2] + "\n Password : " + csvCell[3] + "\nTest case Result : Fail, Redirected to the error page\n");
        //                 WebElement ErrorPage = driver.findElement(By.xpath("/html/body/center/a"));
        //                 ErrorPage.click();
                        
        //             }catch (InterruptedException e) {
        //                 System.err.println(e);
        //             }
        //         }
        //         SignUpBtnLocator = driver.findElement(By.xpath("//*[@id='sign-up-btn']"));
        //         phoneNumberLocator = driver.findElement(By.xpath("/html/body/div/div[1]/div/form[2]/div[1]/input"));
        //         usernameLocator = driver.findElement(By.xpath("/html/body/div/div[1]/div/form[2]/div[2]/input"));
        //         emailLocator = driver.findElement(By.xpath("/html/body/div/div[1]/div/form[2]/div[3]/input"));
        //         passwordLocator = driver.findElement(By.xpath("/html/body/div/div[1]/div/form[2]/div[4]/input"));
        //         ignore++;
        //         i++;            
        //     }
        // }catch(Exception e){
        //    System.out.println(e);
        // }
        // System.out.println("\n======================================================================================================================");


        // ------------------------Login Testcases check----------------------------------------------------------------
        
		
        System.out.println("\n\n======================================================Login Testcase Details============================================\nTestcase Description -> To check the User should use the correct/valid credentials for login.");
        
		WebElement usernameLocator1 = driver.findElement(By.name("username"));
        WebElement passwordLocator1 = driver.findElement(By.name("password"));
        try {
            csvReader = new CSVReader(new FileReader("E:\\selinium\\FoodOrderSystem\\Login.csv"));
            String[] csvCell;
            int user=0;
            int Admin=0, ignore=0;
            while((csvCell = csvReader.readNext()) != null){
                if(ignore!=0){
                    try {
                        Thread.sleep(1000);
                        usernameLocator1.sendKeys(csvCell[0]);
                        Thread.sleep(1000);
                        passwordLocator1.sendKeys(csvCell[1] + Keys.ENTER);
                        Thread.sleep(1000);
                        
                        WebElement UserLogout = driver.findElement(By.xpath("/html/body/h4/a"));

                        System.out.println("\n**********(USL" + user++ +")*************\nTest case credentials :--\n Username : " + csvCell[0] + "\n Password : " + csvCell[1] + "\nTest case Result : Pass, User Successfully Redirected to the Restaurant page\n");
                        // testRestaurant(driver);
                        UserLogout = driver.findElement(By.xpath("/html/body/h4/a"));
                        UserLogout.click();
                    
                    }catch(NoSuchElementException e){  
                        try {
                            WebElement AdminLogout = driver.findElement(By.xpath("/html/body/a"));

                            System.out.println("\n**********(ADL" + Admin++ +")*************\nTest case credentials :--\n Username : " + csvCell[0] + "\n Password : " + csvCell[1] + "\nTest case Result : Pass, Admin Successfully Redirected to the Admin page\n");
                            // adminWorkTest(driver);
                            AdminLogout = driver.findElement(By.xpath("/html/body/a"));
                            AdminLogout.click();

                        } catch (NoSuchElementException e1) {      
                            System.out.println("\n**********(USL" + user++ +")*************\nTest case credentials :--\n Username : " + csvCell[0] + "\n Password : " + csvCell[1] + "\nTest case Result : Fail, Redirected to the error page\n");
                            WebElement ErrorPage = driver.findElement(By.xpath("/html/body/center/a"));
                            ErrorPage.click();
                        }
                    }catch (InterruptedException e) {
                        System.err.println(e);

                    }
                }
                usernameLocator1 = driver.findElement(By.name("username"));
                passwordLocator1 = driver.findElement(By.name("password"));
                ignore++;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        
        System.out.println("\n======================================================================================================================");
        
        driver.quit();
        System.out.println("Driver closed");
    }

    // Restaurant page Test --------------------------------------------------
	
	public void testRestaurant(WebDriver driver){
        CSVReader csvReader;
        WebElement searchRestaurantLocator = driver.findElement(By.xpath("//*[@id='myInput']"));
        
		try {
            csvReader = new CSVReader(new FileReader("E:\\selinium\\FoodOrderSystem\\SearchRestaurant.csv"));
            String[] csvCell;
            int ignore=0;
            while((csvCell = csvReader.readNext()) != null){
                if(ignore!=0){
                    try {
                        Thread.sleep(1000);
                        searchRestaurantLocator.sendKeys(csvCell[0]);
                        WebElement openRestaurentLocator = driver.findElement(By.xpath("//*[@id='container1']/div/a"));
                        Thread.sleep(2000);
                        openRestaurentLocator.click();
                        System.out.println("\nResult : Pass (" + csvCell[0] + ") : Redirected to the Food Page\n");
                        // testFoodPage(driver);
                        Thread.sleep(1000);
                        driver.navigate().back();
                        driver.findElement(By.xpath("//*[@id='myInput']")).clear();
                    
                    }catch (InterruptedException | NoSuchElementException e) {
    
                        searchRestaurantLocator.clear();
                        System.out.println("\nResult : Fails (" + csvCell[0] + ") : Restaurant not found\n");
                    }
                }
                ignore++;
                searchRestaurantLocator = driver.findElement(By.xpath("//*[@id='myInput']"));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
	}	

    // Foodpage test --------------------------------------------------------------

    public void testFoodPage(WebDriver driver){
        try {
            Thread.sleep(2000);
            List<WebElement> foodList = driver.findElements(By.xpath("//*[@id='button1']"));
            for (WebElement webElement : foodList){
                webElement.click();
            }
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='checkout']/button")).click();
            Thread.sleep(2000);
            System.out.println("\nFood order test : Pass (Bill generated..)");
            driver.findElement(By.xpath("/html/body/a")).click();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // Order details page test in the Admin Page ------------------------------------------------------

    public void adminWorkTest(WebDriver driver){
        WebElement adminOrderDetailsLocator = driver.findElement(By.xpath("/html/body/section/a[2]"));
        try {
            Thread.sleep(2000);
            adminOrderDetailsLocator.click();
            System.out.println("\nFood ordered Details shown : Pass(Order Lists Displayed...)");
            Thread.sleep(2000);
            driver.navigate().back();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}