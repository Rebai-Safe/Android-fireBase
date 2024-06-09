package com.example.projectif4firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectif4firebase.adapters.TemperatureAdapter;
import com.example.projectif4firebase.models.Temperature;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddListTemperature extends AppCompatActivity {

    TextView dateProgrammeText;
    EditText temperatureText;
    Button btnAddTemp;
    Intent intent;
    //listeViewTemperature
    ListView listViewTemperatures;
    //hold the list of temperature
    List<Temperature> temperaturesList;
    //
    DatabaseReference databaseTemperatures;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_temperature);
        setTitle("Température ");

        //get views ref
        dateProgrammeText = findViewById(R.id.date_pgr_view);
        temperatureText = findViewById(R.id.temperature_input);
        listViewTemperatures = findViewById(R.id.temperatures_list_view);
        btnAddTemp=findViewById(R.id.btn_add_temp);
        //init temperature List
        temperaturesList = new ArrayList<>();

        //get Intent
        intent = getIntent();
        //getIntentFields
        String pgr_id = intent.getStringExtra(AddListProgramme.ID_PROGRAMME);
        String date_pgr = intent.getStringExtra(AddListProgramme.DATE_PROGRAMME);
       //setViews
        dateProgrammeText.setText("Programme de date "+date_pgr);

        /*
         * this line is important
         * this time we are not getting the reference of a direct node
         * but inside the node température we are creating a new child with the pgr id
         * and inside that node we will store all the temperatures with unique ids
         * */
        databaseTemperatures = FirebaseDatabase.getInstance().getReference("température").child(pgr_id);

        btnAddTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTemprature();
            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();

        //track value changes
        databaseTemperatures.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 //clear the List
                temperaturesList.clear();
                //loop through SnAPSHOT THAT contains all the data
                for(DataSnapshot temperatureSnapshot : dataSnapshot.getChildren()){
                    Temperature temp = temperatureSnapshot.getValue(Temperature.class);
                    //fill the list
                    temperaturesList.add(temp);
                }
             //create New Adapter
                TemperatureAdapter temperatureAdapter = new TemperatureAdapter(AddListTemperature.this,temperaturesList);
                //set adapter for our listView
                listViewTemperatures.setAdapter(temperatureAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void  saveTemprature(){
    //get user Entries
    float degres = (float) Double.parseDouble(temperatureText.getText().toString());
    //save Temperature
    String identifier = databaseTemperatures.push().getKey();
    Temperature temperature = new Temperature(identifier,degres);
    databaseTemperatures.child(identifier).setValue(temperature);//displaying a success toast
    Toast.makeText(this, "Temperature Enregistré", Toast.LENGTH_LONG).show();

    if(degres>=40){
        showDialog();

    }


}

    //confirmer  ou non l'envoi du message
    private void showDialog(){
        //inflate layout
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_envoi_sms,null);
        //this dialog will have this layout we build
        builder.setView(view);

        final AlertDialog alertdialog;
        //get buttons Ref
        Button ok_envoi = view.findViewById(R.id.ok_envoi_sms);
        Button non_envoi = view.findViewById(R.id.non_envoi_sms);

        alertdialog = builder.create();
        alertdialog.show();

        ok_envoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Built-in SMS application intent

                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                startActivity(smsIntent);


            }
        });


        non_envoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alertdialog.hide();


            }
        });



}
}
