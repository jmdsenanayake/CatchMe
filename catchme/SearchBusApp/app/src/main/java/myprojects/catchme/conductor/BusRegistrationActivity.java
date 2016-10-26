package myprojects.catchme.conductor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import myprojects.catchme.common.MainActivity;
import myprojects.catchme.R;

public class BusRegistrationActivity extends AppCompatActivity {
    EditText EtbusName,EtbusNo,Etroute,EtownerName,EtownerNIC,EtownerContact,Etpword,EtbusType,Etseats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_registration);

        EtbusName=(EditText)findViewById(R.id.busName);
        EtbusNo=(EditText)findViewById(R.id.busNo);
        Etroute=(EditText)findViewById(R.id.route);
        EtownerName=(EditText)findViewById(R.id.ownerName);
        EtownerNIC=(EditText)findViewById(R.id.ownerNIC);
        EtownerContact=(EditText)findViewById(R.id.ownerContact);
        Etpword=(EditText)findViewById(R.id.password);
        EtbusType=(EditText)findViewById(R.id.busType);
        Etseats=(EditText)findViewById(R.id.seats);


    }

    public void onRegister(View view){
        String busName=EtbusName.getText().toString();
        String busNo=EtbusNo.getText().toString();
        String route=Etroute.getText().toString();
        String ownerName=EtownerName.getText().toString();
        String ownerNIC=EtownerNIC.getText().toString();
        String ownerContact=EtownerContact.getText().toString();
        String password=Etpword.getText().toString();
        String busType=EtbusType.getText().toString();
        String seats=Etseats.getText().toString();

        BusRegister busRegister=new BusRegister(this);
        String type="registerBus";
        busRegister.execute(type,busName,busNo,route,ownerName,ownerNIC,ownerContact,password,busType,seats);

    }

    public void onRegisterHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);;
    }
}
