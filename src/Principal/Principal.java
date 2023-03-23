/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Controlador.ControladorPrincipal;
import Vista.VistaPizzeria;

/**
 *
 * @author Usuario
 */
public class Principal {
     public static void main(String[] args) {

         VistaPizzeria vistaPrincipal = new VistaPizzeria();

        ControladorPrincipal control = new ControladorPrincipal(vistaPrincipal);
        control.iniciaControl();

    }
}
