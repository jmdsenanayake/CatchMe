package myprojects.catchme.passenger;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import myprojects.catchme.R;
import myprojects.catchme.other.Route;
import myprojects.catchme.other.Settings;
import myprojects.catchme.other.Distance;
import myprojects.catchme.other.Duration;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            Intent intent = getIntent();
            String origin = intent.getStringExtra(SearchActivity.ORIGIN);
            String destination = intent.getStringExtra(SearchActivity.DESTINATION);

            String jsonResult = getResultJSON(origin, destination);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getResultJSON (String origin, String destination)
    {
        String result = "";
        DownloadJson jsonGetter=new DownloadJson();
        FindRouteJSON rjsonGetter=new FindRouteJSON();

        if(origin!=null && destination !=null)
            rjsonGetter.execute(origin,destination);

        if(origin!=null && destination !=null)
            jsonGetter.execute(origin,destination);

        return(result);
    }

    private class DownloadJson extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //String serverUrl="http://192.168.43.164:2407/BusApp/getBusInfo.php";
            String serverUrl= Settings.SERVER_URL +"/getBusInfo.php";

            String result="";

            try {
                String o_place=params[0];
                String d_place=params[1];
                serverUrl += "?o_place=" + o_place + "&d_place=" + d_place;
                System.out.println("-------------------------------");
                System.out.println(serverUrl);
                System.out.println("-------------------------------");
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
            parseResultJSon(res);
        }
    }

    public void parseResultJSon( String result )
    {
        try {
            JSONObject jObject = new JSONObject(result);

            if (jObject.equals(null))
                alertbox("Sorry", "No buses found");

            JSONArray rArray = jObject.getJSONArray("results");

            for (int i = 0; i < rArray.length(); i++) {
                try {
                    JSONObject bus = rArray.getJSONObject(i);
                    // Pulling items from the array
                    //String busName = bus.getString("busName") + "( Capacity: " + bus.getString("capacity") + ", Occupancy: " + bus.getString("crowd") +")";
                    String busName = bus.getString("busName") + "( Capacity: " + bus.getString("capacity") + ", Occupancy: " + bus.getString("crowd") +", Rating:" + bus.getString("rating")+")";
                    Integer busType = bus.getInt("btype");
                    Double longitude = bus.getDouble("longitude");
                    Double lattitude = bus.getDouble("lattitude");

                    // Add a marker in Sydney and move the camera
                    LatLng busPoint = new LatLng(lattitude, longitude);

                    if( busType == 1 )
                        mMap.addMarker(new MarkerOptions().position(busPoint).title(busName).icon(BitmapDescriptorFactory.fromResource(R.mipmap.greenbus)));
                    else if( busType == 2 )
                        mMap.addMarker(new MarkerOptions().position(busPoint).title(busName).icon(BitmapDescriptorFactory.fromResource(R.mipmap.yellowbus)));
                    else
                        mMap.addMarker(new MarkerOptions().position(busPoint).title(busName).icon(BitmapDescriptorFactory.fromResource(R.mipmap.redbus)));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(busPoint,15));

                } catch (Exception e) {
                    // Oops
                }
            }

            Double o_long = jObject.getDouble("o_long");
            Double o_lat = jObject.getDouble("o_lat");
            LatLng sp = new LatLng(o_lat, o_long);
            Double d_long = jObject.getDouble("d_long");
            Double d_lat = jObject.getDouble("d_lat");
            LatLng ep = new LatLng(d_lat, d_long);

            //mMap.addMarker(new MarkerOptions().position(sp).icon(BitmapDescriptorFactory.fromResource(R.mipmap.starticon)));
            //mMap.addMarker(new MarkerOptions().position(ep).icon(BitmapDescriptorFactory.fromResource(R.mipmap.endicon)));

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class FindRouteJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String routeUrl = "https://maps.googleapis.com/maps/api/directions/json";
            String result="";

            try {
                String o_place=params[0];
                String d_place=params[1];
                routeUrl += "?origin=" + o_place + "&destination=" + d_place + "&key=" + Settings.GOOGLE_API_KEY;
                System.out.println("------------------------");
                System.out.println(routeUrl);
                URL url = new URL(routeUrl);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res)
        {
            try {
                parseRouteJSon(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseRouteJSon(String data) throws JSONException
    {
        if (data == null)
            return;

        List<Route> routes = new ArrayList<Route>();
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
            Route route = new Route();

            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
            JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
            JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
            JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
            JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

            route.distance = new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"));
            route.duration = new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"));
            route.endAddress = jsonLeg.getString("end_address");
            route.startAddress = jsonLeg.getString("start_address");
            route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
            route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
            route.points = decodePolyLine(overview_polylineJson.getString("points"));

            routes.add(route);
        }

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 15));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.rgb(204,0,224)).
                    width(15);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            mMap.addPolyline(polylineOptions);
        }
    }

    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
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
