package myprojects.catchme.other;

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

/**
 * Created by Janaka on 10/9/2016.
 */
public class Rating extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    public Rating(Context ctx){
        context=ctx;
    }
    public Rating(){}

    @Override
    protected String doInBackground(String... params) {
        String type=params[0];
        String login_url= Settings.SERVER_URL +"/rateBus.php";
        if(type.equals("rateBus")){
            try {
                String busNo=params[1];
                String ratingVal=params[2];

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("busNo","UTF-8")+"="+URLEncoder.encode(busNo,"UTF-8")+"&"
                        +URLEncoder.encode("ratingVal","UTF-8")+"="+URLEncoder.encode(ratingVal,"UTF-8");
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
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage("Thanks for Rating");
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
