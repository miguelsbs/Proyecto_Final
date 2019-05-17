package com.example.ischedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Datos_Mask> listaDatos;
    RecyclerView tareas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent miIntent = new Intent(MainActivity.this, activity_editar_tarea.class);
                startActivity(miIntent);

            }
        });

        listaDatos = new ArrayList<Datos_Mask>();
        tareas = (RecyclerView) findViewById(R.id.listaTareas);
        tareas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        llenarLista();

        Adaptador_Mask adapter = new Adaptador_Mask(listaDatos);
        tareas.setAdapter(adapter);

        //Conexion conn = new Conexion(this, "db_tareas", null, 1);


    }

    private void llenarLista() {
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
