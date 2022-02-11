package com.example.hidraulicadecanales;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class VelocidadCircular extends Fragment {

    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;
    private Button calcular,limpiar;
    private EditText radio,pendiente;
    private Spinner spinner;
    private TextView resultado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_velocidad, container, false);
        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalVelocidad);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadVelocidad);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaVelocidad));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroVelocidad);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoVelocidad);
        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoVelocidad);
        final int frame = R.id.frameVelocidad;

        calcular = (Button)vista.findViewById(R.id.calcular_botonVelocidad);
        limpiar = (Button)vista.findViewById(R.id.limpiar_botonVelocidad);
        radio = (EditText)vista.findViewById(R.id.editRadioVelocidad);
        pendiente = (EditText)vista.findViewById(R.id.editPendienteVelocidad);
        spinner = (Spinner)vista.findViewById(R.id.spinnerVelocidad);
        resultado = (TextView)vista.findViewById(R.id.txt_resultado_TrapezialVelocidad);

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalVelocidad:
                        //Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoCircularFragment(),frame);
                        break;
                    case R.id.velocidadVelocidad:
                        establecerFragmento(new VelocidadCircular(), frame);
                        break;
                    case R.id.areaVelocidad:
                        establecerFragmento(new AreaHidraulicaCircular(),frame);
                        break;
                    case R.id.perimetroVelocidad:
                        establecerFragmento(new PerimetroAreaCirculoFragment(),frame);
                        break;
                    case R.id.radioHidraulicoVelocidad:
                        establecerFragmento(new RadioHidraulicoCircularFragment(),frame);
                        break;
                }
            }
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double radioD = Double.parseDouble(radio.getText().toString());
                    double pendienteD = Double.parseDouble(pendiente.getText().toString());
                    double numero_Manning=0;
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
                    double velocidad;
                    velocidad = ((Math.pow(radioD,0.6667))*Math.pow(pendienteD,0.5))/numero_Manning;

                    resultado.setText(String.valueOf(redondearDecimales(velocidad,3))+" m/s");
                } catch(Exception e){
                    Toast.makeText(getActivity(),"FALTAN DATOS",Toast.LENGTH_LONG).show();
                }

            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio.setText("");
                resultado.setText("0");
                pendiente.setText("");
            }
        });
        return vista;
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

    private void establecerFragmento(Fragment fragment,int frame) {
        FragmentManager maanger = getFragmentManager();
        FragmentTransaction transaction = maanger.beginTransaction();
        transaction.replace(frame, fragment);
        transaction.commit();
    }

}

