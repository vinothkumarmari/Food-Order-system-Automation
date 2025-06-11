public class AdminTestCase {
    static AdminStateTestCase adminStateTestCase = new AdminStateTestCase();
    static int TestCase_Count = adminStateTestCase.getSize();
    public String[] returnCredentials(int i){
        String[] cred = new String[2];
        cred[0] = adminStateTestCase.getUserName(i);
        cred[1] = adminStateTestCase.getPassword(i);

        return cred;
    }
}

class AdminStateTestCase{
    String[] user = {"admin", "abc"};
    String[] pass = {"admin123", "vinoth"};
    public String getUserName(int i){       
        return user[i];
    }
    public String getPassword(int i){      
        return pass[i];
    }
    public int getSize() {
        return user.length;
    }
}
