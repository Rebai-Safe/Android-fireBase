package com.example.projectif4firebase.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectif4firebase.R;
import com.example.projectif4firebase.models.Medicament;

import java.util.List;

public class MedicamentAdapter extends ArrayAdapter<Medicament> {

    private Activity context;
    List<Medicament> listMedicament;

    public MedicamentAdapter(Activity context,List<Medicament> listMedicament){
        super(context, R.layout.medicament_item, listMedicament);
        this.context=context;
        this.listMedicament = listMedicament;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.medicament_item,null,true);

        //get medicament item fields references
        TextView ref_med = listViewItem.findViewById(R.id.ref_med_view);
        TextView date_debut_conso = listViewItem.findViewById(R.id.date_debut_conso_view);
        TextView duree_med = listViewItem.findViewById(R.id.duree_med_view);

        //get Object
        Medicament medicament = listMedicament.get(position);

        //fill in the fields
        ref_med.setText(medicament.getRef_med());
        date_debut_conso.setText(medicament.getDate_debut_conso());
        duree_med.setText(String.valueOf(medicament.getDuree()));
        duree_med.setText(duree_med.getText() + "Jours");

        //return item view
        return listViewItem;

    }
}
