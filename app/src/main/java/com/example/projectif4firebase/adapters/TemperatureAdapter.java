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
import com.example.projectif4firebase.models.Temperature;

import java.util.List;

public class TemperatureAdapter extends ArrayAdapter<Temperature> {

    private Activity context;
    List<Temperature> temperatureList;

    public TemperatureAdapter(Activity context,List<Temperature> temperatureList){
        super(context, R.layout.temperature_item,temperatureList);
        this.context=context;
        this.temperatureList=temperatureList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.temperature_item,null,true);

        //get reference of View fields
        TextView degres = listViewItem.findViewById(R.id.degre_view);
        TextView date_deb_pgr = listViewItem.findViewById(R.id.date_pgr_temp_view);


        //get Object
        Temperature temperature = temperatureList.get(position);
        //getvalues and set Fields
        degres.setText(String.valueOf(temperature.getDegres()));
       // date_deb_pgr.setText("Date du programme");


        //return item view
        return listViewItem;
    }
}
