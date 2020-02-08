package comemployee.cleanchain_sa.cleanchaindriver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView myordersTapped = (TextView) findViewById(R.id.myordersView);
        TextView dashboardTapped = (TextView) findViewById(R.id.dashboardView);

        TextView userName = (TextView) findViewById(R.id.userName);






        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(Config.Name_SHARED_PREF,"Not Available");


        //Showing the current logged in email to textview
        userName.setText("Welcome: " + name );


        myordersTapped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Starting profile activity
                Intent intent = new Intent(MainActivity.this, MyOrdersActivity.class);
                startActivity(intent);

            }
        });


        dashboardTapped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Starting profile activity
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);


            }
        });




    }




    // To show Menu on Action BAr

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logoutmenu, menu);
        return true;
    }

    // When Back menu Tapped

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout_menu:

                //Getting out sharedpreferences
                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                //Getting editor
                SharedPreferences.Editor editor = preferences.edit();

                //Puting the value false for loggedin
                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                //Putting blank value to email
                editor.putString(Config.EMAIL_SHARED_PREF, "");
                editor.putString(Config.ID_SHARED_PREF, "");
                editor.putString(Config.Name_SHARED_PREF, "");
                editor.putString(Config.Mobile_SHARED_PREF, "");
                editor.putString(Config.User_address, "");

                //Saving the sharedpreferences
                editor.commit();

                //Starting login activity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
