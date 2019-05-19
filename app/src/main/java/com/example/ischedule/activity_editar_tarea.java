package com.example.ischedule;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ischedule.Globales.variables_globales;

public class activity_editar_tarea extends AppCompatActivity {

    Spinner temas;
    String img, edtId;
    EditText titulo, descripcion, fecha, hora, url;

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

        edtId = getIntent().getStringExtra("id");
       // System.out.println("El id que traigo es: " + edtId);
        if(edtId != ""){
            editarEvento(edtId);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.temas_img, android.R.layout.simple_spinner_item);

        temas.setAdapter(adapter);
        temas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //CAPTURAMOS EL NOMBRE DEL TEMA/SPINNER
                img = parent.getItemAtPosition(position).toString();
                //System.out.println(img);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void editarEvento(String edtId) {
        //REALIZAR EL UPDATE PARA LLENAR LOS CAMPOS EN EL FORMULARIO
        Conexion conn = new Conexion(this, "db_tareas", null, 1);
       // SQLiteDatabase db = conn.
    }

    public void crearEvento(View view) {
        crear();
    }

    public void crear(){
        Conexion conn = new Conexion(this, "db_tareas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(variables_globales.campo_titulo, titulo.getText().toString());
        values.put(variables_globales.campo_descripcion, descripcion.getText().toString());
        values.put(variables_globales.campo_fecha, fecha.getText().toString());
        values.put(variables_globales.campo_hora, hora.getText().toString());
        values.put(variables_globales.campo_url, url.getText().toString());
        values.put(variables_globales.campo_img, img);

        Long id = db.insert(variables_globales.tabla,variables_globales.campo_titulo, values);
        if(id != 0){
            Toast.makeText(getApplicationContext(), "El Evento "+titulo.getText().toString()+" se ha agregado a tú I`schedule", Toast.LENGTH_LONG).show();
        }
        db.close();
        Intent miIntent = new Intent(activity_editar_tarea.this, MainActivity.class);
        startActivity(miIntent);
    }
}
