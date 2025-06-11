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

public class TestcasesUpdate{
	
    public static void main(String[] args) throws InterruptedException{

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
	Scanner scanner = new Scanner(System.in);
    static int foodPageID = 0, RestaurantID = 0, OrderDeatilsAdmin = 0, RestaurantsViewAdmin=0, ARestaurantID=0, RestaurantRegisterID=0, FoodRegister=0, SearchFood=0, UpdateFood=0, FoodEditCancel=0;
    WorkSimultenously(WebDriver driver){
        this.driver = driver;
    }
    public WorkSimultenously(){}

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
            out.write("<html><head><style>td, th{align-items:center; padding : 5px;}th{background-color:black; color:white; font-size:18px;}</style></head><body><center><table border=2 width=\"100%\"><tr><th>ID</th><th>Scenario</th><th>Description</th><th>Test Inputs</th><th>ExpectedOutput</th><th>ActualOutput</th><th>Result</th></tr><tr>");
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
		int contiuee=0;
        
        do{
            ArrayList<Integer> multiPages = new ArrayList<>();
            multiPages.add(0);
			System.out.println("\n***************************Testing pages********************************\n\n                                  1.Test SignUpPage\n                                  2.Test LoginPage\n                                  3.Test Restaurants page\n                                  4.Test Food page\n                                  5.Test admin page\n                                  6.Test admin RestaurantList page\n                                  7.Test Register new Restaurant\n                                  8.Add/Modify food From Selected restaurant\n                                  9.Select Multiple pages to test(1-8)\n                                  10.Test AllPages\n\nTest any You wanted...");

            StartTesting(multiPages);
            System.out.println("Enter 1 to continue testing : (otherwise click any..)");
            contiuee = scanner.nextInt();
		}while(contiuee==1);
        
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

    public void StartTesting(ArrayList<Integer> multiPages){
        int switchBreak=0;
        int choice=0;

        for(int i : multiPages){
            if(i==0){
                choice=scanner.nextInt();
            }
            else{
                choice = i;
            }

            switch(choice){
                case 1:
                    runSignUp();
                    break;
                case 2:
                    runLogin(0);
                    break;
                case 3:
                    testRestaurant(driver, 0);
                    driver.findElement(By.xpath(readLocators("userLogout"))).click();
                    break;
                case 4:
                    testFoodPage(driver, 0);
                    driver.findElement(By.xpath(readLocators("userLogout"))).click();
                    break;
                case 5:
                    adminPageFunctinality(driver, 1);
					driver.findElement(By.xpath(readLocators("adminLogout"))).click();
                    break;
                case 6:
                    TestAdminRestaurantSearch(driver, 0);
                    driver.findElement(By.xpath(readLocators("adminLogout"))).click();
                    break;
                case 7:
                    TestNewRestaurantRegisterAdmin(driver, 0);
                    driver.findElement(By.xpath(readLocators("adminLogout"))).click();
                    break;
                case 8:
                    testAddModifyFoodItems(1);
                    break;
                case 9:
                    selectMupltipleTestPages(driver);
                    break;
                case 10:
                    runSignUp();
                    runLogin(1);
                    break;
                default:
                    System.out.println("Not available........");
                    break;
            }
        }
    }
	
	// ------------------------SignUp Testcases check----------------------------------------------------------------

	
	public void runSignUp(){
        System.out.println("Sign Up Testing started");
        CSVReader csvReader;
		String actualOutput = "User redirected to the error page";
        SignUpPageElements signUpPageElements = new SignUpPageElements(driver);
        try {
            csvReader = new CSVReader(new FileReader("E:\\selinium\\FoodOrderSystem\\SignUp.csv"));
            String[] csvCell;
            int i=0, ignore=0;
            while((csvCell = csvReader.readNext()) != null){
                if(ignore!=0){
                    String[] cred = {"Phone : " + csvCell[0] + ",  " + "Username : " + csvCell[1] + ",  " + "Email : " + csvCell[2] + ",  " + "Password : " + csvCell[3]};
                    try {
                        Thread.sleep(1000);
                        signUpPageElements.getSignElement().click();						
                        Thread.sleep(1000);
                        signUpPageElements.getPhoneElement().sendKeys(csvCell[0]);
                        Thread.sleep(1000);
                        signUpPageElements.getUserElement().sendKeys(csvCell[1]);
                        Thread.sleep(1000);
                        signUpPageElements.getEmailElement().sendKeys(csvCell[2]);
                        Thread.sleep(1000);
                        signUpPageElements.getPassElement().sendKeys(csvCell[3] + Keys.ENTER);
                        Thread.sleep(1000);
                
                        driver.findElement(By.xpath(readLocators("signSignUp")));
						actualOutput = "User redirected to the login page";
                        fetchAndStore(actualOutput, "SIGN", i++, cred);                        
                    }catch(NoSuchElementException e){
                        fetchAndStore(actualOutput, "SIGN", i++, cred);
                        driver.findElement(By.xpath(readLocators("errorPage"))).click();                        
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
	}

    // Login test -----------------------------------------------------------------
	
	public void runLogin(int conditionTest){
        System.out.println("Login Testing started");
        CSVReader csvReader;
		LoginPageElements loginPageElements = new LoginPageElements(driver);
        String actualOutput = "User redirected to the error page";
        try {
            csvReader = new CSVReader(new FileReader("E:\\selinium\\FoodOrderSystem\\Login.csv"));
            String[] csvCell;
            int user=0;
            int Admin=0, ignore=0;
            actualOutput = "user redirected to error page";
            while((csvCell = csvReader.readNext()) != null){
                if(ignore!=0){
                    String[] cred = {"Username : " + csvCell[0] + ",  " + "Password : " + csvCell[1]};
                    try {
                        Thread.sleep(1000);
                        loginPageElements.getUserElement().sendKeys(csvCell[0]);
                        Thread.sleep(1000);
                        loginPageElements.getPassElement().sendKeys(csvCell[1] + Keys.ENTER);
                        Thread.sleep(1000);
						
                        driver.findElement(By.xpath(readLocators("userLogout")));
                        
                        actualOutput = "user redirected to Restaurant page";
                        fetchAndStore(actualOutput, "USL", user++, cred);
                        if(conditionTest == 1) testRestaurant(driver, 1);
                        driver.findElement(By.xpath(readLocators("userLogout"))).click();
                    }catch(NoSuchElementException e){  
                        try {
                            driver.findElement(By.xpath(readLocators("adminLogout")));
                            actualOutput = "Admin redirected to the admin page";
                            fetchAndStore(actualOutput, "ADL", Admin++, cred);
                            if(conditionTest == 1){
                                adminPageFunctinality(driver, 0);
                            }
                            driver.findElement(By.xpath(readLocators("adminLogout"))).click();

                        } catch (NoSuchElementException e1) {      
                            fetchAndStore(actualOutput, "USL", user++, cred);
                            driver.findElement(By.xpath(readLocators("errorPage"))).click(); 
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
	}

    // Restaurant page Test --------------------------------------------------

    public void testRestaurant(WebDriver driver, int ResOnly){
        System.out.println("Restaurant page Testing started");
        if(ResOnly!=1){
            LoginPageElements loginPageElements = new LoginPageElements(driver);
            loginPageElements.getUserElement().sendKeys("vinoth");
            loginPageElements.getPassElement().sendKeys("vinoth" + Keys.ENTER);
        }
        CSVReader csvReader;
        WebElement searchRestaurantLocator = driver.findElement(By.xpath(readLocators("restaurantSearch")));
        String acutalOutput = "Searched restaurant \"not found\"";
		try {
            csvReader = new CSVReader(new FileReader("E:\\selinium\\FoodOrderSystem\\SearchRestaurant.csv"));
            String[] csvCell;
            int ignore=0;           
            while((csvCell = csvReader.readNext()) != null){
                if(ignore!=0){
                    String[] cred = {"search keyword : "+csvCell[0]};
                    try {
                        Thread.sleep(1000);
                        searchRestaurantLocator.sendKeys(csvCell[0]);
                        WebElement openRestaurentLocator = driver.findElement(By.xpath(readLocators("openRestaurant")));
                        Thread.sleep(2000);
                        openRestaurentLocator.click();
                        acutalOutput = "Searched restaurant redirected to food page";
                        fetchAndStore(acutalOutput, "RID", RestaurantID++, cred);
                        if(ResOnly!=1){
                            driver.navigate().back();
                        }
                        else{
                            testFoodPage(driver, 1);
                        }
                        Thread.sleep(1000);
                        driver.findElement(By.xpath(readLocators("restaurantSearch"))).clear();
                    
                    }catch (InterruptedException | NoSuchElementException e) {
    
                        searchRestaurantLocator.clear();
                        fetchAndStore(acutalOutput, "RID", RestaurantID++, cred);
                    }
                }
                acutalOutput = "Searched restaurant \"not found\"";
                ignore++;
                searchRestaurantLocator = driver.findElement(By.xpath(readLocators("restaurantSearch")));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
	}	

    // Foodpage test --------------------------------------------------------------

    public void testFoodPage(WebDriver driver, int FoodPageOnly){
        System.out.println("Food Page Testing started");
        if(FoodPageOnly!=1){
            LoginPageElements loginPageElements = new LoginPageElements(driver);
            loginPageElements.getUserElement().sendKeys("vinoth");
            loginPageElements.getPassElement().sendKeys("vinoth" + Keys.ENTER);
            driver.findElement(By.xpath(readLocators("restaurantSearch"))).sendKeys("abc");
            driver.findElement(By.xpath(readLocators("openRestaurant"))).click();
        }
        String acutalOutput = "Food items are added and bill generated";
        String[] cred = {"Click Add food item buttons"};
        try {
            Thread.sleep(2000);
            List<WebElement> foodList = driver.findElements(By.xpath(readLocators("addFood")));
            for (WebElement webElement : foodList){
                webElement.click();
            }
            Thread.sleep(2000);
            driver.findElement(By.xpath(readLocators("foodCheckout"))).click();
            Thread.sleep(2000);
            fetchAndStore(acutalOutput, "FID", foodPageID++, cred);
            driver.findElement(By.xpath(readLocators("billToHome"))).click();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // Admin page ------------------------------------------------------

    public void adminPageFunctinality(WebDriver driver, int adminFunctionality){
        System.out.println("Admin Page Testing started");
        if(adminFunctionality==1){
            LoginPageElements loginPageElements = new LoginPageElements(driver);
            loginPageElements.getUserElement().sendKeys("admin");
            loginPageElements.getPassElement().sendKeys("admin123" + Keys.ENTER);
        }

        String actualOutput = "Restauarant listed", actualOutput1 = "Order details displayed";
        String[] cred1 = {"Click Restaurants Details button"}, cred2 = {"Click orderDetails button buttons"};
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath(readLocators("adminViewRestaurants"))).click();
            fetchAndStore(actualOutput, "AVR", RestaurantsViewAdmin++, cred1);
            if(adminFunctionality!=1){
                TestAdminRestaurantSearch(driver, 1);
            }
            else{
                driver.navigate().back();
            }

            Thread.sleep(2000);
            driver.findElement(By.xpath(readLocators("adminViewOrders"))).click();
            fetchAndStore(actualOutput1, "AVOD", OrderDeatilsAdmin++, cred2);
            Thread.sleep(2000);
            driver.navigate().back();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // Admin Page view Restaurants

    public void TestAdminRestaurantSearch(WebDriver driver, int AdminOnly){
        System.out.println("Admin Restaurant list Page Testing started");
        if(AdminOnly==0){
            LoginPageElements loginPageElements = new LoginPageElements(driver);
            loginPageElements.getUserElement().sendKeys("admin");
            loginPageElements.getPassElement().sendKeys("admin123" + Keys.ENTER);
            driver.findElement(By.xpath(readLocators("adminViewRestaurants"))).click();
        }
        CSVReader csvReader;
        try {
            
            csvReader = new CSVReader(new FileReader("E:\\selinium\\FoodOrderSystem\\SearchRestaurant.csv"));
			Thread.sleep(2000);
            WebElement searchRestaurantLocator = driver.findElement(By.xpath(readLocators("adminRestaurantSearch")));
            String[] csvCell;
            int ignore=0;           
            while((csvCell = csvReader.readNext()) != null){
                String actualOutput = "Searched restaurant redirected to food edit page";
                String[] cred = {"Search key : " + csvCell[0]};
                if(ignore!=0){
                    try {
                        Thread.sleep(1000);
                        searchRestaurantLocator.sendKeys(csvCell[0]);
                        WebElement openRestaurentLocator = driver.findElement(By.xpath(readLocators("adminRestaurantClick")));
                        Thread.sleep(2000);
                        fetchAndStore(actualOutput, "ARID", ARestaurantID++, cred);
                        openRestaurentLocator.click();
                        if(AdminOnly==0){
                            driver.navigate().back();
                        }
                        else{
                            testAddModifyFoodItems(0);
                        }
                        Thread.sleep(1000);
                        driver.findElement(By.xpath(readLocators("adminRestaurantSearch"))).clear();
                    
                    }catch (InterruptedException | NoSuchElementException e) {   
                        searchRestaurantLocator.clear();
                        actualOutput="Searched Restaurant not found";
                        fetchAndStore(actualOutput, "ARID", ARestaurantID++, cred);
                    }
                }
                ignore++;
                searchRestaurantLocator = driver.findElement(By.xpath(readLocators("adminRestaurantSearch")));
            }
            if(AdminOnly!=0){
                Thread.sleep(2000);
                TestNewRestaurantRegisterAdmin(driver, 1);
            }
            else{
                Thread.sleep(2000);
                driver.navigate().back();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // Test register new restaurant

    public void TestNewRestaurantRegisterAdmin(WebDriver driver, int AdminOnly){
        System.out.println("Admin Restaurant Register Page Testing started");
        RegisterRestaurantElements registerRestaurantElements = new RegisterRestaurantElements(driver);
        if(AdminOnly!=1){
            LoginPageElements loginPageElements = new LoginPageElements(driver);
            loginPageElements.getUserElement().sendKeys("admin");
            loginPageElements.getPassElement().sendKeys("admin123" + Keys.ENTER);
            driver.findElement(By.xpath(readLocators("adminViewRestaurants"))).click();
        }
        try {

            CSVReader csvReader, csvReader1;       
            csvReader = new CSVReader(new FileReader("E:\\selinium\\FoodOrderSystem\\RestaurantDetails.csv"));
            csvReader1 = new CSVReader(new FileReader("E:\\selinium\\FoodOrderSystem\\foodDetails.csv"));
            
            String[] csvCell, csvCell1;
            int ignore=0, ignore1=0;           
            String actualOutput="Restaurant added", actualOutput1="Food item added";
            while((csvCell = csvReader.readNext()) != null){
                if(ignore!=0){
                    Thread.sleep(2000);
                    driver.findElement(By.xpath(readLocators("registerNew"))).click();
                    String[] cred1 = {"Restaurant name : "+csvCell[0]+ "\nRating : "+csvCell[1]+ "\nReview : "+csvCell[2]};
                    try {
                        Thread.sleep(1000);
                        registerRestaurantElements.getrestaurantName().sendKeys(csvCell[0]);
                        Thread.sleep(1000);
                        registerRestaurantElements.getrestaurantRating().sendKeys(csvCell[1]);
                        Thread.sleep(1000);
                        registerRestaurantElements.getrestaurantReview().sendKeys(csvCell[2]);
                        Thread.sleep(1000);
                        registerRestaurantElements.getaddRestaurantBTN().click();
                        fetchAndStore(actualOutput, "RRID", RestaurantRegisterID++, cred1);
                        csvReader1 = new CSVReader(new FileReader("E:\\selinium\\FoodOrderSystem\\foodDetails.csv"));
                        ignore1=0;
                        while((csvCell1 = csvReader1.readNext()) != null){
                            if(ignore1!=0){
                                String[] cred2 = {"Food Name : "+csvCell1[0]+ "\nFood Price : "+csvCell1[1]};
                                try {
                                    Thread.sleep(1000);
                                    registerRestaurantElements.getFoodName().sendKeys(csvCell1[0]);
                                    Thread.sleep(1000);
                                    registerRestaurantElements.getFoodPrice().sendKeys(csvCell1[1]);
                                    Thread.sleep(1000);
                                    registerRestaurantElements.getaddFoodBTN().click();
                                    fetchAndStore(actualOutput1, "FRID", FoodRegister++, cred2);
                                    Thread.sleep(1000);
                                    registerRestaurantElements.getFoodName().clear();
                                    Thread.sleep(1000);
                                    registerRestaurantElements.getFoodPrice().clear();
                                }catch (InterruptedException | NoSuchElementException e) {
                                    System.out.println(e);
                                }
                            }
                            ignore1++;
                        }
                        Thread.sleep(1500);
                        driver.findElement(By.xpath(readLocators("RegisterBTN"))).click();
                        Thread.sleep(1500);
                    }catch (Exception e) {
                        System.out.println(e);
                    }
                }
                ignore++;
            }
            Thread.sleep(2000);
            driver.findElement(By.xpath(readLocators("AdminHome"))).click();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
	
	// Testing Add/Modify Food Items
	
    public void testAddModifyFoodItems(int onlyThisPage){
        try {
            if(onlyThisPage==1){
                LoginPageElements loginPageElements = new LoginPageElements(driver);
                Thread.sleep(1000);
                loginPageElements.getUserElement().sendKeys("admin");
                Thread.sleep(1000);
                loginPageElements.getPassElement().sendKeys("admin123" + Keys.ENTER);
                Thread.sleep(1000);
                driver.findElement(By.xpath(readLocators("adminViewRestaurants"))).click();
                Thread.sleep(1000);
                driver.findElement(By.xpath(readLocators("adminRestaurantSearch"))).sendKeys("mam");
                Thread.sleep(1000);
                driver.findElement(By.xpath(readLocators("adminRestaurantClick"))).click();
            }
            
            CSVReader csvReader;       
            csvReader = new CSVReader(new FileReader("E:\\selinium\\FoodOrderSystem\\FoodEdit.csv"));
            
            String[] csvCell, csvCell1;
            int ignore=0;           
            EditFoodElements editFood = new EditFoodElements(driver);
            String actualSearchOutput="Food Item not Found", actualEditOutput="Food Item Udated";
            while((csvCell = csvReader.readNext()) != null){
                if(ignore!=0){
                    String[] searchCred = {"Search Keyword : "+csvCell[0]}, UpdateCred = {"Food Name : "+csvCell[1] + "\nFood Price : "+csvCell[2]}; 
                    try{
                        Thread.sleep(1000);
                        editFood.getSearchFood().sendKeys(csvCell[0]);
                        actualSearchOutput = "Food Item Found";
                        fetchAndStore(actualSearchOutput, "FSID", SearchFood++, searchCred);
                        Thread.sleep(2000);
                        editFood.getAddModifyBTN().click();
                        Thread.sleep(1000);
                        editFood.getCancelBTN().click();
                        Thread.sleep(1000);
                        editFood.getAddModifyBTN().click();
                        Thread.sleep(1000);
                        editFood.getFoodNameEdit().clear();
                        editFood.getFoodNameEdit().sendKeys(csvCell[1]);
                        Thread.sleep(1000);
                        editFood.getFoodPriceEdit().clear();
                        editFood.getFoodPriceEdit().sendKeys(csvCell[2]);
                        Thread.sleep(1000);
                        editFood.getFoodEditUpdate().click();
                        fetchAndStore(actualEditOutput, "FUID", UpdateFood++, UpdateCred);
                        Thread.sleep(1000);

                        editFood.getSearchFood().clear();
                    }
                    catch(NoSuchElementException e){
                        fetchAndStore(actualSearchOutput, "FSID", SearchFood++, searchCred);
                        editFood.getSearchFood().clear();
                    }
                }
                actualSearchOutput="Food Item not Found";
                ignore++;
            }
            driver.navigate().back();
        } 
        catch (Exception e) {
            System.err.println(e);
        }

        
       
    }

    // select mutiple test pages
    
    public void selectMupltipleTestPages(WebDriver driver){
        int temp=0;
        ArrayList<Integer> multiPagesSelect = new ArrayList<>();
        System.out.println("Enter the page number for multi select : (enter -1 to finish select)");
		for(int i=0; ; i++){
		    temp = scanner.nextInt();
		    if(temp==-1){
		        break;
		    }
		    multiPagesSelect.add(temp);
		}
        StartTesting(multiPagesSelect);
    }

    //Fetch Test case result 

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

    // Store testcase details in file 

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
            System.out.println(e);
         }
    }

    // Find Locators from properties file

    public String readLocators(String locatorName){
        String path="";
		try{
			FileReader reader=new FileReader("info.properties");   
			Properties p=new Properties();  
			p.load(reader); 
			path = p.getProperty(locatorName);
		}catch(IOException e){
			System.out.print(e);
		}
		finally{
			return path;
		}
    }
}

// Sign Up page Elements

class SignUpPageElements{  
    WebDriver driver;
    WorkSimultenously workSimultenously = new WorkSimultenously();
    SignUpPageElements(WebDriver driver){
        this.driver = driver;
    }  
    
    private By phone = By.xpath(workSimultenously.readLocators("phoneSignUp"));
    private By user = By.xpath(workSimultenously.readLocators("userSignUp"));
    private By email = By.xpath(workSimultenously.readLocators("emailSignUp"));
    private By pass = By.xpath(workSimultenously.readLocators("passSignUp"));
    private By sign = By.xpath(workSimultenously.readLocators("signSignUp"));

    public WebElement getPhoneElement(){
        return driver.findElement(phone);
    }
    public WebElement getUserElement(){
        return driver.findElement(user);
    }
    public WebElement getEmailElement(){
        return driver.findElement(email);
    }
    public WebElement getPassElement(){
        return driver.findElement(pass);
    }
    public WebElement getSignElement(){
        return driver.findElement(sign);
    }
}

// Login page Elements

class LoginPageElements{

	WorkSimultenously workSimultenously = new WorkSimultenously();
	
    private By user = By.name(workSimultenously.readLocators("username"));
    private By pass = By.name(workSimultenously.readLocators("password"));

    WebDriver driver;
    LoginPageElements(WebDriver driver){
        this.driver = driver;
    }

    public WebElement getUserElement(){
        return driver.findElement(user);
    }

    public WebElement getPassElement(){
        return driver.findElement(pass);
    }
}

// Edit FoodItems Admin elements

class EditFoodElements{
    WorkSimultenously workSimultenously = new WorkSimultenously();
	
    private By SearchFood = By.xpath(workSimultenously.readLocators("AdminFoodSearch"));
    private By AddModifyBTN = By.xpath(workSimultenously.readLocators("foodAddModifyBTN"));
    private By FoodNameEdit = By.xpath(workSimultenously.readLocators("foodEditName"));
    private By FoodPriceEdit = By.xpath(workSimultenously.readLocators("foodEditPrice"));
    private By FoodEditUpdate = By.xpath(workSimultenously.readLocators("FoodEditUpdateBTN"));
    private By CancelBTN = By.xpath(workSimultenously.readLocators("FoodEditCancelBTN"));
    private By DeleteBTN = By.xpath(workSimultenously.readLocators("FoodEditDeleteBTN"));


    WebDriver driver;
    EditFoodElements(WebDriver driver){
        this.driver = driver;
    }

    public WebElement getSearchFood(){
        return driver.findElement(SearchFood);
    }

    public WebElement getAddModifyBTN(){
        return driver.findElement(AddModifyBTN);
    }

    public WebElement getFoodNameEdit(){
        return driver.findElement(FoodNameEdit);
    }
	public WebElement getFoodPriceEdit(){
        return driver.findElement(FoodPriceEdit);
    }

    public WebElement getFoodEditUpdate(){
        return driver.findElement(FoodEditUpdate);
    }
	public WebElement getCancelBTN(){
        return driver.findElement(CancelBTN);
    }

    public WebElement getDeleteBTN(){
        return driver.findElement(DeleteBTN);
    }
}

// Register new restaurant elements

class RegisterRestaurantElements{
    WorkSimultenously workSimultenously = new WorkSimultenously();
	
    private By restaurantName = By.xpath(workSimultenously.readLocators("registerRestaurantName"));
    private By restaurantRating = By.xpath(workSimultenously.readLocators("registerRestaurantRating"));
    private By restaurantReview = By.xpath(workSimultenously.readLocators("registerRestaurantReview"));
    private By addRestaurantBTN = By.xpath(workSimultenously.readLocators("addRestaurantButton"));
    private By FoodName = By.xpath(workSimultenously.readLocators("addFoodName"));
    private By FoodPrice = By.xpath(workSimultenously.readLocators("addFoodPrice"));
    private By addFoodBTN = By.xpath(workSimultenously.readLocators("addFoodBtn"));


    WebDriver driver;
    RegisterRestaurantElements(WebDriver driver){
        this.driver = driver;
    }

    public WebElement getrestaurantName(){
        return driver.findElement(restaurantName);
    }

    public WebElement getrestaurantRating(){
        return driver.findElement(restaurantRating);
    }

    public WebElement getrestaurantReview(){
        return driver.findElement(restaurantReview);
    }
	public WebElement getaddRestaurantBTN(){
        return driver.findElement(addRestaurantBTN);
    }

    public WebElement getFoodName(){
        return driver.findElement(FoodName);
    }
	public WebElement getFoodPrice(){
        return driver.findElement(FoodPrice);
    }

    public WebElement getaddFoodBTN(){
        return driver.findElement(addFoodBTN);
    }
}
