public class LoginTestCases {
    static UserStateTestCases userStateTestCases = new UserStateTestCases();
    static int TestCaseCount = userStateTestCases.getSize();
    public String[] returnCredentials(int i){
        String[] cred = new String[2];       
        cred[0] = userStateTestCases.getUserName(i);
        cred[1] = userStateTestCases.getPassword(i);

        return cred;
    }
}

class UserStateTestCases{
    String[] user = {"vinoth", "gv", "admin"};
    String[] pass = {"vinoth", "vinoth", "admin123"};
    public String getUserName(int i){
        
        return user[i];
    }
    public int getSize() {
        return user.length;
    }
    public String getPassword(int i){
        return pass[i];
    }
}