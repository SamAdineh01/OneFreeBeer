package dk.onefreebeer.gui.login;

import java.security.PrivateKey;
import java.util.HashMap;

public class UserManager {

    private static HashMap<String, String> adminInfo = new HashMap<>();
    private static HashMap<String, String> ecInfo = new HashMap<>();

    static {
        adminInfo.put("Radelc", "Adminpass");

        ecInfo.put("Rad01", "pass");
        ecInfo.put("Sam", "password");
    }

    public static boolean isAdmin(String username, String password) {
        return adminInfo.containsKey(username) && adminInfo.get(username).equals(password);
    }

    public static boolean isEC(String username, String password) {
        return ecInfo.containsKey(username) && ecInfo.get(username).equals(password);
    }

    public static void addEC(String username, String password) {
        ecInfo.put(username, password);
    }
}