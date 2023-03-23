/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.VistaPizzeria;

/**
 *
 * @author Usuario
 */
public class ControladorPrincipal {

    VistaPizzeria vistaPrincipal;

    public ControladorPrincipal(VistaPizzeria vistapizzeria) {
        this.vistaPrincipal = vistapizzeria;
        vistapizzeria.setVisible(true);
        vistapizzeria.setLocationRelativeTo(null);
    }

    public void iniciaControl() {
        vistaPrincipal.getBtnCrear().addActionListener(l -> CrearPizza());

    }

    public void CrearPizza() {
        
    }
}
