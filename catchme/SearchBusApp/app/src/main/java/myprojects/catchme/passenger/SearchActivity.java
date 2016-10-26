package myprojects.catchme.passenger;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RatingBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import myprojects.catchme.R;
import myprojects.catchme.other.Rating;
import myprojects.catchme.other.Settings;

import android.widget.RatingBar.OnRatingBarChangeListener;

public class SearchActivity extends AppCompatActivity {

    public final static String ORIGIN = "ORIGIN";
    public final static String DESTINATION = "DESTINATION";
    AutoCompleteTextView origin;
    AutoCompleteTextView destination;
    EditText rNumber = null;

    ArrayList<String> places = new ArrayList<String>();


    private RatingBar ratingBar;
    private int ratingVal=0;
    private AutoCompleteTextView busNoAuto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Search Buses");
        origin = (AutoCompleteTextView)findViewById(R.id.txtOrigin);
        destination = (AutoCompleteTextView)findViewById(R.id.txtDestination);
        rNumber = (EditText)findViewById(R.id.txtNumber);

        addListenerOnRatingBar();

        busNoAuto=(AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        GetPlaces gplaces =new GetPlaces();
        gplaces.execute();
    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.busRatingBar);

        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,boolean fromUser) {
                ratingVal=(int)rating;
            }
        });
    }

    public void onBtnClick(View v)
    {
        String originPlace = origin.getText().toString();
        String destinationPlace = destination.getText().toString();

        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra(ORIGIN, originPlace);
        intent.putExtra(DESTINATION, destinationPlace);
        startActivity(intent);
    }

    public void onRouteBtnClick(View v)
    {
        String busNumber = rNumber.getText().toString();
        GetNameRoutes gplaces = new GetNameRoutes();
        gplaces.execute(busNumber);
    }

    public void onRateBtnClick(View v){
        String busNo= String.valueOf(busNoAuto.getText());
        Rating rating=new Rating(this);
        rating.execute("rateBus",busNo,""+ratingVal);
        busNoAuto.setText("");
        ratingBar.setRating(0);
    }

    public void openMapForRoute(String origin, String destination)
    {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra(ORIGIN, origin);
        intent.putExtra(DESTINATION, destination);
        startActivity(intent);
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

                openMapForRoute( origin, destination );

            }catch (Exception e) {
                e.printStackTrace();
            }
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
            parseResultJSon( res );
        }
    }

    public void parseResultJSon( String result )
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

    /*----------Method to create an SessionHolder ------------- */
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mymessage)
                .setCancelable(false)
                .setTitle(title)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
