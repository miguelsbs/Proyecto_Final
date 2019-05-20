package com.example.ischedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Evento extends AppCompatActivity {

    String edtId;
    ImageView fondo;
    TextView titulo, descripcion, fecha, hora;
    //Button volver, editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        fondo = (ImageView) findViewById(R.id.imgEvento);
        titulo = (TextView) findViewById(R.id.tV_titulo);
        descripcion = (TextView) findViewById(R.id.tV_Descripcion);
        fecha = (TextView) findViewById(R.id.tV_Fecha);
        hora = (TextView) findViewById(R.id.tV_Hora);

        edtId = getIntent().getStringExtra("id");
        // System.out.println("El id que traigo es: " + edtId);
        if(edtId != ""){
            consultaEvento(edtId);
        }



    }

    private void consultaEvento(String edtId) {
        //CONSULTA A LA BASE DE DATOS CON UN WHERE  id=edtId
        //COLOCAMOS LOS VALORES DE LA CONSULTA EN LA INTERFAZ DE LA VENTANA

    }
}
