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

import org.w3c.dom.Text;

public class GastoFragment extends Fragment {


    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;
    private EditText ancho,tirante,talud,pendiente;
    private Spinner spinner;
    private Button calcular,limpiar;
    private TextView resultado,tiranteText,taludText,froude;
    private CheckBox maxima_eficiencia;

    public GastoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_gato, container, false);
        /*ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(new Trapezial_Activity(),R.array.datos,R.layout.support_simple_spinner_dropdown_item);
        Spinner spinner;
        spinner = (Spinner)vista.findViewById(R.id.spinner);
        spinner.setAdapter(adaptador);*/
        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalGasto);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadGasto);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaGasto));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroGasto);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoGasto);
        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoGasto);
        final int frame = R.id.frameGastoTrapezial;
        ancho = (EditText)vista.findViewById(R.id.editAnchoGasto);
        tirante = (EditText)vista.findViewById(R.id.editTiranteGasto);
        talud = (EditText)vista.findViewById(R.id.editTaludGasto);
        resultado = (TextView) vista.findViewById(R.id.txt_resultado_TrapezialGasto);
        calcular = (Button) vista.findViewById(R.id.calcular_botonGasto);
        limpiar = (Button)vista.findViewById(R.id.limpiar_botonGasto);
        spinner = (Spinner)vista.findViewById(R.id.spinnerGasto);
        pendiente = (EditText)vista.findViewById(R.id.editPendienteGasto);
        maxima_eficiencia = (CheckBox)vista.findViewById(R.id.check_gasto);
        //penHidraulica = (TextView)vista.findViewById(R.id.pendiente_gasto);
        tiranteText = (TextView)vista.findViewById(R.id.txt_tiranteGastoTrapecial) ;
        taludText = (TextView)vista.findViewById(R.id.txt_taludRadio);
        froude = (TextView)vista.findViewById(R.id.froude_txt);

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalGasto:
                        //Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoFragment(),frame);
                        break;
                    case R.id.velocidadGasto:
                        establecerFragmento(new VelocidadTrapezialFragment(), frame);
                        break;
                    case R.id.areaGasto:
                        establecerFragmento(new AreaHGastoTrapezial(),frame);
                        break;
                    case R.id.perimetroGasto:
                        establecerFragmento(new PerimetroyAreaFragment(),frame);
                        break;
                    case R.id.radioHidraulicoGasto:
                        establecerFragmento(new RadioHidraulicoFragment(),frame);
                        break;
                }
            }
        });

       maxima_eficiencia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (b){
                   talud.setEnabled(false);
                   taludText.setTextColor(Color.RED);
                   tirante.setEnabled(false);
                   tiranteText.setTextColor(Color.RED);

               } else {
                   talud.setEnabled(true);
                   taludText.setTextColor(Color.BLACK);
                   tirante.setEnabled(true);
                   tiranteText.setTextColor(Color.BLACK);
               }
           }
       });
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (maxima_eficiencia.isChecked()){
                        double anchoD = Double.parseDouble(ancho.getText().toString());
                        double tiranteD = Math.sqrt(3)*anchoD/2;
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

                        double area,gasto,radio;
                        area = Math.sqrt(3)*Math.pow(tiranteD,2);

                        radio = tiranteD/2;
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

                    }else {
                        double anchoD = Double.parseDouble(ancho.getText().toString());
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

                        area = (anchoD*tiranteD) + (taludD*Math.pow(tiranteD,2));
                        perimetro = anchoD + (2*tiranteD*Math.sqrt(1+Math.pow(taludD,2)));
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
                    }
                } catch(Exception e){
                    Toast.makeText(getActivity(),"FALTAN DATOS",Toast.LENGTH_LONG).show();
                }

            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ancho.setText("");
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

    private void establecerFragmento(Fragment fragment, int frame) {
        FragmentManager maanger = getFragmentManager();
        FragmentTransaction transaction = maanger.beginTransaction();
        transaction.replace(frame, fragment);
        transaction.commit();
    }

}
