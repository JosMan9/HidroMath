package com.example.hidraulicadecanales;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.*;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Trapezial_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment calculo_fragmento;
    private BottomNavigationView navegacion;

    private EditText gastoEdit,radio,pendiente;
    private TextView resultado;
    private Button calcular,limpiar;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trapezial_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        navegacion = (BottomNavigationView)findViewById(R.id.navigationTrapezial);

        calculo_fragmento = new OpctionsTrapezialFragment();
        final int frame =  R.id.frameTrapezial;

        /*gastoEdit = (EditText)findViewById(R.id.editGastoArea);
        radio = (EditText)findViewById(R.id.editRadioArea);
        pendiente = (EditText)findViewById(R.id.editPendienteArea);
        resultado = (TextView)findViewById(R.id.txt_resultado_ATrapezial);
        calcular = (Button) findViewById(R.id.calcular_botonATrapezial);
        limpiar = (Button)findViewById(R.id.limpiar_botonATrapezial);*/

        establecerFragmento(calculo_fragmento,frame);



        navegacion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.calculo_navegador:
                        establecerFragmento(calculo_fragmento,frame);
                        return true;
                    case R.id.tablas_navegador:
                        establecerFragmento(new TablaFragment(),frame);
                        return true;
                    case R.id.nomenclatura_navegador:
                        establecerFragmento(new Formula_TrapezialFragment(),frame);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.flujo_normal){
            ejecutarNormalTrapezial(null);
            return true;
        } else {
            if (id == R.id.canales){
                ejecutarPrincipal(null);
                return  true;
            } else {
                if (id == R.id.flujo_normal_rectangular){
                    ejecutarNormalRectangular(null);
                    return true;
                } else {
                    if (id == R.id.flujo_normal_triangular){
                        ejecutarNormalTriangular(null);
                        return true;
                    } else {
                        if (id == R.id.flujo_normal_circular){
                            ejecutarNormalCircular(null);
                            return true;
                        } else {
                            if (id == R.id.infoDrawer){
                                ejecutarInformacion(null);
                                return true;
                            } else {
                                if (id == R.id.redes){
                                    ejecutarContacto(null);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ejecutarNormalTrapezial(View vista){
        Intent intento = new Intent(this, Trapezial_Activity.class);
        startActivity(intento);
    }

    private void ejecutarPrincipal (View vista){
        Intent intento = new Intent(this,MainActivity.class);
        startActivity(intento);
    }

    private void ejecutarNormalRectangular(View vs){
        Intent intent = new Intent(this,Normal_Rectangular_Activity.class);
        startActivity(intent);
    }

    private void ejecutarNormalCircular(View view){
        Intent intento = new Intent(this,Normal_Circular_Activity.class);
        startActivity(intento);
    }

    private void ejecutarNormalTriangular(View view){
        Intent i = new Intent(this, Normal_Triangular_Activity.class);
        startActivity(i);
    }


    public void limpiar(View view){
        gastoEdit.setText("");
        pendiente.setText("");
        radio.setText("");
        resultado.setText("0");
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
