import java.util.*;
import java.io.*;

public class StoreInfoLocators {
	
	
	
    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
		System.out.println(getPath("emailSignUp"));
    }
	
	static String getPath(String val){
		String path="";
		try{
			
			FileReader reader=new FileReader("info.properties");  
		  
			Properties p=new Properties();  
			p.load(reader); 
			path = p.getProperty(val);
		}catch(IOException e){
			System.out.print("");
		}
		finally{
			return path;
		}
        //return path;
	}
}
