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
import com.example.projectif4firebase.models.Prise;
import com.example.projectif4firebase.models.Programme;

import java.util.List;

public class PrisesAdapter extends ArrayAdapter<Prise> {

    private Activity context;
    List<Prise> priseList;

    public PrisesAdapter(Activity context,List<Prise> priseList){
        super(context, R.layout.prise_item,priseList);
        this.context=context;
        this.priseList=priseList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.prise_item,null,true);

        //get fields
        TextView date_prise_view = listViewItem.findViewById(R.id.date_prise_view);
        TextView heure_prise_view = listViewItem.findViewById(R.id.heure_prise_view);
        TextView description_view = listViewItem.findViewById(R.id.description_view);
        TextView qte_prise_view = listViewItem.findViewById(R.id.qte_prise_view);

        //get Object
        Prise prise = priseList.get(position);
        //getvalues and set Fields
        date_prise_view.setText(prise.getDate_debut());
        heure_prise_view .setText(String.valueOf(prise.getHeure()));
        heure_prise_view.setText(heure_prise_view.getText() + "H");
        description_view.setText(prise.getDecription());
        qte_prise_view.setText(String.valueOf(prise.getQte()));

        //return item view
        return listViewItem;
    }

}
