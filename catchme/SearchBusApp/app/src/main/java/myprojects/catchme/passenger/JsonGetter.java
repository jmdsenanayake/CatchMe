package myprojects.catchme.passenger;

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
 * Created by admin on 9/23/2016.
 */
public class JsonGetter extends AsyncTask<String, Void, String>
{
    @Override
    protected String doInBackground(String... params)
    {
        String serverUrl= Settings.SERVER_URL+ "/getBusInfo.php";
        String result="";
        try
        {
            String o_place=params[0];
            String d_place=params[1];
            URL url = new URL(serverUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String post_data= URLEncoder.encode("o_place","UTF-8")+"="+URLEncoder.encode(o_place,"UTF-8")+"&"
                    +URLEncoder.encode("d_place","UTF-8")+"="+URLEncoder.encode(d_place,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String line="";
            while ((line=bufferedReader.readLine())!=null){
                result +=line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
