package com.example.hidraulicadecanales;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private Fragment informacion_fragmento;
    private BottomNavigationView navegacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        informacion_fragmento = new InformacionActionBarFragment();
        final int frame = R.id.frameActivityMain2;
        establecerFragmento(informacion_fragmento,frame);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationInfo);
        navegacion = (BottomNavigationView)findViewById(R.id.navigationInfo);
        navegacion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.informacion_navegador:
                        establecerFragmento(informacion_fragmento,frame);
                        return true;
                    case R.id.concpetos_navegador:
                        establecerFragmento(new ConceptosFragment(),frame);
                        return true;
                    case R.id.formuula_navegador:
                        establecerFragmento(new Formulas_GeneralFragment(),frame);
                        return true;
                    case R.id.secciones_navegador:
                        establecerFragmento(new SeccionesFragment(),frame);
                        return true;
                }
                return false;
            }
        });
    }


    private void establecerFragmento(Fragment fragment,int frame){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frame,fragment);
        transaction.commit();
    }


    private void ejecutarInformacion(View view){
        Intent i = new Intent(this, InfromacionActivity.class);
        startActivity(i);
    }

    private void ejecutarContacto(View view){
        Intent i = new Intent(this,ContactoActivity.class);
        startActivity(i);
    }

}
