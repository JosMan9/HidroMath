package com.example.hidraulicadecanales;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PerimetroATriangularFragment extends Fragment {

    private RadioButton gasto;
    private Fragment calculo_gasto;
    private RadioButton botonGasto;
    private RadioButton botonAreaGasto;
    private RadioButton botonVelocidad;
    private RadioButton botonAreaPerimetro;
    private RadioButton botonradio;
    private Button calcular,limpiar;
    private EditText talud,tirante;
    private TextView resultado,perimetro;
    private CheckBox maxima_eficiencia;


    public PerimetroATriangularFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_perimetro_atriangular, container, false);
        botonGasto = (RadioButton)vista.findViewById(R.id.radioCaudalPerimetroTriangular);
        botonVelocidad = (RadioButton)vista.findViewById(R.id.velocidadPerimetroTriangular);
        botonAreaGasto = (RadioButton)vista.findViewById((R.id.areaPerimetroTriangular));
        botonAreaPerimetro = (RadioButton)vista.findViewById(R.id.perimetroPerimetroTriangular);
        botonradio = (RadioButton)vista.findViewById(R.id.radioHidraulicoPerimetroTriangular);
        RadioGroup grupo = (RadioGroup)vista.findViewById(R.id.grupoPerimetroTriangular);

        talud = (EditText)vista.findViewById(R.id.editTaludPerimetroTriangular);
        tirante = (EditText)vista.findViewById(R.id.editTirantePerimetroTriangular);
        calcular = (Button)vista.findViewById(R.id.calcular_botonPerimetroTriangular);
        limpiar = (Button)vista.findViewById(R.id.limpiar_botonPerimetroTriangular);
        resultado = (TextView)vista.findViewById(R.id.txt_ResultadoAreaTriangular);
        perimetro = (TextView)vista.findViewById(R.id.txt_perimetroTriangular);
        final int frame = R.id.framePerimetroTriangular;

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioCaudalPerimetroTriangular:
                        establecerFragmento(new GastoTriangularFragment(),frame);
                        break;
                    case R.id.velocidadPerimetroTriangular:
                        establecerFragmento(new VelocidadTrangularFragment(), frame);
                        break;
                    case R.id.areaPerimetroTriangular:
                        establecerFragmento(new AreaHGastoTriangularFragment(),frame);
                        break;
                    case R.id.perimetroPerimetroTriangular:
                        establecerFragmento(new PerimetroATriangularFragment(),frame);
                        break;
                    case R.id.radioHidraulicoPerimetroTriangular:
                        establecerFragmento(new RadioHidraulicoTriangularFragment(),frame);
                        break;
                }
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                talud.setText("");
                tirante.setText("");
                resultado.setText("0");
            }
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double taludD = Double.parseDouble(talud.getText().toString());
                    double tiranteD = Double.parseDouble(tirante.getText().toString());
                    double area = (taludD*Math.pow(tiranteD,2))/2;
                    double perimetroD = 2*tiranteD*Math.sqrt(1+Math.pow(taludD,2));
                    resultado.setText(String.valueOf(redondearDecimales(area,3))+" m²");
                    perimetro.setText(String.valueOf(redondearDecimales(perimetroD,3))+" m");
                } catch(Exception e){
                    Toast.makeText(getActivity(),"FALTAN DATOS",Toast.LENGTH_LONG).show();
                }
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
