package com.example.hidraulicadecanales;


import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class PerimetroAreaCirculoFragment extends Fragment {

    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;
    private EditText diametroEdit,tiranteEdit;
    private TextView resultado,diametroText,perimetri_txt,tiranteTxt;
    private Button calcular,limpiar;
    private CheckBox maxima_eficiencia;


    public PerimetroAreaCirculoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_perimetro_area_circulo, container, false);

        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalPerimetroCirculo);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadPerimetroCirculo);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaPerimetroCirculo));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroPerimetroCirculo);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoPerimetroCirculo);
        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoPerimetroCirculo);

        diametroEdit = (EditText)vista.findViewById(R.id.editDiametroAreaCirculo);
        tiranteEdit = (EditText)vista.findViewById(R.id.editTiranteAreaCirculo);
        resultado = (TextView) vista.findViewById(R.id.txt_ResultadoAreaCirculo);
        calcular = (Button) vista.findViewById(R.id.calcular_botonAreaCirculo);
        limpiar = (Button)vista.findViewById(R.id.limpiar_botonAreaCirculo);
        diametroText = (TextView)vista.findViewById(R.id.txt_DiametroPerimetroCirculo);
        maxima_eficiencia = (CheckBox)vista.findViewById(R.id.checkPerimetroCirculo);
        perimetri_txt = (TextView)vista.findViewById(R.id.txt_perimetroCirculo);
        tiranteTxt = (TextView)vista.findViewById(R.id.txt_tirantePerimetroCirculo);
        final int frame = R.id.framePerimetroACirculo;

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalPerimetroCirculo:
                        //Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoCircularFragment(),frame);
                        break;
                    case R.id.velocidadPerimetroCirculo:
                        establecerFragmento(new VelocidadCircular(), frame);
                        break;
                    case R.id.areaPerimetroCirculo:
                        establecerFragmento(new AreaHidraulicaCircular(),frame);
                        break;
                    case R.id.perimetroPerimetroCirculo:
                        establecerFragmento(new PerimetroAreaCirculoFragment(),frame);
                        break;
                    case R.id.radioHidraulicoPerimetroCirculo:
                        establecerFragmento(new RadioHidraulicoCircularFragment(),frame);
                        break;
                }
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diametroEdit.setText("");
                //taludEdit.setText("");
                tiranteEdit.setText("");
                resultado.setText("0");
            }
        });

        maxima_eficiencia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    //penHidraulica.setTextColor(Color.RED);
                    //pendiente.setEnabled(false);
                    tiranteEdit.setEnabled(false);
                    tiranteTxt.setTextColor(Color.RED);

                } else {
                    tiranteEdit.setEnabled(true);
                    tiranteTxt.setTextColor(Color.BLACK);

                }
            }
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (maxima_eficiencia.isChecked()) {
                        double diametroD = Double.parseDouble(diametroEdit.getText().toString());
                        double area, perimetro;
                        double tiranteD = diametroD/2;
                        area = Math.PI * tiranteD * tiranteD / 2;
                        perimetro = Math.PI * tiranteD;
                        resultado.setText(String.valueOf(redondearDecimales(area, 3)) + " m²");
                        perimetri_txt.setText(String.valueOf(redondearDecimales(perimetro, 3)) + " m");
                    } else {
                        double tiranteD = Double.parseDouble(tiranteEdit.getText().toString());
                        double area, perimetro;
                        double diametroD = Double.parseDouble(diametroEdit.getText().toString());
                        double angulo = (2 * Math.PI) - (2 * Math.acos((2 * tiranteD / diametroD) - 1));
                        if (angulo <= 0) {
                            Toast.makeText(getActivity(), "RESULTADO IRREAL", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (angulo == Double.NaN){
                            Toast.makeText(getActivity(),"RESULTADO IRREAL", Toast.LENGTH_LONG).show();
                            return;
                        }

                        double numerador = (angulo-Math.sin(angulo))*Math.pow(diametroD,2);
                        area = numerador/8;
                        perimetro = (diametroD * angulo) / 2;
                        if (area == Double.NaN){
                            Toast.makeText(getActivity(),"RESULTADO IRREAL", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (perimetro == Double.NaN){
                            Toast.makeText(getActivity(),"RESULTADO IRREAL", Toast.LENGTH_LONG).show();
                            return;
                        }
                        resultado.setText(String.valueOf(redondearDecimales(area, 3)) + " m²");
                        perimetri_txt.setText(String.valueOf(redondearDecimales(perimetro, 3)) + " m");
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "FALTAN DATOS", Toast.LENGTH_LONG).show();
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
