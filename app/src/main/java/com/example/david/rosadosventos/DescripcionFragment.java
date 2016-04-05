package com.example.david.rosadosventos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DescripcionFragment extends Fragment {

    String descripcion = "---";
    private TextView tDescripcion;

    public DescripcionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_descripcion, container, false);
        tDescripcion = (TextView) view.findViewById(R.id.tDescripcion);

        tDescripcion.setText(descripcion);

        return view;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        tDescripcion.setText(descripcion);
    }
}
