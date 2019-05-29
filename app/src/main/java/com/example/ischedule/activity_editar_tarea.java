package com.example.ischedule;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ischedule.Globales.variables_globales;

import java.util.ArrayList;
import java.util.Objects;

public class activity_editar_tarea extends AppCompatActivity {

  //  private static final String TEXT_STATE = "currentText";
    private TextView miMensaje;
    private Spinner temas;
    private TextView titEvento;
    private Switch favorito;
    private String img, idEvento;
    private EditText titulo, descripcion, fecha, hora, url;
    private String[] datosEvento = new String[7];
    private static String[] listaImg = new String[]{"Temas", "Boda", "Cita de Negocios", "Cita Romantica",
            "Comida", "Compras", "Concierto", "Cumpleaños", "Estudios", "Examen", "Médico",
            "Oficina", "Partido", "Reunión Amigos", "Reunión Familiar", "Vacaciones", "Viaje"};
    Conexion conn;
    private Button btnCrear;
    private int fav = 0;

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
        favorito = (Switch) findViewById(R.id.sw_Fav);
        btnCrear = (Button) findViewById(R.id.btnCrear);
        titEvento = (TextView) findViewById(R.id.tV_1);

        conn = new Conexion(this, "db_tareas", null, 1);

        //EXTRAEMOS LO QUE NOS VIENE DESDE LA VENTANA EVENTO
       // datosEvento = getIntent().getStringArrayExtra("datosEvento");
        idEvento = getIntent().getStringExtra("id");
        System.out.println("Id del EVENTO: " + idEvento);

        if(!Objects.equals(idEvento, null)){
        //    System.out.println("ESTOY EDITANDO");
            btnCrear.setText("Actualizar");
            titEvento.setText("Editar Evento");
            editarEvento();

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

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav = 1;
            }
        });

        miMensaje = findViewById(R.id.tFav);
        //SOLO NOS MUESTRA EL MENSAJE DE LA TAREAASYNC SI EL EVENTO NO ES FAVORITO
        if(!favorito.isChecked()){
            new TareasAsync(miMensaje).execute();
        }


    }

    private void editarEvento() {
        SharedPreferences datosEvento = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String fav = "";

        try{
            titulo.setText(datosEvento.getString("titulo", "Error en la consulta"));
            descripcion.setText(datosEvento.getString("descripcion", "Error en la consulta"));
            fecha.setText(datosEvento.getString("fecha", "Error en la consulta"));
            hora.setText(datosEvento.getString("hora", "Error en la consulta"));
            url.setText(datosEvento.getString("url", "Error en la consulta"));
            temas.setSelection(obtenerPosicionItem(datosEvento.getString("temas", "Error en la consulta")));

            fav = datosEvento.getString("favorito", "Error en la consulta");
            if(Integer.parseInt(fav) == 1){
                favorito.setChecked(true);
            }
//            titulo.setText(datos[0]);
//            descripcion.setText(datos[1]);
//            fecha.setText(datos[2]);
//            hora.setText(datos[3]);
//            url.setText(datos[4]);
//            temas.setSelection(obtenerPosicionItem(datos[5]));
//            if(Integer.parseInt(datos[6]) == 1){
//                favorito.setChecked(true);
//            }
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
        values.put(variables_globales.campo_favorito, fav);

        try{
            Long id = db.insert(variables_globales.tabla,variables_globales.campo_titulo, values);
//            if(id != 0){
//                Toast.makeText(getApplicationContext(), "El Evento "+titulo.getText().toString()+" se ha agregado a tú I`schedule", Toast.LENGTH_LONG).show();
//            }
            db.close();
        }catch (SQLiteException s){
            s.getMessage();
        }catch (NullPointerException n){
            n.getMessage();
        }finally {
            Intent miIntent = new Intent(activity_editar_tarea.this, MainActivity.class);
            miIntent.putExtra("evento", titulo.getText().toString());//MANDAMOS EL TITULO DEL EVENTO
            activity_editar_tarea.this.finish();
            startActivity(miIntent);
        }

    }

    public static int obtenerPosicionItem(String tema) {
        int posicion = 0;
        for (int i = 0; i < listaImg.length; i++) {
            if (listaImg[i].equalsIgnoreCase(tema)) {
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
            activity_editar_tarea.this.finish();
            startActivity(miIntent);
        }
    }

}
