package com.example.hidraulicadecanales;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class PerimetroARectangularFragment extends Fragment {

    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;
    private Button calcular,limpiar;
    private EditText ancho,tirante;
    private TextView resultado,perimetro,anchoText;
    private CheckBox maxima_eficiencia;

    public PerimetroARectangularFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_perimetro_arectangular, container, false);
        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalPerimetroRectangular);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadPerimetroRectangular);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaPerimetroRectangular));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroPerimetroRectangular);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoPerimetroRectangular);
        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoPerimetroRectangular);
        final int frame = R.id.framePerimetroARect;

        ancho = (EditText)vista.findViewById(R.id.editAnchoAreaRectangular);
        tirante = (EditText)vista.findViewById(R.id.editTiranteAreaRectangular);
        calcular = (Button)vista.findViewById(R.id.calcular_botonAreaRectangular);
        limpiar = (Button)vista.findViewById(R.id.limpiar_botonAreaRectangular);
        resultado = (TextView)vista.findViewById(R.id.txt_ResultadoAreaRectangular);
        perimetro = (TextView)vista.findViewById(R.id.txt_perimetroRectangular);
        maxima_eficiencia = (CheckBox)vista.findViewById(R.id.checkPerimetroRectangular);
        anchoText = (TextView)vista.findViewById(R.id.txt_anchoPerimetroRectangulo);

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalPerimetroRectangular:
                        //Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoRectanguloFragment(),frame);
                        break;
                    case R.id.velocidadPerimetroRectangular:
                        establecerFragmento(new VelocidadFragment(), frame);
                        break;
                    case R.id.areaPerimetroRectangular:
                        establecerFragmento(new AreaHidraulicaGastoFragment(),frame);
                        break;
                    case R.id.perimetroPerimetroRectangular:
                        establecerFragmento(new PerimetroARectangularFragment(),frame);
                        break;
                    case R.id.radioHidraulicoPerimetroRectangular:
                        establecerFragmento(new RadioHidraulicoRectangularFragment(),frame);
                        break;
                }
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ancho.setText("");
                tirante.setText("");
                resultado.setText("0");
                perimetro.setText("0");
            }
        });
        
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (maxima_eficiencia.isChecked()){
                        double tiranteD = Double.parseDouble(tirante.getText().toString());
                        double area;
                        double perimetroD;
                        area = 2*Math.pow(tiranteD,2);
                        perimetroD = 4*tiranteD;
                        resultado.setText(String.valueOf(redondearDecimales(area,3))+" m²");
                        perimetro.setText(String.valueOf(redondearDecimales(perimetroD,3))+" m");

                    }else {
                        double anchoD = Double.parseDouble(ancho.getText().toString());
                        double tiranteD = Double.parseDouble(tirante.getText().toString());
                        double area;
                        double perimetroD;
                        area = anchoD*tiranteD;
                        perimetroD = anchoD + 2*tiranteD;
                        resultado.setText(String.valueOf(redondearDecimales(area,3))+" m²");
                        perimetro.setText(String.valueOf(redondearDecimales(perimetroD,3))+" m");
                    }
                } catch(Exception e){
                    Toast.makeText(getActivity(),"FALTAN DATOS",Toast.LENGTH_LONG).show();
                }
            }
        });

        maxima_eficiencia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ancho.setEnabled(false);
                    anchoText.setTextColor(Color.RED);

                } else {
                    ancho.setEnabled(true);
                    anchoText.setTextColor(Color.BLACK);

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
        resultado = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
        return resultado;
    }

}
