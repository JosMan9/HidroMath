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
public class GastoCircularFragment extends Fragment {

    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;
    private EditText tirante,diametro,pendiente;
    private Spinner spinner;
    private Button calcular,limpiar;
    private TextView resultado,penHidraulica,tiranteText,diametroText,froude;
    private CheckBox maxima_eficiencia;


    public GastoCircularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_gasto_circular, container, false);

        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalCirculoGasto);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadCirculoGasto);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaCirculoGasto));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroCirculoGasto);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoCirculoGasto);
        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoGastoCircular);
        final int frame = R.id.frameCirculoGasto;
        tirante = (EditText)vista.findViewById(R.id.editTiranteGastoCircular);
        diametro = (EditText)vista.findViewById(R.id.edit_DiametroGastoCircular);
        resultado = (TextView) vista.findViewById(R.id.txt_resultado_GastoCirculo);
        calcular = (Button) vista.findViewById(R.id.calcular_botonGastoCirculo);
        limpiar = (Button)vista.findViewById(R.id.limpiar_botonGastoCirculo);
        spinner = (Spinner)vista.findViewById(R.id.spinnerGastoCircular);
        pendiente = (EditText)vista.findViewById(R.id.editPendienteGastoCirculo);
        maxima_eficiencia = (CheckBox)vista.findViewById(R.id.checkGastoCirculo);
        //penHidraulica = (TextView)vista.findViewById(R.id.pendiente_gasto);
        tiranteText = (TextView)vista.findViewById(R.id.txt_tiranteGastoCircular) ;
        diametroText = (TextView)vista.findViewById(R.id.txt_diametro_gasto);
        froude = (TextView)vista.findViewById(R.id.froude_txtCircular);

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalCirculoGasto:
                        Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoCircularFragment(),frame);
                        break;
                    case R.id.velocidadCirculoGasto:
                        establecerFragmento(new VelocidadCircular(), frame);
                        break;
                    case R.id.areaCirculoGasto:
                        establecerFragmento(new AreaHidraulicaCircular(),frame);
                        break;
                    case R.id.perimetroCirculoGasto:
                        establecerFragmento(new PerimetroAreaCirculoFragment(),frame);
                        break;
                    case R.id.radioHidraulicoCirculoGasto:
                        establecerFragmento(new RadioHidraulicoCircularFragment(),frame);
                        break;
                }
            }
        });

        maxima_eficiencia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    tirante.setEnabled(false);
                    tiranteText.setTextColor(Color.RED);
                } else {
                    tirante.setEnabled(true);
                    tiranteText.setTextColor(Color.BLACK);
                }
            }
        });
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //double anchoD = Double.parseDouble(ancho.getText().toString());

                    if (maxima_eficiencia.isChecked()){
                        double diametroD = Double.parseDouble(diametro.getText().toString());
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
                        double tiranteD = diametroD/2;
                        double area = Math.PI*Math.pow(tiranteD,2)/2;
                        double radio = tiranteD/2;
                        double velocidad = (Math.pow(radio,0.6667)*Math.pow(pendienteD,0.5))/numero_Manning;
                        double gasto = area*velocidad;

                        resultado.setText(String.valueOf(redondearDecimales(gasto,3))+" m³/s");

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
                        double tiranteD = Double.parseDouble(tirante.getText().toString());
                        double pendienteD = Double.parseDouble(pendiente.getText().toString());
                        double numero_Manning=0;
                        //double velocidad;
                        double diametroD = Double.parseDouble(diametro.getText().toString());

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
                        double angulo=0;
                        angulo = (2*Math.PI)-(2*Math.acos((2*tiranteD/diametroD)-1));
                        if (angulo <= 0){
                            Toast.makeText(getActivity(),"RESULTADO IRREAL", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (angulo == Double.NaN){
                            Toast.makeText(getActivity(),"RESULTADO IRREAL", Toast.LENGTH_LONG).show();
                            return;
                        }
                        double numerador = (angulo-Math.sin(angulo))*Math.pow(diametroD,2);
                        area = numerador/8;
                        //Toast.makeText(getActivity(),""+angulo + "\n"+area,Toast.LENGTH_LONG).show();
                        perimetro = (diametroD*angulo)/2;
                        radio = area/perimetro;
                        double velocidad = (Math.pow(radio,0.6667)*Math.pow(pendienteD,0.5))/numero_Manning;
                        gasto = area*velocidad;
                        if (gasto == Double.NaN){
                            Toast.makeText(getActivity(),"RESULTADO IRREAL", Toast.LENGTH_LONG).show();
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
                    }
                } catch(Exception e){
                    Toast.makeText(getActivity(),"FALTAN DATOS",Toast.LENGTH_LONG).show();
                }

            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ancho.setText("");
                tirante.setText("");
                //talud.setText("");
                resultado.setText("0");
                pendiente.setText("");
                diametro.setText("");
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

    private void establecerFragmento(Fragment fragment, int frame) {
        FragmentManager maanger = getFragmentManager();
        FragmentTransaction transaction = maanger.beginTransaction();
        transaction.replace(frame, fragment);
        transaction.commit();
    }

}
