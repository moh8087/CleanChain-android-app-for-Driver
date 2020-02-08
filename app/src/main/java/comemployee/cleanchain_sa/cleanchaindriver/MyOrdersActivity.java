package comemployee.cleanchain_sa.cleanchaindriver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {

    String rid = "1063";

    //adapter class
    ArrayList<AdapterItems> listnewsData = new ArrayList<AdapterItems>();
    MyCustomAdapter myadapter;

    ArrayList<String> myTrips = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);


        final ListView lvlis = (ListView) findViewById(R.id.lvlist);

        // identify user information
        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        rid = sharedPreferences.getString(Config.ID_SHARED_PREF, "Not Available");







        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {




                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String posts = jsonResponse.getString("posts");


                    // Getting JSON Array node
                    JSONArray contacts = jsonResponse.getJSONArray("posts");




                    if (success) {

                        for (int i = 0; i < contacts.length(); i++) {
                            try {
                                JSONObject c = contacts.getJSONObject(i);

                                String id = c.getString("id");
                                String notes = c.getString("notes");
                                String price = c.getString("totalcharge");
                                String status = c.getString("status");
                                String created_at = c.getString("created_at");

                                /*String id = jsonResponse.getString("id");
                                String dname = jsonResponse.getString("dname");
                                String dmobile = jsonResponse.getString("dmobile");
                                String pickaddress = jsonResponse.getString("pickaddress");
                                String totalcharge = jsonResponse.getString("totalcharge");
                                //String status = jsonResponse.getString("status");
                                String created_at = jsonResponse.getString("created_at");*/


                                Log.i("PlaceInfo", id);
                                Log.i("PlaceInfo", price);
                                Log.i("PlaceInfo", created_at);

                                //System.out.print(id + " " + price);



                                //add data and view it
                                listnewsData.add(new AdapterItems(id, notes, price, status, created_at));
                                //listnewsData.add(new AdapterItems(2,"developer"," develop apps"));
                                //listnewsData.add(new AdapterItems(2,"developer"," develop apps"));

                                myadapter = new MyCustomAdapter(listnewsData);
                                lvlis.setAdapter(myadapter);


                                //myTrips.add( id);
                                //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MyOrdersActivity.this, android.R.layout.simple_list_item_1, myTrips);
                                //lvlis.setAdapter(arrayAdapter);





                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }





                        //myTrips.add(jsonResponse.getString("posts"));




                        //myTrips.add(jsonResponse.getString("posts"));







                        //TripsListView.setAdapter(arrayAdapter);






                    } else {

                        // Toast.makeText(getApplicationContext(), "Please wait, We will call you in 3 mintues", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MyOrdersActivity.this);
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

        MyOrdersRequest myOrdersRequest = new MyOrdersRequest(rid, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyOrdersActivity.this);
        queue.add(myOrdersRequest);





    }


    //display news list
    private class MyCustomAdapter extends BaseAdapter {
        public  ArrayList<AdapterItems>  listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<AdapterItems>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layout_orders, null);

            final AdapterItems s = listnewsDataAdpater.get(position);

            TextView txtOrderID=( TextView) myView.findViewById(R.id.orderIDView);
            txtOrderID.setText(s.OrderID);

            TextView txtNotes=( TextView) myView.findViewById(R.id.notesText);
            txtNotes.setText(s.Notes);


            TextView txtPrice =( TextView)myView.findViewById(R.id.orderPriceView);
            txtPrice.setText(s.Price);

            TextView txtStatus =( TextView)myView.findViewById(R.id.statusView);
            txtStatus.setText(s.status);

            TextView txtCreatedAt =( TextView)myView.findViewById(R.id.orderDateView);
            txtCreatedAt.setText(s.created_at);

            Button displayOrderButton = (Button) myView.findViewById(R.id.displayOrderButton);

            displayOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // save request id to general varibal
                    Config.Request_ID = s.OrderID;

                    if (s.status.equals("completed"))
                    {
                        Toast.makeText(getApplicationContext(), "Sorry, Order is already completed", Toast.LENGTH_SHORT).show();

                    }
                    else // go to TripActivity
                    {
                        Intent intent = new Intent(MyOrdersActivity.this, OrderDetialsActivity.class);
                        MyOrdersActivity.this.startActivity(intent);

                        //Toast.makeText(getApplicationContext(), "Hello " + s.TripID , Toast.LENGTH_LONG).show();

                    }

                }
            });




            return myView;
        }

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
                Intent changeActivity = new Intent (MyOrdersActivity.this, MainActivity.class);
                MyOrdersActivity.this.startActivity(changeActivity);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
