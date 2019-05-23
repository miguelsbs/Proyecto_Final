package com.example.ischedule;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ischedule.Globales.variables_globales;

import java.util.ArrayList;
import java.util.Objects;

public class activity_editar_tarea extends AppCompatActivity {

    Spinner temas;
    String img, idEvento;
    EditText titulo, descripcion, fecha, hora, url;
    String[] datosEvento = new String[6];
    Conexion conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarea);

        temas = (Spinner) findViewById(R.id.sp_1);

        titulo = (EditText) findViewById(R.id.eT_titulo);
        descripcion = (EditText) findViewById(R.id.eT_descrip);
        fecha = (EditText) findViewById(R.id.eT_fecha);
        hora = (EditText) findViewById(R.id.eT_horas);
        url = (EditText) findViewById(R.id.eT_web);

        conn = new Conexion(this, "db_tareas", null, 1);

        //EXTRAEMOS LO QUE NOS VIENE DESDE LA VENTANA EVENTO
        datosEvento = getIntent().getStringArrayExtra("datosEvento");
        idEvento = getIntent().getStringExtra("id");
        System.out.println("Id del EVENTO: " + idEvento);

        if(!Objects.equals(datosEvento, "")){
            editarEvento(datosEvento);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.temas_img, android.R.layout.simple_spinner_item);

        temas.setAdapter(adapter);
        temas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //CAPTURAMOS EL NOMBRE DEL TEMA/SPINNER
                img = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void editarEvento(String[] datos) {
        String listaImg[] = new String[]{"Temas", "Boda", "Cita de Negocios", "Cita Romantica",
                "Comida", "Compras", "Concierto", "Cumpleaños", "Estudios", "Examen", "Médico",
                "Oficina", "Partido", "Reunión Amigos", "Reunión Familiar", "Vacaciones", "Viaje"};
        try{
            titulo.setText(datos[0]);
            descripcion.setText(datos[1]);
            fecha.setText(datos[2]);
            hora.setText(datos[3]);
            url.setText(datos[4]);

            //ARREGLAR ESTE PEO DEL SPINNER
//            for(int i=0; i < listaImg.length; i++){
//                if(datos[5].equals(listaImg[i])){
//                    System.out.println("LLEGO AQUI");
//                    temas.setSelection(i);
//                }
//            }
            //temas.setSe

            //Colocamos el valor al Spinner
            temas.setSelection(obtenerPosicionItem(listaImg, datos[5]));
        }catch (NullPointerException e){
            e.getMessage();
        }

    }

    public void crearEvento(View view) {
        crear();
    }

    public void crear(){
     //   Conexion conn = new Conexion(this, "db_tareas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(variables_globales.campo_titulo, titulo.getText().toString());
        values.put(variables_globales.campo_descripcion, descripcion.getText().toString());
        values.put(variables_globales.campo_fecha, fecha.getText().toString());
        values.put(variables_globales.campo_hora, hora.getText().toString());
        values.put(variables_globales.campo_url, url.getText().toString());
        values.put(variables_globales.campo_img, img);

        try{
            Long id = db.insert(variables_globales.tabla,variables_globales.campo_titulo, values);
            if(id != 0){
                Toast.makeText(getApplicationContext(), "El Evento "+titulo.getText().toString()+" se ha agregado a tú I`schedule", Toast.LENGTH_LONG).show();
            }
            db.close();
        }catch (SQLiteException s){
            s.getMessage();
        }catch (NullPointerException n){
            n.getMessage();
        }finally {
            Intent miIntent = new Intent(activity_editar_tarea.this, MainActivity.class);
            startActivity(miIntent);
        }

    }

    public static int obtenerPosicionItem(String[] temas, String tema) {
        int posicion = 0;
        for (int i = 0; i < temas.length; i++) {
            if (temas[i].equalsIgnoreCase(tema)) {
                posicion = i;
            }
        }
        return posicion;
    }

    public void editarEvento(View view) {
        System.out.println("Id del EVENTO dentro de la funcion de ACTUALIZAR: " + idEvento);
        String[] parametro = {idEvento};
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (int i=0; i < datosEvento.length; i++){
            System.out.println("Los datos del ARRAY: "+ datosEvento[i]);
        }

        values.put(variables_globales.campo_titulo, datosEvento[0]);
        values.put(variables_globales.campo_descripcion, datosEvento[1]);
        values.put(variables_globales.campo_fecha, datosEvento[2]);
        values.put(variables_globales.campo_hora, datosEvento[3]);
        values.put(variables_globales.campo_url, datosEvento[4]);
        values.put(variables_globales.campo_img, datosEvento[5]);

//        int res = db.update(variables_globales.tabla, values, variables_globales.campo_id+"="+idEvento, null);
//        //       db.execSQL("UPDATE tarea SET titulo="+ datos[0] +"WHERE _id="+ parametro);
//        if(res != 0){
//            Toast.makeText(getApplicationContext(), "El Evento "+titulo.getText().toString()+" se ha actualizado en tú I`schedule", Toast.LENGTH_LONG).show();
//        }
//        db.close();
        try{
            int res = db.update(variables_globales.tabla, values, variables_globales.campo_id+"=?", parametro);
            if(res != 0){
                Toast.makeText(getApplicationContext(), "El Evento "+titulo.getText().toString()+" se ha actualizado en tú I`schedule", Toast.LENGTH_LONG).show();
            }
            db.close();
        }catch (SQLiteException s){
            s.getMessage();
        }catch (NullPointerException n){
            n.getMessage();
        }finally {
            Intent miIntent = new Intent(activity_editar_tarea.this, MainActivity.class);
            startActivity(miIntent);
        }
    }

}
