package com.example.ischedule.Globales;

public class variables_globales {

    //Variables globales de la tabla tarea
    public static final String tabla = "tarea";
    public static final String campo_titulo = "titulo";
    public static final String campo_descripcion = "descripcion";
    public static final String campo_fecha = "fecha";
    public static final String campo_hora = "hora";
    public static final String campo_url = "url";
    public static final String campo_img = "img";

    public static final String CREAR_TABLA_TAREA = "CREATE TABLE "+tabla+" ("+campo_titulo+" TEXT, "+campo_descripcion+" TEXT, "+campo_fecha+" TEXT, "+campo_hora+"" +
            " TEXT, "+campo_url+" TEXT, "+campo_img+" TEXT)";
}
