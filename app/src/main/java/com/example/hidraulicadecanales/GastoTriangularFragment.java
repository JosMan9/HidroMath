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



/**
 * A simple {@link Fragment} subclass.
 */
public class GastoTriangularFragment extends Fragment {

    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;
    private EditText tirante,talud,pendiente;
    private Spinner spinner;
    private Button calcular,limpiar;
    private TextView resultado,taludText,tiranteText,froude;
    private CheckBox maxima_eficiencia;


    public GastoTriangularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_gasto_triangular, container, false);
        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalGastoTriangular);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadGastoTriangular);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaGastoTriangular));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroGastoTriangular);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoGastoTriangular);
        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoGastoTriangular);
        calcular = (Button)vista.findViewById(R.id.calcular_botonGastoTriangular);
        limpiar = (Button)vista.findViewById(R.id.limpiar_botonGastoTriangular);
        spinner = (Spinner)vista.findViewById(R.id.spinnerGastoTriangular);
        resultado = (TextView)vista.findViewById(R.id.txt_ResultadoGastoTriangular);
        tirante = (EditText)vista.findViewById(R.id.editTaludGastoTriangular);
        talud = (EditText)vista.findViewById(R.id.editTaludGastoTriangular);
        pendiente = (EditText)vista.findViewById(R.id.editGastoPendienteTriangular);
        froude = (TextView)vista.findViewById(R.id.froude_txtTriangular);
        final int frame = R.id.frameTriangular;
        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalGastoTriangular:
                        //Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoTriangularFragment(),frame);
                        break;
                    case R.id.velocidadGastoTriangular:
                        establecerFragmento(new VelocidadTrangularFragment(), frame);
                        break;
                    case R.id.areaGastoTriangular:
                        establecerFragmento(new AreaHGastoTriangularFragment(),frame);
                        break;
                    case R.id.perimetroGastoTriangular:
                        establecerFragmento(new PerimetroATriangularFragment(),frame);
                        break;
                    case R.id.radioHidraulicoGastoTriangular:
                        establecerFragmento(new RadioHidraulicoTriangularFragment(),frame);
                        break;
                }
            }
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double tiranteD = Double.parseDouble(tirante.getText().toString());
                    double taludD = Double.parseDouble(talud.getText().toString());
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
                    double area,gasto,perimetro,radio;
                    area = taludD*Math.pow(tiranteD,2);
                    perimetro = 2*tiranteD*Math.sqrt(1+Math.pow(taludD,2));
                    radio = area/perimetro;
                    double velocidad = (Math.pow(radio,0.6667)*Math.pow(pendienteD,0.5))/numero_Manning;
                    gasto = area*velocidad;
                    if (gasto < 0){
                        Toast.makeText(getActivity(),"RESULTADO IRREAL",Toast.LENGTH_LONG).show();
                        return;
                    }
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
                } catch(Exception e){
                    Toast.makeText(getActivity(),"FALTAN DATOS",Toast.LENGTH_LONG).show();
                }
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tirante.setText("");
                talud.setText("");
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
