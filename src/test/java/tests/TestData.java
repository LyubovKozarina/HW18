package tests;

public class TestData {

    // public static final String login = System.getProperty("login","test123456");
    //public static final String password = System.getProperty("password", "Test123456@");

    public static String getLogin() {
        return System.getProperty("login");
    }

    public static String getPassword() {
        return System.getProperty("password");
    }

    public static final String isbn = "9781491904244";
}
