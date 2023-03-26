package com.example.carapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.List;

/**
 * ListAdapter.java - Classe responsável pelo adpater da lista de veículos
 * @author  Gregory Perozzo
 * @version 1.0
 */
public class ListAdapter extends ArrayAdapter<Vehicle> {

    private int _resourceLayout;
    private Context _context;

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<Vehicle> items) {
        super(context, resource, items);

        _resourceLayout = resource;
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(_context);
            v = vi.inflate(_resourceLayout, null);
        }

        Vehicle vehicle = getItem(position);

        if (vehicle != null) {

            TextView plate = v.findViewById(R.id.txtCarItemPlate);
            TextView manufacturer = v.findViewById(R.id.txtCarItemManufacturer);
            TextView year = v.findViewById(R.id.txtCarItemYear);

            if (plate != null) {
                plate.setText(vehicle.getPlate());
            }

            if (manufacturer != null) {
                manufacturer.setText(vehicle.getManufacturer());
            }

            if (year != null) {
                year.setText(vehicle.getYear());
            }
        }

        return v;
    }
}
