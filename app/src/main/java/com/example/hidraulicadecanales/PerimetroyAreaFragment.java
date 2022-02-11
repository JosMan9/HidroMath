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


public class PerimetroyAreaFragment extends Fragment {

    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;
    private EditText anchoEdit,taludEdit,tiranteEdit;
    private TextView resultado,perimetro,anchoText,taludText;
    private Button calcular,limpiar;
    private CheckBox maxima_eficiencia;


    public PerimetroyAreaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_perimetroy_area, container, false);
        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalPerimetro);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadPerimetro);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaPerimetro));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroPerimetro);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoPerimetro);
        String[] opciones = {"","Arroyo de Montañas con muchas piedras","Tepetate (liso y uniforme)",
                "Tierra en buenas condiciones","Tierra libre de vegetacion","Mampostería Seca","Mampostería con cemento"
                ,"Concreto","Asbesto Cemento","Polietileno y PVC","Fierro Fundido (Fo.Fo","Acero","Vidrio,Cobre"};


        /*spinner = (Spinner)vista.findViewById(R.id.spinnerAreaGasto);
        //ArrayAdapter adaptador = ArrayAdapter.createFromResource(getActivity(),R.array.datos,R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,opciones);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptador);*/

        anchoEdit = (EditText)vista.findViewById(R.id.editAnchoArea);
        tiranteEdit = (EditText)vista.findViewById(R.id.editTiranteArea);
        taludEdit = (EditText)vista.findViewById(R.id.editTaludArea);
        resultado = (TextView) vista.findViewById(R.id.txt_resultado_APTrapezial);
        calcular = (Button) vista.findViewById(R.id.calcular_botonArea);
        limpiar = (Button)vista.findViewById(R.id.limpiar_botonArea);
        perimetro = (TextView) vista.findViewById(R.id.txt_perimetroAreaTrapezial);
        anchoText=(TextView) vista.findViewById(R.id.txt_anchoPerimetroRectangulo);
        taludText = (TextView)vista.findViewById(R.id.txt_taludRadio);
        maxima_eficiencia = (CheckBox)vista.findViewById(R.id.checkBox);

        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoPerimetro);
        final int frame = R.id.framePerimetro;
        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalPerimetro:
                        //Toast.makeText(getActivity(),"NOSE",Toast.LENGTH_LONG).show();
                        establecerFragmento(new GastoFragment(),frame);
                        break;
                    case R.id.velocidadPerimetro:
                        establecerFragmento(new VelocidadTrapezialFragment(), frame);
                        break;
                    case R.id.areaPerimetro:
                        establecerFragmento(new AreaHGastoTrapezial(),frame);
                        break;
                    case R.id.perimetroPerimetro:
                        establecerFragmento(new PerimetroyAreaFragment(),frame);
                        break;
                    case R.id.radioHidraulicoPerimetro:
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
                perimetro.setText("0");
            }
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (maxima_eficiencia.isChecked()){
                        //taludD = 1/Math.sqrt(taludD);
                        double tiranteD = Double.parseDouble(tiranteEdit.getText().toString());

                        double area = Math.sqrt(3)*Math.pow(tiranteD,2);
                        double perimetroD = 2*Math.sqrt(3)*tiranteD;
                        resultado.setText(String.valueOf(redondearDecimales(area,3))+" m²");
                        perimetro.setText(String.valueOf(redondearDecimales(perimetroD,3))+" m");
                    }else {
                        double anchoD = Double.parseDouble(anchoEdit.getText().toString());
                        double taludD = Double.parseDouble(taludEdit.getText().toString());
                        double tiranteD = Double.parseDouble(tiranteEdit.getText().toString());
                        double area;
                        double perimetroD;
                        area = (anchoD*tiranteD) + (taludD*Math.pow(tiranteD,2));
                        perimetroD = anchoD + (2*tiranteD*Math.sqrt(1+Math.pow(taludD,2)));
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
