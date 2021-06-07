package com.example.simulacropc02.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.simulacropc02.Entity.Revista;
import com.example.simulacropc02.R;

import java.util.List;

public class RevistaAdapter extends ArrayAdapter<Revista> {

    private Context context;
    private List<Revista> lista;

    public RevistaAdapter(@NonNull Context context, int resource, @NonNull List<Revista> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_revista_crud_item, parent, false);

        Revista obj = lista.get(position);

        TextView txtNombre = row.findViewById(R.id.idItemCrudRevistaNombre);
        txtNombre.setText(obj.getNombre());

        return row;
    }
}
