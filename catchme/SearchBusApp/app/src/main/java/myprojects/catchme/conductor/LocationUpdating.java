package myprojects.catchme.conductor;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import myprojects.catchme.other.Settings;

/**
 * Created by Nadee on 9/23/2016.
 */
public class LocationUpdating extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    LocationUpdating(Context ctx){
        context=ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type=params[0];
        String login_url= Settings.SERVER_URL +"/updateLocation.php";
        if(type.equals("update")){
            try {
                String bus=params[1];
                String date_time=params[2];
                String longitude=params[3];
                String latitude=params[4];
                String is_started=params[5];
                String is_closed=params[6];

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("bus","UTF-8")+"="+URLEncoder.encode(bus,"UTF-8")+"&"
                        +URLEncoder.encode("date_time","UTF-8")+"="+URLEncoder.encode(date_time,"UTF-8")+"&"
                        +URLEncoder.encode("longitude","UTF-8")+"="+URLEncoder.encode(longitude,"UTF-8")+"&"
                        +URLEncoder.encode("latitude","UTF-8")+"="+URLEncoder.encode(latitude,"UTF-8")+"&"
                        +URLEncoder.encode("is_started","UTF-8")+"="+URLEncoder.encode(is_started,"UTF-8")+"&"
                        +URLEncoder.encode("is_closed","UTF-8")+"="+URLEncoder.encode(is_closed,"UTF-8")+"&";
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line=bufferedReader.readLine())!=null){
                    result +=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute(){
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status Update");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
