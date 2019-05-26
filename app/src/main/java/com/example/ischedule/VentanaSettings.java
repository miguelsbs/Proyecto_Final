package com.example.ischedule;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ischedule.Globales.variables_globales;

public class VentanaSettings extends AppCompatActivity {

    Conexion conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_settings);

        conn = new Conexion(this, "db_tareas", null, 1);
    }

    public void limpiar(View view) {
        AlertDialog.Builder aviso = new AlertDialog.Builder(view.getContext());
        aviso.setTitle("Aviso!");
        aviso.setMessage("¿Deseas vacíar la lista de Eventos?");
        aviso.setCancelable(false);
        aviso.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface aviso, int id) {
                SQLiteDatabase db = conn.getWritableDatabase();
                try{
                    db.delete(variables_globales.tabla, null, null);
                    db.close();
                }catch (SQLiteException s){
                    s.getMessage();
                }finally {
                    Intent editar = new Intent(VentanaSettings.this, MainActivity.class);
                    VentanaSettings.this.finish();
                    startActivity(editar);
                }

            }
        });

        aviso.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface aviso, int id) {
                aviso.cancel();
            }
        });
        aviso.show();
    }
}
