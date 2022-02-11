package com.example.hidraulicadecanales;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class OpctionsTrapezialFragment extends Fragment {

    public OpctionsTrapezialFragment(){}

    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;

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
        final int frame = R.id.frame_opciones;

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalORIGINAL:
                        //Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoFragment(),frame);
                        break;
                    case R.id.velocidadORIGINAL:
                        establecerFragmento(new VelocidadTrapezialFragment(), frame);
                        break;
                    case R.id.areaORIGINAL:
                        establecerFragmento(new AreaHGastoTrapezial(),frame);
                        break;
                    case R.id.perimetroORIGINAL:
                        establecerFragmento(new PerimetroyAreaFragment(),frame);
                        break;
                    case R.id.radioHidraulicoORIGINAL:
                        establecerFragmento(new RadioHidraulicoFragment(),frame);
                        break;
                }
            }
        });

        return vista;
    }

    private void establecerFragmento(Fragment fragment, int frame) {
        FragmentManager maanger = getFragmentManager();
        FragmentTransaction transaction = maanger.beginTransaction();
        transaction.replace(frame, fragment);
        transaction.commit();
    }
}
