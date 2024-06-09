package com.example.projectif4firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectif4firebase.adapters.MedicamentAdapter;
import com.example.projectif4firebase.models.Medicament;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AddListLigneMedicament extends AppCompatActivity {
    Spinner listeMedicamentsSpinner;
    //hold list (all medecines) from firebase
    List medicamentsList;
    //hold list (medecines of a programme) from firebase
    List medicaments_pgr_List;

    String selectedRef;
    DatabaseReference databaseMedicaments;
    //
    DatabaseReference databaseLigneMedicaments;
    //pour stocker le medicament d'un prgramme
    Medicament medicament_pgr;
    //
    Intent intent;

    ListView med_pgr_list_view;

    Button btn_add_med_pgr;
   TextView date_pgr_view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligne_medicament);
        //get Views ref
        listeMedicamentsSpinner = findViewById(R.id.spinner_ref_med);
        btn_add_med_pgr = findViewById(R.id.btn_add_med_pgr);
        //getDbRef
        databaseMedicaments= FirebaseDatabase.getInstance().getReference("medicament");
        medicamentsList =new ArrayList<Medicament>();
        medicaments_pgr_List = new ArrayList<Medicament>();
        setTitle("Medicament");
        //
        med_pgr_list_view = findViewById(R.id.med_pgr_list_view);
        date_pgr_view = findViewById(R.id.date_pgr_ligne_view);

        //listener

        listeMedicamentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRef = parent.getItemAtPosition(position).toString();

                //search the correspondante Object of selected ref
                for(int i=0;i<medicamentsList.size();i++) {
                    Medicament medicament = (Medicament) medicamentsList.get(i);
                    if (selectedRef.equals(medicament.getRef_med())) {
                        medicament_pgr = medicament;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btn_add_med_pgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMedicamentPrograme();
            }
        });


    //get intent
        intent = getIntent();

        //getIntentFields
        String pgr_id = intent.getStringExtra(AddListProgramme.ID_PROGRAMME);
        String date_pgr = intent.getStringExtra(AddListProgramme.DATE_PROGRAMME);

        databaseLigneMedicaments=FirebaseDatabase.getInstance().getReference("ligne_médicament").child(pgr_id);
        date_pgr_view.setText("Programme de date "+date_pgr);


    }


    //getMedicinesList

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


                //call method
                addItemsOnSpinnerMedicament();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //display Medicines of a programme
        //track value changes
        databaseLigneMedicaments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear the List
                medicaments_pgr_List.clear();
                //loop through SnAPSHOT THAT contains all the data
                for(DataSnapshot medicineSnapshot : dataSnapshot.getChildren()){
                    //getting medicine
                    Medicament medicament = medicineSnapshot.getValue(Medicament.class);
                    //fill the list
                    medicaments_pgr_List.add(medicament);
                }

                //create Med adapter
                MedicamentAdapter adapter = new MedicamentAdapter(AddListLigneMedicament.this,medicaments_pgr_List);
                //
                med_pgr_list_view.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    // method to add items into spinner dynamically
    public void addItemsOnSpinnerMedicament() {
      List<String> listRefMed = new ArrayList<String>();
        //getRef and assign it to spinner
         for(int i=0;i<medicamentsList.size();i++){
             Medicament medicament = (Medicament) medicamentsList.get(i);
             listRefMed.add(medicament.getRef_med());
         }



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listRefMed);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeMedicamentsSpinner.setAdapter(dataAdapter);
    }


    public void saveMedicamentPrograme(){

        String identifier = databaseLigneMedicaments.push().getKey();
        databaseLigneMedicaments.child(identifier).setValue(medicament_pgr);

        Toast.makeText(this, "Enregistré", Toast.LENGTH_LONG).show();

    }



}
