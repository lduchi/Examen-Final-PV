/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Image;
import java.io.FileInputStream;

/**
 *
 * @author Usuario
 */
public class Pizza {
   private int codigo;
   private String nombre,tamanio,ingredientes;
   private double costo,pvp;
    private FileInputStream foto;
    private int longitud;

    //Recuperar imagen
    private Image imagen;
    public Pizza() {
    }

    public Pizza(int codigo, String nombre, String tamanio, String ingredientes, double costo, double pvp, FileInputStream foto, int longitud, Image imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.ingredientes = ingredientes;
        this.costo = costo;
        this.pvp = pvp;
        this.foto = foto;
        this.longitud = longitud;
        this.imagen = imagen;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public FileInputStream getFoto() {
        return foto;
    }

    public void setFoto(FileInputStream foto) {
        this.foto = foto;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }
   
   
}
