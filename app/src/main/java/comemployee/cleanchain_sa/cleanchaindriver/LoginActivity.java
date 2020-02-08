package comemployee.cleanchain_sa.cleanchaindriver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    SharedPreferences sharedpreferences;

    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Hide ActionBar
        ActionBar myActionBar = getSupportActionBar();
        //For hiding android actionbar
        myActionBar.hide();



        // Make sure if user is login go to HomeActivity
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }


        final EditText etUsername = (EditText) findViewById(R.id.eemailText);
        final EditText etPassword = (EditText) findViewById(R.id.passwordText);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.registerView);
        final Button bLogin = (Button) findViewById(R.id.loginButton);

        final ProgressBar progressBarlogin = (ProgressBar) findViewById(R.id.progressBar);

        progressBarlogin.setVisibility(View.INVISIBLE);


        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        });


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valid = true; // to make sure everthing is OK


                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();


                if (username.isEmpty()) {
                    //String message122 = getResources().getString(R.string.msgEnterEmail);
                    String message122 = "Enter your mobile";
                    etUsername.setError(message122);
                    valid = false;

                } else {
                    etUsername.setError(null);
                }

                if (password.isEmpty()) {
                    //String message133 = getResources().getString(R.string.msgEnterPassword2);
                    String message133 = "Enter your password";
                    etPassword.setError(message133);
                    valid = false;

                } else {
                    etPassword.setError(null);
                }


                if (valid) { // if everthing is OK


                /*message to show dialog
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Login...");
                progressDialog.show();*/


                    // visible the progress bar
                    progressBarlogin.setVisibility(View.VISIBLE);


                    // Response received from the server
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");


                                if (success) {


                                    String id = jsonResponse.getString("did");
                                    String name = jsonResponse.getString("dfullname");
                                    String email = jsonResponse.getString("demail");
                                    String mobile = jsonResponse.getString("dmobile");


                                    Log.i("Hi", id);
                                    Log.i("Hi", name);
                                    Log.i("Hi", mobile);




                                /* make new session
                                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                editor.putString(Name, name);
                                editor.putString(Email, email);
                                editor.putString(Phone, mobile);
                                editor.commit();

                                Intent in = new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(in);*/

                                    //Creating a shared preference
                                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                    //Creating editor to store values to shared preferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    //Adding values to editor
                                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                    editor.putString(Config.ID_SHARED_PREF, id);
                                    editor.putString(Config.EMAIL_SHARED_PREF, email);
                                    editor.putString(Config.Name_SHARED_PREF, name);
                                    editor.putString(Config.Mobile_SHARED_PREF, mobile);

                                    //Saving values to editor
                                    editor.commit();

                                    //Starting profile activity
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);




                                /*Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                intent.putExtra("mobile", mobile);
                                LoginActivity.this.startActivity(intent);*/

                                } else {

                                    // Hide the progress bar
                                    progressBarlogin.setVisibility(View.INVISIBLE);


                                    String message122 = "Enter your mobile";
                                    etUsername.setError(message122);


                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Login Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);

                } // end valid




            }
        });





    }
}
