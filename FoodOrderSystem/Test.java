import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.JavascriptExecutor;

import java.io.*;
import java.sql.*;

import com.opencsv.CSVReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Test{
	
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
    static int foodPageID = 0, RestaurantID = 0, OrderDeatilsAdmin = 0;
    WorkSimultenously(WebDriver driver){
        this.driver = driver;
    }

    static Connection con = null;
    PreparedStatement ps;
    ResultSet rs;
    static{
        try {
            Class.forName("org.postgresql.Driver");
            String url="jdbc:postgresql://localhost:4321/foodordersystem";
            String user="postgres";
            String pass="Vinoth@45";
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.err.println(e);
        } 
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("E:/selinium/FoodOrderSystem/TESTLOG.html", true));
            out.write("<html><head><style>td, th{align-items:center; padding : 5px;}th{background-color:black; color:white; font-size:18px;}</style></head><body><center><table border=2><tr><th>ID</th><th>Scenario</th><th>Description</th><th>Test Inputs</th><th>ExpectedOutput</th><th>ActualOutput</th><th>Result</th></tr><tr>");
            out.close();
         }
         catch (IOException e) {
            System.out.println(e);
         }
    }
    
    public void run() {
        driver.get("http://localhost:8082");
        // driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS) ;
		driver.manage().window().maximize();
		JavascriptExecutor js = (JavascriptExecutor)driver;
        CSVReader csvReader;


        // ------------------------SignUp Testcases check----------------------------------------------------------------

        // System.out.println("\n\n======================================================SignUp Testcase Details============================================\nTestcase Description -> To check user should use the valid credentials for sign up.");
        
		String SignUpRedirect = "document.querySelector(\"#sign-up-btn\").click()";
		String SignInRedirect = "document.querySelector(\"#sign-in-btn\").click()";
		String PhoneSignUp = "document.querySelector(\"[name='phone']\")";
		String UserNameSignUp = "document.querySelectorAll(\"[name='username']\")[1]";		
        String EmailSignUp = "document.querySelector(\"[name='email']\")";
		String passwordSignUp = "document.querySelectorAll(\"[name='password']\")[1]";
		String signUp = "document.querySelectorAll(\"[type='submit']\")[1].click()";
		String error = "document.querySelector(\"a[href='/test/index.jsp']\").click()";
		String actualOutput = "User redirected to the error page";
        try {
            csvReader = new CSVReader(new FileReader("E:\\selinium\\FoodOrderSystem\\SignUp.csv"));
            String[] csvCell;
            int i=0, ignore=0;
            while((csvCell = csvReader.readNext()) != null){
                if(ignore!=0){
					String appendPhone = PhoneSignUp + ".value=\"" + csvCell[0] + "\";";
					String appendUser = UserNameSignUp + ".value=\"" + csvCell[1] + "\";";
					String appendEmail = EmailSignUp + ".value=\"" + csvCell[2] + "\";";
					String appendPass = passwordSignUp + ".value=\"" + csvCell[3] + "\";";
                    String[] cred = {"Phone : " + csvCell[0] + ",  " + "Username : " + csvCell[1] + ",  " + "Email : " + csvCell[2] + ",  " + "Password : " + csvCell[3]};
                    try {
                        Thread.sleep(1000);
                        js.executeScript(SignUpRedirect);
						
                        Thread.sleep(1000);
						js.executeScript(appendPhone);
                        Thread.sleep(1000);
						js.executeScript(appendUser);
                        Thread.sleep(1000);
						js.executeScript(appendEmail);
                        Thread.sleep(1000);
						js.executeScript(appendPass);
						js.executeScript(signUp);
                        Thread.sleep(1000);
                
                        driver.findElement(By.xpath("//*[@id='sign-up-btn']"));
						actualOutput = "User redirected to the login page";
                        fetchAndStore(actualOutput, "SIGN", i++, cred);                        
                    }catch(NoSuchElementException e){
                        fetchAndStore(actualOutput, "SIGN", i++, cred);
                        js.executeScript(error);
                        
                    }catch (InterruptedException e) {
                        System.err.println(e);
                    }
                }
                actualOutput = "User redirected to the error page";
                ignore++;           
            }
        }catch(Exception e){
           System.out.println(e);
        }
        String[] signUpEnd = {};
        storeInFILE(signUpEnd);
        // System.out.println("\n======================================================================================================================");


        // ------------------------Login Testcases check----------------------------------------------------------------
        
		
        // System.out.println("\n\n======================================================Login Testcase Details============================================\nTestcase Description -> To check the User should use the correct/valid credentials for login.");
        
		String UserNameLogin="document.querySelectorAll(\"[name='username']\")[0]";
		String passWordLogin="document.querySelectorAll(\"[name='password']\")[0]";
		String Login = "document.querySelectorAll(\"[type='submit']\")[0].click()";
		String Logout = "document.querySelector(\"a[href=logoutPage]\").click()";
		
        try {
            csvReader = new CSVReader(new FileReader("E:\\selinium\\FoodOrderSystem\\Login.csv"));
            String[] csvCell;
            int user=0;
            int Admin=0, ignore=0;
            actualOutput = "user redirected to error page";
            while((csvCell = csvReader.readNext()) != null){
                if(ignore!=0){
					String appendUsername = UserNameLogin + ".value=\"" + csvCell[0] + "\";";
					String appendPassword = passWordLogin + ".value=\"" + csvCell[1] + "\";";
                    String[] cred = {"Username : " + csvCell[0] + ",  " + "Password : " + csvCell[1]};
                    try {
                        Thread.sleep(1000);
						js.executeScript(appendUsername);
                        Thread.sleep(1000);
						js.executeScript(appendPassword);
                        js.executeScript(Login);
                        Thread.sleep(1000);
						
                        driver.findElement(By.xpath("/html/body/h4/a"));
                        
                        actualOutput = "user redirected to Restaurant page";
                        fetchAndStore(actualOutput, "USL", user++, cred);
                        testRestaurant(driver);
						js.executeScript(Logout);
                    
                    }catch(NoSuchElementException e){  
                        try {
                            driver.findElement(By.xpath("/html/body/a"));
                            actualOutput = "Admin redirected to the admin page";
                            fetchAndStore(actualOutput, "ADL", Admin++, cred);
                            adminWorkTest(driver);
                            js.executeScript(Logout);

                        } catch (NoSuchElementException e1) {      
                            fetchAndStore(actualOutput, "USL", user++, cred);
                            js.executeScript(error);
                        }
                    }catch (InterruptedException e) {
                        System.err.println(e);

                    }
                }
                actualOutput = "user redirected to error page";
                ignore++;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        
        // System.out.println("\n======================================================================================================================");
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("E:/selinium/FoodOrderSystem/TESTLOG.html", true));
            out.write("</table></center>");
            out.close();
         }
         catch (IOException e) {
            System.out.println(e);
         }
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
                        String[] data = {"RID" + RestaurantID++, "Checking Restaurant page", "To check Restaurant search bar working by given correct keyword and searched restaurant redirected to the food page or not", "search keyword", "Restaurant search bar working by given correct keyword and searched restaurant redirected to the food page", "Restaurant search bar working by given correct keyword and searched restaurant redirected to the food page", "PASS"};
                        storeInFILE(data);
                        testFoodPage(driver);
                        Thread.sleep(1000);
                        //driver.navigate().back();
                        driver.findElement(By.xpath("//*[@id='myInput']")).clear();
                    
                    }catch (InterruptedException | NoSuchElementException e) {
    
                        searchRestaurantLocator.clear();
                        String[] data = {"RID" + RestaurantID++, "Checking Restaurant page", "To check Restaurant search bar working by given correct keyword and searched restaurant redirected to the food page or not", "search keyword", "Restaurant search bar working by given correct keyword and searched restaurant redirected to the food page", "Restaurant search bar result shows restaurant 'not found' by given keyword", "FAIL"};
                        storeInFILE(data);
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
        String[] datawrite = {"FID" + foodPageID++, "Checking food items page", "To check foodItems are selectable or not", "Click Add item buttons", "Food items are added and bill generated successfully", "Food items are added and bill generated successfully", "PASS"};
        try {
            Thread.sleep(2000);
            List<WebElement> foodList = driver.findElements(By.xpath("//*[@id='button1']"));
            for (WebElement webElement : foodList){
                webElement.click();
            }
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='checkout']/button")).click();
            Thread.sleep(2000);
            storeInFILE(datawrite);
            driver.findElement(By.xpath("/html/body/a")).click();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // Order details page test in the Admin Page ------------------------------------------------------

    public void adminWorkTest(WebDriver driver){
        String[] datawrite = {"ODA" + OrderDeatilsAdmin++, "Checking Admin page", "To check Order items are shown or not", "Click orderDetails button buttons", "Food order details displayed successfully", "Food order details displayed successfully", "PASS"};
        WebElement adminOrderDetailsLocator = driver.findElement(By.xpath("/html/body/section/a[2]"));
        try {
            Thread.sleep(2000);
            adminOrderDetailsLocator.click();
            storeInFILE(datawrite);
            Thread.sleep(2000);
            driver.navigate().back();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    //Fetch result and store in file

    private void fetchAndStore(String actualOutput, String type, int i, String[] credentials) {
        String[] STATUS = new String[6 + credentials.length];
        int index=0;
        try {
            ps = con.prepareStatement("select * from testcasetable where actualout=? and identify=?");
            ps.setString(1, actualOutput);
            ps.setString(2, type); 
            rs = ps.executeQuery();
            while (rs.next()) {
                STATUS[index++] = rs.getString("identify")+i;
                STATUS[index++] = rs.getString("scenario");
                STATUS[index++] = rs.getString("description");
                for(String cred : credentials){
                    STATUS[index++] = cred;
                }
                STATUS[index++] = rs.getString("expectedout");
                STATUS[index++] = rs.getString("actualout");
                STATUS[index++] = rs.getString("result");
                
                storeInFILE(STATUS);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }       
    }

    private void storeInFILE(String[] sTATUS) {
        
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("E:/selinium/FoodOrderSystem/TESTLOG.html", true));
            
            for (String string : sTATUS) {
                out.write("<td>"+string+"</td>");
            }
            out.write("</tr>");
            out.close();
         }
         catch (IOException e) {
            System.out.println("exception occoured"+ e);
         }
    }
}