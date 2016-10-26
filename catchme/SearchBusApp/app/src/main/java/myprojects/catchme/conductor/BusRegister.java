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
 * Created by Nadee on 9/24/2016.
 */
public class BusRegister extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    BusRegister(Context ctx){
        context=ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type=params[0];
        String login_url= Settings.SERVER_URL +"/registerBus.php";
        if(type.equals("registerBus")){
            try {
                String busName=params[1];
                String busNo=params[2];
                String route=params[3];
                String ownerName=params[4];
                String ownerNIC=params[5];
                String ownerContact=params[6];
                String uname=params[2];
                String pword=params[7];
                String busType=params[8];
                String seats=params[9];

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("busName","UTF-8")+"="+URLEncoder.encode(busName,"UTF-8")+"&"
                        +URLEncoder.encode("busNo","UTF-8")+"="+URLEncoder.encode(busNo,"UTF-8")+"&"
                        +URLEncoder.encode("route","UTF-8")+"="+URLEncoder.encode(route,"UTF-8")+"&"
                        +URLEncoder.encode("ownerName","UTF-8")+"="+URLEncoder.encode(ownerName,"UTF-8")+"&"
                        +URLEncoder.encode("ownerNIC","UTF-8")+"="+URLEncoder.encode(ownerNIC,"UTF-8")+"&"
                        +URLEncoder.encode("ownerContact","UTF-8")+"="+URLEncoder.encode(ownerContact,"UTF-8")+"&"
                        +URLEncoder.encode("uname","UTF-8")+"="+URLEncoder.encode(uname,"UTF-8")+"&"
                        +URLEncoder.encode("pword","UTF-8")+"="+URLEncoder.encode(pword,"UTF-8")+"&"
                        +URLEncoder.encode("busType","UTF-8")+"="+URLEncoder.encode(busType,"UTF-8")+"&"
                        +URLEncoder.encode("seats","UTF-8")+"="+URLEncoder.encode(seats,"UTF-8")+"&";
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
        //alertDialog.setTitle("Status Update");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
       // alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

