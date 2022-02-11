package com.example.hidraulicadecanales;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarCorreo();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    protected void enviarCorreo() {
        String TO = "umalucsasociacion@gmail.com";
        String CC = "";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);

        // Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
        //emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asunto");
        //emailIntent.putExtra(Intent.EXTRA_TEXT, "Escribe aquí tu mensaje");

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
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
                if (id == R.id.flujo_normal_rectangular) {
                    ejecutarNormalRectangular(null);
                    return true;
                } else {
                    if (id == R.id.flujo_normal_triangular) {
                        ejecutarNormalTriangular(null);
                        return true;
                    } else {
                        if (id == R.id.flujo_normal_circular) {
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actibity_main_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_informacion:
                Intent i = new Intent(this,Main2Activity.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }

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
