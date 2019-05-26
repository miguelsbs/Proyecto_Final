package com.example.ischedule;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ischedule.Globales.variables_globales;

import java.util.ArrayList;
import java.util.Objects;

public class Evento extends AppCompatActivity {

    String edtId = "";
    ImageView fondo, star;
    TextView titulo, descripcion, fecha, hora, web;
    Button btnVolver, btnEditar;
    Conexion conn;
    String[] datos = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        edtId = getIntent().getStringExtra("id");
        conn = new Conexion(this, "db_tareas", null, 1);

        fondo = findViewById(R.id.imgEvento);
        titulo = findViewById(R.id.tV_titulo);
        descripcion = findViewById(R.id.tV_Descripcion);
        fecha = findViewById(R.id.tV_Fecha);
        hora = findViewById(R.id.tV_Hora);
        web = findViewById(R.id.tV_url);
        star = findViewById(R.id.iV_star);
        //web.setText(Html.fromHtml("web del evento: www.youtube.com"));
        Linkify.addLinks(web, Linkify.WEB_URLS);

        if(!Objects.equals(edtId, "")){
            datos = consultaEvento(edtId);
          //  star.setVisibility(View.VISIBLE);
        }

        btnVolver = (Button) findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent atras = new Intent(Evento.this, MainActivity.class);
                Evento.this.finish();
                startActivity(atras);
            }
        });

        btnEditar = (Button) findViewById(R.id.btnEdit);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editar = new Intent(Evento.this, activity_editar_tarea.class);
                editar.putExtra("datosEvento", datos);
                editar.putExtra("id", edtId);
                Evento.this.finish();
                startActivity(editar);
            }
        });
    }

    private String[] consultaEvento(String edtId) {
        String[] datosConsulta = new String[7];
        String[] parametro = {edtId};
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {variables_globales.campo_titulo, variables_globales.campo_descripcion, variables_globales.campo_fecha, variables_globales.campo_hora,
                            variables_globales.campo_url, variables_globales.campo_img, variables_globales.campo_favorito};

        try {
            Cursor cursor = db.query(variables_globales.tabla, campos, variables_globales.campo_id+"=?", parametro, null, null, null);
            cursor.moveToFirst();
            titulo.setText(cursor.getString(0));
            descripcion.setText(cursor.getString(1));
            fecha.setText(cursor.getString(2));
            hora.setText(cursor.getString(3));
            web.setText(Html.fromHtml(cursor.getString(4)));
            fondo.setImageResource(enviarImg(cursor.getString(5)));
            if(Objects.equals(cursor.getString(6), "1")){
                star.setVisibility(View.VISIBLE);
            }

            for(int i=0; i < 7; i++){
                datosConsulta[i] = cursor.getString(i);
                System.out.println("Dato: " + datosConsulta[i]);
            }
            cursor.close();
        }catch (SQLiteException e){
            e.getMessage();
        }catch (NullPointerException f){
            f.getMessage();
        }
        return datosConsulta;
    }

    private int enviarImg(String nomImg) {
        int aux = 0;
        int imgPosition[] = {R.drawable.temas_2, R.drawable.boda_2, R.drawable.cita_negocios_2, R.drawable.cita_romantica_2, R.drawable.comida_2, R.drawable.compras_2, R.drawable.concierto_2, R.drawable.cumpleanos_2, R.drawable.estudios_2, R.drawable.examen_2, R.drawable.medico_2, R.drawable.oficina_2, R.drawable.partido_2, R.drawable.reunion_amigos_2, R.drawable.reunion_familiar_2, R.drawable.vacaciones_2, R.drawable.viaje_2};
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

    public void eliminar(View view) {
        String[] parametro = {edtId};
        SQLiteDatabase db = conn.getWritableDatabase();
        try{
            db.delete(variables_globales.tabla, variables_globales.campo_id+"=?", parametro);
            Toast.makeText(getApplicationContext(), "El Evento "+titulo.getText().toString()+" se ha ELIMINADO de tú I`schedule", Toast.LENGTH_LONG).show();
            db.close();
        }catch (SQLiteException s){
            s.getMessage();
        }finally {
            Intent editar = new Intent(Evento.this, MainActivity.class);
            Evento.this.finish();
            startActivity(editar);
        }
    }
}
