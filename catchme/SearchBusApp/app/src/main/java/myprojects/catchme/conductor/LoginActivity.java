package myprojects.catchme.conductor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import myprojects.catchme.other.AsyncResponse;
import myprojects.catchme.common.MainActivity;
import myprojects.catchme.R;

public class LoginActivity extends Activity implements AsyncResponse {
    public final static String BUS_NO = "";
    EditText UsernameEt,PasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameEt=(EditText)findViewById(R.id.username);
        PasswordEt=(EditText)findViewById(R.id.password);
    }

    public void onLogin(View view){
        String username=UsernameEt.getText().toString();
        String password=PasswordEt.getText().toString();

       LoginValidation loginValidation=new LoginValidation(this);
        String type="login";
        loginValidation.delegate=this;
        loginValidation.execute(username,password,type);
    }

    @Override
    public void processFinish(String output) {
        if(!(output.equals(""))){
            Intent intent = new Intent(this, TicketActivity.class);
            startActivity(intent);
        }
    }

    public void onRegistration(View view){
        Intent intent = new Intent(this, BusRegistrationActivity.class);
        startActivity(intent);
    }

    public void onRegisterHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);;
    }
}
