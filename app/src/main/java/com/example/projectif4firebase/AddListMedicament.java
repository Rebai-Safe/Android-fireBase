package com.example.projectif4firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projectif4firebase.adapters.MedicamentAdapter;
import com.example.projectif4firebase.models.Medicament;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddListMedicament extends AppCompatActivity {


    public static final String REF_MED = "ref_med";
    public static final String ID_MEDICAMANT = "id_med";

    //--Vars Declaration
    //
    EditText date_debut_conso;
    EditText duree_med_Text;
    EditText ref_med_Text;
    //
    int day, month, year;
    Calendar calendar;
    String dateString;
    Button btnAdd;
    //hold list
    List medicamentsList;

    ListView listeViewmedicaments;

    DatabaseReference databaseMedicaments;
     DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicament);
        setTitle("Nouveau Medicament");
        //getViews references
        listeViewmedicaments = findViewById(R.id.med_list_view);
        date_debut_conso = findViewById(R.id.date_debut_conso_input);
        duree_med_Text = findViewById(R.id.duree_medi_input);
        ref_med_Text = findViewById(R.id.ref_med_input);
        btnAdd=findViewById(R.id.btn_add_med);


        //popUp dat dialog
        date_debut_conso.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              final Calendar cldr = Calendar.getInstance();
                                              int day = cldr.get(Calendar.DAY_OF_MONTH);
                                              int month = cldr.get(Calendar.MONTH);
                                              int year = cldr.get(Calendar.YEAR);
                                              // date picker dialog
                                              datePickerDialog = new DatePickerDialog(AddListMedicament.this,
                                                      new DatePickerDialog.OnDateSetListener() {
                                                          @Override
                                                          public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                              date_debut_conso.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                                          }
                                                      }, year, month, day);
                                              datePickerDialog.show();



                                          }
                                      }
        );







        //initListMedicamant
        medicamentsList = new ArrayList<Medicament>();

        //getDbRef
        databaseMedicaments= FirebaseDatabase.getInstance().getReference("medicament");


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addProgrammes()
                //the method is defined below
                //this method is actually performing the write operation
                addMedicamant();
            }
        });


        //setclick listener to medicament list View to see les prises correspendantes :')
        listeViewmedicaments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Medicament selectedMedicament = (Medicament) medicamentsList.get(position);
                //creating an intent
                Intent intent = new Intent(getApplicationContext(), AddListPrise.class);
                //putting artist name and id to intent
                intent.putExtra(REF_MED,selectedMedicament.getRef_med());
                intent.putExtra(ID_MEDICAMANT,selectedMedicament.getId_med());
                //start
                startActivity(intent);

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseMedicaments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clearing the previous programmes list
                medicamentsList.clear();
                //iterating through all the nodes
                for (DataSnapshot medicamentSnapshot : dataSnapshot.getChildren()) {
                    //getting medicament
                    Medicament medicament = medicamentSnapshot.getValue(Medicament.class);
                    //
                    medicamentsList.add(medicament);
                }

                //create Med adapter
                MedicamentAdapter adapter = new MedicamentAdapter(AddListMedicament.this,medicamentsList);
                //
                listeViewmedicaments.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void addMedicamant() {

        String identifier = databaseMedicaments.push().getKey();
        //convertFiels to createNewObject
        String ref_med = ref_med_Text.getText().toString();
        int duree = Integer.parseInt(duree_med_Text.getText().toString());
        String dateString = date_debut_conso.getText().toString();


        Medicament medicament = new Medicament(identifier,ref_med, dateString, duree);


        //append programme to db
        databaseMedicaments.child(identifier).setValue(medicament);

        //displaying a success toast
        Toast.makeText(this, "Medicament ajouté", Toast.LENGTH_LONG).show();


    }
    }
