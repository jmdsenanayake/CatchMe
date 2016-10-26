package myprojects.catchme.conductor;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import myprojects.catchme.R;
import myprojects.catchme.other.SessionHolder;
import myprojects.catchme.other.Settings;

public class TicketActivity extends AppCompatActivity implements View.OnClickListener{
    private static double totalTicketValue=0;

    ArrayList<String> places = new ArrayList<String>();
    AutoCompleteTextView origin;
    AutoCompleteTextView destination;

    TextView EdTicketFees,busValueTxt;
    EditText EdLatitude,EdLongitude,EdRoute;
    LocationUpdating locationUpdating;

    private LocationManager locationMangaer = null;
    private LocationListener locationListener = null;

    private Button btnStartJourney = null;
    private Button btnStopJourney = null;
    private ProgressBar pb = null;

    private static final String TAG = "Debug";
    private Boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        setTitle("Ticket Manager");
        origin = (AutoCompleteTextView)findViewById(R.id.origin);
        destination = (AutoCompleteTextView)findViewById(R.id.destination);
        EdTicketFees = (TextView)findViewById(R.id.ticketFees);
        busValueTxt = (TextView)findViewById(R.id.busValue);

        GetPlaces gplaces =new GetPlaces();
        gplaces.execute();


        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.BUS_NO);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        btnStartJourney = (Button) findViewById(R.id.btnStart);

        btnStopJourney = (Button) findViewById(R.id.btnStop);
        btnStopJourney.setVisibility(View.VISIBLE);

        btnStartJourney.setOnClickListener(this);

        locationMangaer = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationUpdating=new LocationUpdating(this);


        destination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                EdTicketFees.setText("Rs. 20.00");
            }
        });
    }

    public void addTicket(View v)
    {
        String originPlace = origin.getText().toString();
        String destinationPlace = destination.getText().toString();

        SaveTicket st =new SaveTicket();
        st.execute(originPlace, destinationPlace, "20");

        //origin.setText("");
        //destination.setText("");;
        EdTicketFees.setText("Rs. 0");

        totalTicketValue+=20;
        busValueTxt.setText("Rs. "+totalTicketValue);

    }

    public void parsePlacesJSon( String result )
    {
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray rArray = jObject.getJSONArray("places");
            places.clear();

            for (int i = 0; i < rArray.length(); i++) {
                try {
                    JSONObject place = rArray.getJSONObject(i);
                    String pname = place.getString("name");
                    places.add(pname);
                } catch (Exception e) {
                    // Oops
                }
            }

            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,places);

            origin.setAdapter(adapter);
            origin.setThreshold(1);

            destination.setAdapter(adapter);
            destination.setThreshold(1);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetPlaces extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //String serverUrl="http://192.168.43.164:2407/BusApp/getBusInfo.php";
            String serverUrl= Settings.SERVER_URL +"/getPlaces.php";

            String result="";

            try {
                URL url = new URL(serverUrl);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res)
        {
            parsePlacesJSon( res );
        }
    }

    private class SaveTicket extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //String serverUrl="http://192.168.43.164:2407/BusApp/getBusInfo.php";
            String serverUrl= Settings.SERVER_URL +"/saveTicket.php?";
            serverUrl += "r="+ SessionHolder.ROUTE_ID +"&b="+ SessionHolder.BUS_ID +"&o=" + params[0] + "&d=" + params[1] + "&a=" + params[2];

            System.out.println(serverUrl);

            String result="";

            try {
                URL url = new URL(serverUrl);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res)
        {
            System.out.println("--------------------------------------------------------");
            System.out.println(res);
        }
    }

    private class GetNameRoutes extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //String serverUrl="http://192.168.43.164:2407/BusApp/getRouteNames.php";
            String serverUrl= Settings.SERVER_URL +"/getRouteNames.php?rnum=" + params[0];
            System.out.println(serverUrl);
            String result="";

            try {
                URL url = new URL(serverUrl);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res)
        {
            try {
                JSONObject jObject = new JSONObject(res);
                String origin = "";
                String destination = "";

                JSONArray rArray = jObject.getJSONArray("places");

                for (int i = 0; i < rArray.length(); i++) {
                    try {
                        JSONObject place = rArray.getJSONObject(i);
                        origin = place.getString("origin");
                        destination = place.getString("destination");
                    } catch (Exception e) {
                        // Oops
                    }
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onStop(View view){

        btnStopJourney.setVisibility(View.INVISIBLE);
        btnStartJourney.setVisibility(View.VISIBLE);

        pb.setVisibility(View.INVISIBLE);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        totalTicketValue=0;

    }

    @Override
    public void onClick(View v) {

        flag = displayGpsStatus();
        if (flag) {

            Log.v(TAG, "onClick");


            pb.setVisibility(View.VISIBLE);
            btnStopJourney.setEnabled(true);
            btnStartJourney.setEnabled(false);
            locationListener = new MyLocationListener();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

    }

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = android.provider.Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    /*----------Method to create an SessionHolder ------------- */
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        android.provider.Settings.ACTION_SECURITY_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    /*----------Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {

            // editLocation.setText("");
            // EdLatitude.setText("");
            //EdLongitude.setText("");
            pb.setVisibility(View.INVISIBLE);
            Toast.makeText(getBaseContext(),"Location changed : Lat: " +
                            loc.getLatitude()+ " Lng: " + loc.getLongitude(),
                    Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " +loc.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " +loc.getLatitude();
            Log.v(TAG, latitude);



            String s = longitude+"\n"+latitude;
            //editLocation.setText(s);
            //EdLatitude.setText(latitude);
            //EdLongitude.setText(longitude);

            String busNo = ""+SessionHolder.BUS_ID;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = sdf.format(new Date());


            String type="update";

            locationUpdating.execute(type,busNo,currentDateTime,""+loc.getLongitude(),""+loc.getLatitude(),"1","0");

            // UpdateDB(longitude,latitude);

        }


        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }


}

