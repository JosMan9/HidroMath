package com.example.hidraulicadecanales;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.support.v7.app.AppCompatActivity;


public class Normal_Trapezial_Calculo extends Fragment {

    private RadioButton gasto;
    private Fragment calculo_gasto;

    public Normal_Trapezial_Calculo() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_opciones, container, false);


        return vista;
    }



}
