package com.example.hidraulicadecanales;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RadioHidraulicoTriangularFragment extends Fragment {
    public RadioHidraulicoTriangularFragment() {
        // Required empty public constructor
    }

    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;
    private Button calcular,limpiar;
    private EditText talud,tirante;
    private TextView resultado;
    private CheckBox maxima_eficiencia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_radio_hidraulico_triangular, container, false);
        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalRadioTriangular);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadRadioTriangular);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaRadioTriangular));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroRadioTriangular);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoRadioTriangular);
        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoRadioTriangular);

        talud = (EditText)vista.findViewById(R.id.editTaludAreaTriangular);
        tirante = (EditText)vista.findViewById(R.id.editTiranteAreaTriangular);
        calcular = (Button)vista.findViewById(R.id.calcular_botonAreaTriangular);
        limpiar = (Button)vista.findViewById(R.id.limpiar_botonAreaTriangular);
        resultado = (TextView)vista.findViewById(R.id.txt_resultado_radioTriangular);
        final int frame = R.id.frameRadioTriangular;

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalRadioTriangular:
                        //Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoTriangularFragment(),frame);
                        break;
                    case R.id.velocidadRadioTriangular:
                        establecerFragmento(new VelocidadTrangularFragment(), frame);
                        break;
                    case R.id.areaRadioTriangular:
                        establecerFragmento(new AreaHGastoTriangularFragment(),frame);
                        break;
                    case R.id.perimetroRadioTriangular:
                        establecerFragmento(new PerimetroATriangularFragment(),frame);
                        break;
                    case R.id.radioHidraulicoRadioTriangular:
                        establecerFragmento(new RadioHidraulicoTriangularFragment(),frame);
                        break;
                }
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                talud.setText("");
                tirante.setText("");
                resultado.setText("0");
            }
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double taludD = Double.parseDouble(talud.getText().toString());
                    double tiranteD = Double.parseDouble(tirante.getText().toString());
                    double area = (taludD*Math.pow(tiranteD,2))/2;
                    double perimetro = 2*tiranteD*Math.sqrt(1+Math.pow(taludD,2));
                    double radio = area/perimetro;
                    resultado.setText(String.valueOf(redondearDecimales(radio,3))+" m");
                } catch(Exception e){
                    Toast.makeText(getActivity(),"FALTAN DATOS",Toast.LENGTH_LONG).show();
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

    private double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
}
