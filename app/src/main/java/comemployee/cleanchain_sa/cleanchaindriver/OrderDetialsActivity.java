package comemployee.cleanchain_sa.cleanchaindriver;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderDetialsActivity extends AppCompatActivity {

    String requetID;
    String riderMobile;

    public static double riderLat;
    public static double riderLog;
    Double driverLat;
    Double driverlog;


    public static String picklatitude;
    public static String picklongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detials);


        Log.i("Request Number is: ", Config.Request_ID);

        // being request id from Config
        requetID = Config.Request_ID;

        final TextView orderIDView = (TextView) findViewById(R.id.orderIDView);
        final TextView customerView = (TextView) findViewById(R.id.cutomerView);




        Button callCustomerButton = (Button) findViewById(R.id.callButton);
        Button googlemapButton = (Button) findViewById(R.id.googleMapButton);
        Button cancelOrderButton = (Button) findViewById(R.id.cancelButton);
        Button receivedOrderButton = (Button) findViewById(R.id.receivedButton);


        // Bring user information
        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    //boolean success = jsonResponse.getBoolean("success");

                    // bring driver information from json
                    String id_request = jsonResponse.getString("id");
                    String riderName = jsonResponse.getString("rname");
                    riderMobile = jsonResponse.getString("rmobile");
                    picklatitude = jsonResponse.getString("picklatitude");
                    picklongitude = jsonResponse.getString("picklongitude");
                    String dlat = jsonResponse.getString("dlat");
                    String dlog = jsonResponse.getString("dlog");


                    if (id_request != null) {


                        riderLat = Float.parseFloat(picklatitude);
                        riderLog = Float.parseFloat(picklongitude);

                        orderIDView.setText(id_request);
                        customerView.setText(riderName);


                        //driverLat = Double.parseDouble(dlat);
                        //driverlog = Double.parseDouble(dlog);

                        //infoText.setText("Request Number is : " + id_request + "  -  " + "Your rider is : " + riderName);


                    } else {

                        // Toast.makeText(getApplicationContext(), "Please wait, We will call you in 3 mintues", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetialsActivity.this);
                        builder.setMessage("Error in bring data")
                                .setNegativeButton("OK", null)
                                .create()
                                .show();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        OrderDetialsRequest tripRequest = new OrderDetialsRequest(requetID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(OrderDetialsActivity.this);
        queue.add(tripRequest);


        // CALL Cutomer Tapped
        callCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel:" + riderMobile));

                if (ActivityCompat.checkSelfPermission(OrderDetialsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);



            }
        });

        // Google MAp Tapped
        googlemapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+picklatitude+"&daddr="+picklongitude));
                startActivity(intent);



            }
        });

        // Receive Order Button Tapped
        receivedOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Cancel request from database

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {


                                // go to Home active
                                Intent intent = new Intent(OrderDetialsActivity.this, MyOrdersActivity.class);
                                OrderDetialsActivity.this.startActivity(intent);


                                //id = jsonResponse.getString("id");
                                // String name = jsonResponse.getString("fullname");
                                //String email = jsonResponse.getString("email");
                                //String mobile = jsonResponse.getString("mobile");

                                Toast.makeText(getApplicationContext(), "Order has been received", Toast.LENGTH_SHORT).show();


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetialsActivity.this);
                                builder.setMessage("Delete Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                OrderReceiceRequest receiveOrderRequest = new OrderReceiceRequest(requetID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(OrderDetialsActivity.this);
                queue.add(receiveOrderRequest);




            }
        });


        // Cancel Order Tapped
        cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Cancel request from database

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {


                                // go to Home active
                                Intent intent = new Intent(OrderDetialsActivity.this, MyOrdersActivity.class);
                                OrderDetialsActivity.this.startActivity(intent);


                                //id = jsonResponse.getString("id");
                                // String name = jsonResponse.getString("fullname");
                                //String email = jsonResponse.getString("email");
                                //String mobile = jsonResponse.getString("mobile");

                                Toast.makeText(getApplicationContext(), "Order has been cancelled", Toast.LENGTH_SHORT).show();


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetialsActivity.this);
                                builder.setMessage("Delete Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                OrderCancelRequest cancelTripRequest = new OrderCancelRequest(requetID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(OrderDetialsActivity.this);
                queue.add(cancelTripRequest);





            }
        });



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
                Intent changeActivity = new Intent (OrderDetialsActivity.this, MyOrdersActivity.class);
                OrderDetialsActivity.this.startActivity(changeActivity);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
