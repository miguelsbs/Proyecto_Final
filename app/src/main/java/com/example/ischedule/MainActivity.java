package com.example.ischedule;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ischedule.Globales.variables_globales;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ArrayList<Datos_Mask> listaDatos;
    RecyclerView tareas;
    Conexion conn;

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
        conn = new Conexion(this, "db_tareas", null, 1);
        listaDatos = new ArrayList<Datos_Mask>();
        tareas = (RecyclerView) findViewById(R.id.listaTareas);
        tareas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        llenarLista();
        Adaptador_Mask adapter = new Adaptador_Mask(listaDatos);
        final Intent MiIntent = new Intent(MainActivity.this, Evento.class);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final Intent miIntent = new Intent(MainActivity.this, activity_editar_tarea.class);
//                miIntent.putExtra("id", String.valueOf(listaDatos.get(tareas.getChildAdapterPosition(v)).getId()));
//                AlertDialog.Builder aviso = new AlertDialog.Builder(v.getContext());
//                aviso.setTitle("Aviso!");
//                aviso.setMessage("¿Desea modificar este Evento?");
//                aviso.setCancelable(false);
//                aviso.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface aviso, int id) {
//                        startActivity(miIntent);
//                    }
//                });
//                aviso.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface aviso, int id) {
//                        aviso.cancel();
//                    }
//                });
//                aviso.show();

            //    System.out.println("Este es el ID antes de ir a la ventana: " + listaDatos.get(tareas.getChildAdapterPosition(v)).getId());
                String id = String.valueOf(listaDatos.get(tareas.getChildAdapterPosition(v)).getId());
                MiIntent.putExtra("id", id);
                startActivity(MiIntent);
               //Toast.makeText(getApplicationContext(),
                 //       "Seleccion: " + listaDatos.get(tareas.getChildAdapterPosition(v)).getId(), Toast.LENGTH_LONG).show();
            }
        });
        tareas.setAdapter(adapter);

    }

    private void llenarLista() {
        //listaDatos.add(new Datos_Mask("compras","Nos vamos de Compras",R.drawable.compras));
        // datos.setImg(cursor.getInt(5));
        SQLiteDatabase db = conn.getReadableDatabase();
        Datos_Mask datos = null;
        Cursor cursor = db.rawQuery(variables_globales.Consul_select+variables_globales.tabla, null);
        while(cursor.moveToNext()){
            datos = new Datos_Mask(cursor.getString(1),cursor.getString(2),(int) enviarDatos(cursor.getString(6)), cursor.getInt(0));
            listaDatos.add(datos);
        }
        db.close();
    }

    private int enviarDatos(String nomImg) {
        int aux = 0;
        int imgPosition[] = {R.drawable.temas, R.drawable.boda, R.drawable.cita_negocios, R.drawable.cita_romantica, R.drawable.comida, R.drawable.compras, R.drawable.concierto, R.drawable.cumpleanos, R.drawable.estudios, R.drawable.examen, R.drawable.medico, R.drawable.oficina, R.drawable.partido, R.drawable.reunion_amigos, R.drawable.reunion_familiar, R.drawable.vacaciones, R.drawable.viaje};
        String listaImg[] = new String[]{"Temas", "Boda", "Cita de Negocios", "Cita Romantica",
                "Comida", "Compras", "Concierto", "Cumpleaños", "Estudios", "Examen", "Médico",
                "Oficina", "Partido", "Reunión Amigos", "Reunión Familiar", "Vacaciones", "Viaje"};
        for(int i=0; i < listaImg.length; i++){
            if(Objects.equals(listaImg[i], nomImg)){
               aux = imgPosition[i];
            }else if(nomImg == null){
                aux = imgPosition[0];
            }
        }
        return aux;
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
