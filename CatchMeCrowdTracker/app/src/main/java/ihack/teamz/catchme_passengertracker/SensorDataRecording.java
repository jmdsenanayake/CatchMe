package ihack.teamz.catchme_passengertracker;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Janaka on 02/10/2016.
 */
public class SensorDataRecording extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    SensorDataRecording(Context ctx){
        context=ctx;
    }
    SensorDataRecording(){
    }

    @Override
    protected String doInBackground(String... params) {
        String type=params[0];
        //String addUser_URL="http://192.168.43.109/catch_me/sensorData.php";
        String addUser_URL="http://www.catchme.esy.es/catch_me/sensorData.php";
        if(type.equals("addSensorData")){
            try {
                String busNo=params[1];
                String sensorNo=params[2];

                URL url = new URL(addUser_URL);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("busNo","UTF-8")+"="+URLEncoder.encode(busNo,"UTF-8")+"&"
                        +URLEncoder.encode("sensorNo","UTF-8")+"="+URLEncoder.encode(sensorNo,"UTF-8");
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

                System.out.println(post_data);
                return result;
            }
            catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute(){
    }

    @Override
    protected void onPostExecute(String result) {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

