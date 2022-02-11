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


/**
 * A simple {@link Fragment} subclass.
 */
public class RadioHidraulicoFragment extends Fragment {

    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;
    private EditText anchoEdit,taludEdit,tiranteEdit;
    private TextView resultado,anchoText,taludText;
    private Button calcular,limpiar;
    private CheckBox maxima_eficiencia;


    public RadioHidraulicoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_radio_hidraulico, container, false);
        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalRadio);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadRadio);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaRadio));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroRadio);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoRadio);
        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoRadio);

        anchoEdit = (EditText)vista.findViewById(R.id.editAnchoRadioTrapezial);
        tiranteEdit = (EditText)vista.findViewById(R.id.editTiranteRadioTrapezial);
        taludEdit = (EditText)vista.findViewById(R.id.editTaludRadioTrapezial);
        resultado = (TextView) vista.findViewById(R.id.txt_resultado_RadioTrapezial);
        calcular = (Button) vista.findViewById(R.id.calcular_botonRadioTrapezial);
        limpiar = (Button)vista.findViewById(R.id.limpiar_botonRadioTrapezial);
        anchoText = (TextView)vista.findViewById(R.id.txt_anchoRadioTrapezial);
        taludText = (TextView)vista.findViewById(R.id.txt_taludRadioTrapezial);
        maxima_eficiencia = (CheckBox)vista.findViewById(R.id.checkRadioTrapezial);
        final int frame = R.id.frameRadio;
        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalRadio:
                        //Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoFragment(),frame);
                        break;
                    case R.id.velocidadRadio:
                        establecerFragmento(new VelocidadTrapezialFragment(), frame);
                        break;
                    case R.id.areaRadio:
                        establecerFragmento(new AreaHGastoTrapezial(),frame);
                        break;
                    case R.id.perimetroRadio:
                        establecerFragmento(new PerimetroyAreaFragment(),frame);
                        break;
                    case R.id.radioHidraulicoRadio:
                        establecerFragmento(new RadioHidraulicoFragment(),frame);
                        break;
                }
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anchoEdit.setText("");
                taludEdit.setText("");
                tiranteEdit.setText("");
                resultado.setText("0");
            }
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (maxima_eficiencia.isChecked()){
                        double radio;
                        double tiranteD = Double.parseDouble(tiranteEdit.getText().toString());
                        radio = tiranteD/2;
                        resultado.setText(String.valueOf(redondearDecimales(radio,3))+" m");
                    }else {
                        double radio;
                        double tiranteD = Double.parseDouble(tiranteEdit.getText().toString());
                        //tiranteD = 1/Math.tan(tiranteD);
                        double anchoD = Double.parseDouble(anchoEdit.getText().toString());
                        double taludD = Double.parseDouble(taludEdit.getText().toString());
                        double area = (anchoD*tiranteD) + (taludD*Math.pow(tiranteD,2));
                        double perimetroD = anchoD + (2*tiranteD*Math.sqrt(1+Math.pow(taludD,2)));
                        radio = area/perimetroD;
                        resultado.setText(String.valueOf(redondearDecimales(radio,3))+ " m");
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
                    taludEdit.setEnabled(false);
                    taludText.setTextColor(Color.RED);
                    anchoEdit.setEnabled(false);
                    anchoText.setTextColor(Color.RED);

                } else {
                    taludEdit.setEnabled(true);
                    taludText.setTextColor(Color.BLACK);
                    anchoEdit.setEnabled(true);
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
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }

}
