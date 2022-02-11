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

public class AreaHidraulicaGastoFragment extends Fragment {
    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;
    private EditText gastoEdit,radio,pendiente;
    private TextView resultado;
    private Button calcular,limpiar;
    private Spinner spinner;



    public AreaHidraulicaGastoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_area_hidraulica_gasto, container, false);
        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalAreaHidraulica);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadAreaHidraulica);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaAreaHidraulica));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroAreaHidraulica);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoAreaHidraulica);
        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoAreaHidraulica);
        gastoEdit = (EditText)vista.findViewById(R.id.editGastoAreaH);
        radio = (EditText)vista.findViewById(R.id.editRadioAreaH);
        pendiente = (EditText)vista.findViewById(R.id.editPendienteAreaHidraulica);
        resultado = (TextView) vista.findViewById(R.id.txt_resultado_ATrapezialH);
        calcular = (Button) vista.findViewById(R.id.calcular_botonAreaTrapezialH);
        limpiar = (Button)vista.findViewById(R.id.limpiar_AreaHTrapezial);
        spinner = (Spinner)vista.findViewById(R.id.spinnerAreaGasto);
        final int frame = R.id.frameAreaHidraulica;

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalAreaHidraulica:
                        //Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoRectanguloFragment(),frame);
                        break;
                    case R.id.velocidadAreaHidraulica:
                        establecerFragmento(new VelocidadFragment(), frame);
                        break;
                    case R.id.areaAreaHidraulica:
                        establecerFragmento(new AreaHidraulicaGastoFragment(),frame);
                        break;
                    case R.id.perimetroAreaHidraulica:
                        establecerFragmento(new PerimetroARectangularFragment(),frame);
                        break;
                    case R.id.radioHidraulicoAreaHidraulica:
                        establecerFragmento(new RadioHidraulicoRectangularFragment(),frame);
                        break;
                }
            }
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double gastoD = Double.parseDouble(gastoEdit.getText().toString());
                    double radioD = Double.parseDouble(radio.getText().toString());
                    double pendienteD = Double.parseDouble(pendiente.getText().toString());
                    double numero_Manning = 0;
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

                    double area = (gastoD * numero_Manning) / ((Math.pow(radioD, 0.66667)) * Math.pow(pendienteD, 0.5));
                    resultado.setText(String.valueOf(redondearDecimales(area,3))+" m²");
                } catch(Exception e){
                    Toast.makeText(getActivity(),"FALTAN DATOS",Toast.LENGTH_LONG).show();
                }

            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gastoEdit.setText("");
                pendiente.setText("");
                radio.setText("");
                resultado.setText("0");
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
