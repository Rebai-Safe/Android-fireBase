package com.example.projectif4firebase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projectif4firebase.adapters.ProgrammeAdapter;
import com.example.projectif4firebase.models.Programme;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddListProgramme extends AppCompatActivity {

    //constants to pass to intent for temperature Activity
    public static final String DATE_PROGRAMME = "date_pgr";
    public static final String ID_PROGRAMME = "id_pgr";


    //
    DatePickerDialog datePickerDialog;
    EditText date_debut;
    EditText dureeText;
    EditText maladieText;
    String dateString;
    Button btnAdd;
    List programmesList;
    ListView listeViewprogrammes;
    DatabaseReference databaseProgrammes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_programme);
        setTitle("Nouveau programme");
        date_debut = findViewById(R.id.date_debut_input);
        dureeText = findViewById(R.id.duree_input);
        maladieText = findViewById(R.id.maladie_input);
        btnAdd = findViewById(R.id.btn_add_pgr);

        //listeView
        listeViewprogrammes = findViewById(R.id.pgr_list_view);

        //
        databaseProgrammes = FirebaseDatabase.getInstance().getReference("programme");
         /*
        //get Date fields
        day = date_debut.getDayOfMonth();
        month = date_debut.getMonth();
        year = date_debut.getYear();

        //make new Calendar
        calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        //convert date To string
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.set(year, month, day);
        dateString = sdf.format(calendar.getTime());
*/



         date_debut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                   final Calendar cldr = Calendar.getInstance();
                   int day = cldr.get(Calendar.DAY_OF_MONTH);
                   int month = cldr.get(Calendar.MONTH);
                   int year = cldr.get(Calendar.YEAR);
                   // date picker dialog
               Locale locale = getResources().getConfiguration().locale;
               Locale.setDefault(locale);
               datePickerDialog = new DatePickerDialog(AddListProgramme.this,
                           new DatePickerDialog.OnDateSetListener() {
                               @Override
                               public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                   date_debut.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                               }
                           }, year, month, day);
               datePickerDialog.show();



             }
            }
         );







        //list to store programmes
        programmesList = new ArrayList<Programme>();
        //add button click listener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addProgrammes()
                //the method is defined below
                //this method is actually performing the write operation
                addProgramme();
            }
        });


        //set item click listener to programmes list View
        listeViewprogrammes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                   @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                      //getting the selected programme i is the position
                     Programme pgr = (Programme) programmesList.get(i);
                     String pgr_id =  pgr.getPgr_id();
                     String date_deb = pgr.getDate_debut();


                      showDialog(pgr_id,date_deb);      }
                                               }


        );


    }



   private void showDialog(final String id , final String date){
       AlertDialog.Builder builder = new AlertDialog.Builder(this);

       LayoutInflater inflater = getLayoutInflater();

       View view = inflater.inflate(R.layout.dialog_layout,null);
       //this dialog will have this layout we build
       builder.setView(view);
       //get buttons Ref
       Button add_med_dialog_btn = view.findViewById(R.id.add_med_dialog_btn);
       Button add_temp_dialog_btn = view.findViewById(R.id.add_temp_dialog_btn);

       add_temp_dialog_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //creating an intent
               Intent intent = new Intent(getApplicationContext(), AddListTemperature.class);

               //putting artist name and id to intent
               intent.putExtra(ID_PROGRAMME, id);
               intent.putExtra(DATE_PROGRAMME, date);
               //starting the activity with intent
               startActivity(intent);
           }
       });


       add_med_dialog_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //creating an intent
               Intent intent = new Intent(getApplicationContext(), AddListLigneMedicament.class);

               //putting artist name and id to intent
               intent.putExtra(ID_PROGRAMME, id);
               intent.putExtra(DATE_PROGRAMME, date);
               //starting the activity with intent
               startActivity(intent);

           }
       });




       AlertDialog alertdialog = builder.create();
       alertdialog.show();

   }



    //to display data in list View
    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseProgrammes.addValueEventListener(new ValueEventListener() {
            @Override
            //datasnapshot contains all the data from firedb
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous programmes list
                programmesList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting programme
                    Programme programme = postSnapshot.getValue(Programme.class);
                    //adding programe to the list
                    programmesList.add(programme);
                }

                //creating adapter
                ProgrammeAdapter programmeAdapter = new ProgrammeAdapter(AddListProgramme.this, programmesList);
                //attaching adapter to the listview
                listeViewprogrammes.setAdapter(programmeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void addProgramme() {

        //getting a unique id using push().getKey() method
        //it will create a unique id and we will use it as the Primary Key for our programme
        String identifier = databaseProgrammes.push().getKey();
        //autres champs
        int duree = Integer.parseInt(dureeText.getText().toString());
        String maladie = maladieText.getText().toString();
        dateString = date_debut.getText().toString();

         //create programme object
        Programme programme = new Programme(identifier, dateString, duree, maladie);
        //append programme to db
        databaseProgrammes.child(identifier).setValue(programme);

        //displaying a success toast
        Toast.makeText(this, "programme ajouté", Toast.LENGTH_LONG).show();

    }


}

