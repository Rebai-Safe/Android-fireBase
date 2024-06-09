package com.example.projectif4firebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import java.util.Objects;

public class Home extends AppCompatActivity {

    CardView pgr_card;
    CardView med_card;
    CardView doctors_card;
    CardView conseil_card;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pgr_card = findViewById(R.id.pgr_card);
        med_card = findViewById(R.id.med_card);
        doctors_card = findViewById(R.id.doctorsSearch);
        conseil_card = findViewById(R.id.advice);




        pgr_card.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            goToProgrammes();
                                        }
                                    }

        );

        med_card.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goToMedicament();
            }
        });

        doctors_card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Search for doctors nearby
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=médecin");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });

        conseil_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to site intent
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://lasante.net/fiches-conseil/infos-pratiques/tout-savoir-medicaments/"));
                intent.setPackage("com.android.chrome");  // package of chrome
                startActivity(intent);
            }
        });

    }









   void goToMedicament(){
       intent = new Intent(this, AddListMedicament.class);
       startActivity(intent);
   }

   void goToProgrammes(){
        intent = new Intent(this, AddListProgramme.class);
        startActivity(intent);
   }


}