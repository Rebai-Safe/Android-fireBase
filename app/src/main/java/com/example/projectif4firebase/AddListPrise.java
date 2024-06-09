package com.example.projectif4firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectif4firebase.adapters.PrisesAdapter;
import com.example.projectif4firebase.models.Prise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddListPrise extends AppCompatActivity {


    DatePickerDialog datePickerDialog;
    TextView ref_med_prise_text;
    EditText heure_prise_text;
    EditText description_prise_text;
    EditText qte_prise_text;
    Button btnAddPrise;
    EditText date_prise;
    Intent intent;

    Calendar calendar;
    String dateString;
    //listeViewPrises
    ListView listViewPrises;
    //hold the list of temperature
    List<Prise> prisesList;
    //
    DatabaseReference databasePrises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prise);

        //getRefs
        ref_med_prise_text = findViewById(R.id.ref_med_prise_view);
        heure_prise_text = findViewById(R.id.heure_prise_input);
        description_prise_text = findViewById(R.id.description_input);
        qte_prise_text = findViewById(R.id.qte_prise_input);
        btnAddPrise = findViewById(R.id.btn_add_prise);
        date_prise= findViewById(R.id.date_prise_input);
        listViewPrises = findViewById(R.id.prises_list_view);


        calendar = Calendar.getInstance();

        //popUp date dialog and set date
        date_prise.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    final Calendar cldr = Calendar.getInstance();
                                                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                                                    final int month = cldr.get(Calendar.MONTH);
                                                    int year = cldr.get(Calendar.YEAR);
                                                    // date picker dialog
                                                    datePickerDialog = new DatePickerDialog(AddListPrise.this,
                                                            new DatePickerDialog.OnDateSetListener() {
                                                                @Override
                                                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                                    calendar.set(year,month,dayOfMonth);
                                                                    date_prise.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                                                }
                                                            }, year, month, day);
                                                    datePickerDialog.show();



                                                }
                                            }
        );







        //get Intent
        intent = getIntent();
        //getIntentFields
        String ref_med = intent.getStringExtra(AddListMedicament.REF_MED);
        String id_med = intent.getStringExtra(AddListMedicament.ID_MEDICAMANT);
        /* init List*/
        prisesList = new ArrayList<>();
   /* set ref texte*/
        ref_med_prise_text.setText("Prise du médicament "+ref_med);

        /*
         */
        /* inside the node prises we are creating a new child with the medicament id
         * and inside that node we will store  with unique ids
         */
        databasePrises = FirebaseDatabase.getInstance().getReference("prises").child(id_med);

        btnAddPrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();

            }
        });


    }

//confirmer la géneration ou non du rappel pour cette prise
private void showDialog(){
    //inflate layout
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    LayoutInflater inflater = getLayoutInflater();
    View view = inflater.inflate(R.layout.confirmation_rappel_prise,null);
    //this dialog will have this layout we build
       builder.setView(view);

    final AlertDialog alertdialog;
    //get buttons Ref
    Button ok_rappel = view.findViewById(R.id.ok_rappel_prise);
    Button non_rappel = view.findViewById(R.id.non_rappel_prise);

    alertdialog = builder.create();
    alertdialog.show();

      ok_rappel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //setAlaram Manger to schdule task via braodcast reveiver
            //getting the alarm manager
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            //creating a new intent specifying the broadcast receiver
            Intent intent = new Intent(getApplicationContext(),PriseReminder.class);
            intent.putExtra("HeurePrise",Integer.parseInt(heure_prise_text.getText().toString()));

            //creating a pending intent using the intent
            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

            //setting the  alarm  on that date
            am.setExact(AlarmManager.RTC, calendar.getTimeInMillis(),  pi);

            alertdialog.hide();
            savePrises();

        }
    });


       non_rappel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            alertdialog.hide();
            savePrises();

        }
    });






}




    @Override
    protected void onStart() {
        super.onStart();

        //track value changes
        databasePrises.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear the List
                prisesList.clear();
                //loop through SnAPSHOT THAT contains all the data
                for(DataSnapshot priseSnapshot : dataSnapshot.getChildren()){
                    Prise prise = priseSnapshot.getValue(Prise.class);
                    //fill the list
                    prisesList.add(prise);
                }
                //create New Adapter
                PrisesAdapter priseAdapter = new PrisesAdapter(AddListPrise.this,prisesList);
                //set adapter for our listView
                listViewPrises.setAdapter(priseAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void  savePrises(){
        //genreate id
        String identifier = databasePrises.push().getKey();
        //convert user entries
        int heure = Integer.parseInt(heure_prise_text.getText().toString());
        float qte = (float) Double.parseDouble(qte_prise_text.getText().toString());
        String description = description_prise_text.getText().toString();
        dateString = date_prise.getText().toString();

        //
        Prise prise = new Prise(identifier,dateString,description,heure,qte);
        //append to db
        databasePrises.child(identifier).setValue(prise);
        Toast.makeText(this, "Prise Enregistré", Toast.LENGTH_LONG).show();



    }
}
