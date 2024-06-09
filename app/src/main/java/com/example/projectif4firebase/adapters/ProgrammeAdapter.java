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
import com.example.projectif4firebase.models.Programme;

import java.util.List;

public class ProgrammeAdapter extends ArrayAdapter<Programme> {

    private Activity context;
    List<Programme> programmesList;

    public ProgrammeAdapter(Activity context,List<Programme> programmesList){
        super(context, R.layout.programme_item,programmesList);
        this.context=context;
        this.programmesList=programmesList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.programme_item,null,true);

        //get fields
        TextView numero = listViewItem.findViewById(R.id.numero_pgr);
        TextView date_deb = listViewItem.findViewById(R.id.date_debut_pgr);
        TextView duree = listViewItem.findViewById(R.id.duree_pgr);
        TextView maladie = listViewItem.findViewById(R.id.maladie);

        //get Object
        Programme programe = programmesList.get(position);
        //getvalues and set Fields
        numero.setText("");
        date_deb.setText(programe.getDate_debut());
        duree.setText(String.valueOf(programe.getDuree()));
        maladie.setText(programe.getMaladie());

       //return item view
        return listViewItem;
    }
}