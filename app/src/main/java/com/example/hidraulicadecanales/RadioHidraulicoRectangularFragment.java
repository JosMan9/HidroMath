package com.example.hidraulicadecanales;


import android.graphics.Color;
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

public class RadioHidraulicoRectangularFragment extends Fragment {
    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;
    private Button calcular,limpiar;
    private EditText ancho,tirante;
    private TextView resultado,anchoText;
    private CheckBox maxima_eficiencia;

    public RadioHidraulicoRectangularFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_radio_hidraulico_rectangular, container, false);
        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalRadioRectangular);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadRadioRectangular);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaRadioRectangular));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroRadioRectangular);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoRadioRectangular);
        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoRadioRectangular);
        final int frame = R.id.frameRadioHidraulicoRect;

        ancho = (EditText)vista.findViewById(R.id.editAnchoRadioRectangular);
        tirante = (EditText)vista.findViewById(R.id.editTiranteRadioRectangular);
        calcular = (Button)vista.findViewById(R.id.calcular_botonRadioRectangular);
        limpiar = (Button)vista.findViewById(R.id.limpiar_botonRadioRectangular);
        resultado = (TextView)vista.findViewById(R.id.txt_ResultadoRadioRectangular);
        maxima_eficiencia = (CheckBox)vista.findViewById(R.id.checkRadioRectangular);
        anchoText = (TextView)vista.findViewById(R.id.txt_anchoRadioRectangulo);

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalRadioRectangular:
                        //Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoRectanguloFragment(),frame);
                        break;
                    case R.id.velocidadRadioRectangular:
                        establecerFragmento(new VelocidadFragment(), frame);
                        break;
                    case R.id.areaRadioRectangular:
                        establecerFragmento(new AreaHidraulicaGastoFragment(),frame);
                        break;
                    case R.id.perimetroRadioRectangular:
                        establecerFragmento(new PerimetroARectangularFragment(),frame);
                        break;
                    case R.id.radioHidraulicoRadioRectangular:
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
            }
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (maxima_eficiencia.isChecked()){
                        double tiranteD = Double.parseDouble(tirante.getText().toString());
                        double radio;
                        radio = tiranteD/2;

                        resultado.setText(String.valueOf(redondearDecimales(radio,3))+" m");
                    }else {
                        double tiranteD = Double.parseDouble(tirante.getText().toString());
                        double radio;
                        double anchoD = Double.parseDouble(ancho.getText().toString());

                        double area = anchoD*tiranteD;
                        double perimetro = anchoD + 2*tiranteD;
                        radio = area/perimetro;
                        resultado.setText(String.valueOf(redondearDecimales(radio,3))+" m");
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
                    //penHidraulica.setTextColor(Color.RED);
                    //pendiente.setEnabled(false);
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
