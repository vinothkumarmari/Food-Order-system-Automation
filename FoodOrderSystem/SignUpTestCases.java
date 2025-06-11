public class SignUpTestCases {
    static SignUpStateTestCases signUpStateTestCases = new SignUpStateTestCases();
    static int TestCase_Count = signUpStateTestCases.getSize();
    public String[] returnCredentials(int i){
        String[] cred = new String[4];
        cred[0] = signUpStateTestCases.getPhone(i);
        cred[1] = signUpStateTestCases.getUserName(i);
        cred[2] = signUpStateTestCases.getEmail(i);
        cred[3] = signUpStateTestCases.getPassword(i);
        
        return cred;
    }
}

class SignUpStateTestCases{
    String[] user = {"vinith", "vinith"};  
    String[] pass = {"kumar", "kumar"}; 
    String[] phone = {"9940441759", "9940441759"};
    String[] email = {"vinith@gmail.com", "vinith@gmail.com"};
    public String getUserName(int i){
        return user[i];
    }
    public int getSize() {
        return user.length;
    }
    public String getPassword(int i){
        return pass[i];
    }
    public String getPhone(int i){
        return phone[i];
    }
    public String getEmail(int i){
        return email[i];
    }
}