package com.example.hidraulicadecanales;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OpcionesFragment extends Fragment {
    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;


    public OpcionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_opciones, container, false);

        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalORIGINAL);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadORIGINAL);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaORIGINAL));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroORIGINAL);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoORIGINAL);
        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoORIGINAL);

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalORIGINAL:
                        //Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoRectanguloFragment(),R.id.frame_opciones);
                        break;
                    case R.id.velocidadORIGINAL:
                        establecerFragmento(new VelocidadFragment(), R.id.frame_opciones);
                        break;
                    case R.id.areaORIGINAL:
                        establecerFragmento(new AreaHidraulicaGastoFragment(),R.id.frame_opciones);
                        break;
                    case R.id.perimetroORIGINAL:
                        establecerFragmento(new PerimetroARectangularFragment(),R.id.frame_opciones);
                        break;
                    case R.id.radioHidraulicoORIGINAL:
                        establecerFragmento(new RadioHidraulicoRectangularFragment(),R.id.frame_opciones);
                        break;
                }
            }
        });



        return vista;
    }

    private void establecerFragmento(Fragment fragment,int frame) {
        FragmentManager maanger = getFragmentManager();
        FragmentTransaction transaction = maanger.beginTransaction();
        transaction.replace(frame, fragment);
        transaction.commit();
    }

}
