package comemployee.cleanchain_sa.cleanchaindriver;

/**
 * Created by moh on 24 أكت، 2017 م.
 */

public class Config {

    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static String EMAIL_SHARED_PREF = "email";
    //This would be used to store the email of current logged in user
    public static String ID_SHARED_PREF = "id";
    //This would be used to store the email of current logged in user
    public static String Name_SHARED_PREF = "name";
    //This would be used to store the email of current logged in user
    public static String Mobile_SHARED_PREF = "mobile";

    // General Varibal
    public static Double User_lat = 0.0;
    public static Double User_log = 0.0;
    public static Double Drop_lat = 0.0;
    public static Double Drop_log = 0.0;
    public static String User_address = "no address";
    public static String Drop_address = "no address";

    //This would be used to store the email of current logged in user
    public static String Request_ID = "id";





    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
}
