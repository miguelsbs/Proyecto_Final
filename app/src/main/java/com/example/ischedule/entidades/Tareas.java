package com.example.ischedule.entidades;

import com.google.android.gms.maps.GoogleMap;

import java.sql.Date;

public class Tareas {

    private String titulo, descrip, url, fecha, hora;
    private int id, img;
    private GoogleMap map;

    public Tareas(String titulo, String descrip, String url, String fecha, String hora, int id, int img, GoogleMap map) {
        this.titulo = titulo;
        this.descrip = descrip;
        this.url = url;
        this.fecha = fecha;
        this.hora = hora;
        this.id = id;
        this.img = img;
        this.map = map;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public GoogleMap getMap() {
        return map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }
}
