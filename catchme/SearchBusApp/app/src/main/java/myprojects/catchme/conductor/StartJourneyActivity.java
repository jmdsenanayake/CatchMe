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
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import myprojects.catchme.R;

public class StartJourneyActivity extends AppCompatActivity implements View.OnClickListener {
    EditText EdBusNo,EdLatitude,EdLongitude,EdRoute;
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
        setContentView(R.layout.activity_start_journey);

        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.BUS_NO);
        EdBusNo=(EditText) findViewById(R.id.txtBusNo);
        EdRoute=(EditText) findViewById(R.id.route);
        EdBusNo.setText(message);
        EdBusNo.setEnabled(false);
        EdBusNo.setVisibility(View.INVISIBLE);
        //EdLatitude.setVisibility(View.INVISIBLE);
        //EdLongitude.setVisibility(View.INVISIBLE);
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        btnStartJourney = (Button) findViewById(R.id.btnStart);

        btnStopJourney = (Button) findViewById(R.id.btnStop);
        btnStopJourney.setVisibility(View.VISIBLE);

        btnStartJourney.setOnClickListener(this);

        locationMangaer = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationUpdating=new LocationUpdating(this);
    }



    public void onStop(View view){

        btnStopJourney.setVisibility(View.INVISIBLE);
        btnStartJourney.setVisibility(View.VISIBLE);

        pb.setVisibility(View.INVISIBLE);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

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
        boolean gpsStatus = Settings.Secure
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
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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

            String busNo=EdBusNo.getText().toString();

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
