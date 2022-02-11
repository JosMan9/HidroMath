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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GastoRectanguloFragment extends Fragment {
    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;
    private EditText tirante,ancho,pendiente;
    private Spinner spinner;
    private Button calcular,limpiar;
    private TextView resultado,anchoText,froude;
    private CheckBox maxima_eficiencia;

    public GastoRectanguloFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_gasto_rectangulo, container, false);
        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalRectanguloGasto);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadRectanguloGasto);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaRectanguloGasto));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroRectanguloGasto);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoRectanguloGasto);
        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoGastoRectangulo);

        tirante = (EditText)vista.findViewById(R.id.editTiranteGastoRectangulo);
        ancho = (EditText)vista.findViewById(R.id.edit_gastoRectangulo);
        resultado = (TextView) vista.findViewById(R.id.txt_resultado_GastoRectangulo);
        calcular = (Button) vista.findViewById(R.id.calcular_botonGastoRectangulo);
        limpiar = (Button)vista.findViewById(R.id.limpiar_botonGastoRectangulo);
        spinner = (Spinner)vista.findViewById(R.id.spinnerGastoRectangulo);
        pendiente = (EditText)vista.findViewById(R.id.editPendienteGastoRectangulo);
        anchoText = (TextView)vista.findViewById(R.id.txt_anchoGastoRectangulo);
        maxima_eficiencia = (CheckBox)vista.findViewById(R.id.checkGastoRectangulo);
        froude = (TextView)vista.findViewById(R.id.froude_txtRectangular);

        final int frame = R.id.frameRectanguloGasto;
        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalRectanguloGasto:
                        //Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoRectanguloFragment(),frame);
                        break;
                    case R.id.velocidadRectanguloGasto:
                        establecerFragmento(new VelocidadFragment(), frame);
                        break;
                    case R.id.areaRectanguloGasto:
                        establecerFragmento(new AreaHidraulicaGastoFragment(),frame);
                        break;
                    case R.id.perimetroRectanguloGasto:
                        establecerFragmento(new PerimetroARectangularFragment(),frame);
                        break;
                    case R.id.radioHidraulicoRectanguloGasto:
                        establecerFragmento(new RadioHidraulicoRectangularFragment(),frame);
                        break;
                }
            }
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (maxima_eficiencia.isChecked()){
                        double area,gasto,radio;
                        double numero_Manning=0;
                        double tiranteD = Double.parseDouble(tirante.getText().toString());
                        double pendienteD = Double.parseDouble(pendiente.getText().toString());
                        switch (spinner.getSelectedItem().toString()){
                            case "Arroyo de montaña con muchas piedras":
                                numero_Manning = 0.04;
                                break;
                            case "Tepetate (liso y uniforme)":
                                numero_Manning = 0.035;
                                break;
                            case "Tierra en buenas condiciones":
                                numero_Manning = 0.02;
                                break;
                            case "Tierra libre en vegetación":
                                numero_Manning = 0.025;
                                break;
                            case "Mampostería seca":
                                numero_Manning = 0.03;
                                break;
                            case "Mampostería con cemento":
                                numero_Manning = 0.02;
                                break;
                            case "Concreto":
                                numero_Manning = 0.017;
                                break;
                            case "Asbesto cemento":
                                numero_Manning = 0.01;
                                break;
                            case "Polietileno y PVC":
                                numero_Manning = 0.008;
                                break;
                            case "Fierro fundido":
                                numero_Manning = 0.014;
                                break;
                            case "Acero":
                                numero_Manning = 0.015;
                                break;
                            case "Vidrio, cobre":
                                numero_Manning = 0.01;
                                break;
                        }
                        radio = tiranteD/2;
                        area = 2*Math.pow(tiranteD,2);
                        double velocidad = (Math.pow(radio,0.6667)*Math.pow(pendienteD,0.5))/numero_Manning;
                        gasto = area*velocidad;
                        resultado.setText(String.valueOf(redondearDecimales(gasto,3)) + " m³/s");
                        double numeroFroude = velocidad/(Math.sqrt(9.81*tiranteD));
                        if (numeroFroude == 0 ){
                            froude.setText("Crítico");
                        } else {
                            if (numeroFroude < 1){
                                froude.setText("Subcrítico");
                            } else {
                                if (numeroFroude > 1){
                                    froude.setText("Supercrítico");
                                }

                            }
                        }
                    }else {
                        double area, gasto, perimetro, radio;
                        double numero_Manning = 0;
                        double tiranteD = Double.parseDouble(tirante.getText().toString());
                        double pendienteD = Double.parseDouble(pendiente.getText().toString());
                        double anchoD = Double.parseDouble(ancho.getText().toString());
                        switch (spinner.getSelectedItem().toString()) {
                            case "Arroyo de montaña con muchas piedras":
                                numero_Manning = 0.04;
                                break;
                            case "Tepetate (liso y uniforme)":
                                numero_Manning = 0.035;
                                break;
                            case "Tierra en buenas condiciones":
                                numero_Manning = 0.02;
                                break;
                            case "Tierra libre en vegetación":
                                numero_Manning = 0.025;
                                break;
                            case "Mampostería seca":
                                numero_Manning = 0.03;
                                break;
                            case "Mampostería con cemento":
                                numero_Manning = 0.02;
                                break;
                            case "Concreto":
                                numero_Manning = 0.017;
                                break;
                            case "Asbesto cemento":
                                numero_Manning = 0.01;
                                break;
                            case "Polietileno y PVC":
                                numero_Manning = 0.008;
                                break;
                            case "Fierro fundido":
                                numero_Manning = 0.014;
                                break;
                            case "Acero":
                                numero_Manning = 0.015;
                                break;
                            case "Vidrio, cobre":
                                numero_Manning = 0.01;
                                break;
                        }
                        area = anchoD * tiranteD;
                        perimetro = anchoD + (2 * tiranteD);
                        radio = area/perimetro;
                        double velocidad = (Math.pow(radio,0.6667)*Math.pow(pendienteD,0.5))/numero_Manning;
                        gasto = area*velocidad;
                        resultado.setText(String.valueOf(redondearDecimales(gasto,3)) + " m³/s");
                        double numeroFroude = velocidad/(Math.sqrt(9.81*tiranteD));
                        if (numeroFroude == 0 ){
                            froude.setText("Crítico");
                        } else {
                            if (numeroFroude < 1){
                                froude.setText("Subcrítico");
                            } else {
                                if (numeroFroude > 1){
                                    froude.setText("Supercrítico");
                                }

                            }
                        }
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

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ancho.setText("");
                tirante.setText("");
                resultado.setText("0");
                pendiente.setText("");
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
