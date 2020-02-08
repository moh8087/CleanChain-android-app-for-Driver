package comemployee.cleanchain_sa.cleanchaindriver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {

    // identify the user information
    String did ;

    String TotalCompleteOrder ;
    String TotalCancelledOrder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);




        final TextView successView = (TextView) findViewById(R.id.completedNumberView);
        final TextView cancelView = (TextView) findViewById(R.id.cancelledOrdersView);
        final TextView userName = (TextView) findViewById(R.id.usernameView);





        // identify user information
        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        did = sharedPreferences.getString(Config.ID_SHARED_PREF, "Not Available");
        String name = sharedPreferences.getString(Config.Name_SHARED_PREF,"Not Available");




        userName.setText("Welcome: " + name );



        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    Log.i("TotalCompleteOrder", Boolean.toString(success));

                    TotalCompleteOrder = jsonResponse.getString("completednumber");
                    TotalCancelledOrder = jsonResponse.getString("cancellednumber");

                    if (success) {

                        Log.i("TotalCompleteOrder", TotalCompleteOrder);
                        Log.i("TotalCancelledOrder", TotalCancelledOrder);



                        successView.setText(TotalCompleteOrder);
                        cancelView.setText(TotalCancelledOrder);


                    } else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DashboardActivity.this);
                        builder.setMessage("Bring data Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        DashboardRequest dashboardRequest = new DashboardRequest(did, responseListener);
        RequestQueue queue = Volley.newRequestQueue(DashboardActivity.this);
        queue.add(dashboardRequest);













    }




    // To show Menu on Action BAr

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    // When Back menu Tapped

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.back_menu:
                // Back To Home menu
                Intent changeActivity = new Intent (DashboardActivity.this, MainActivity.class);
                DashboardActivity.this.startActivity(changeActivity);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
