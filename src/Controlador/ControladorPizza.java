/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConectPG;
import Modelo.ModeloPizza;
import Modelo.Pizza;
import Vista.VistaPizzeria;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.xml.ws.Holder;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Usuario
 */
public class ControladorPizza {

    ModeloPizza modelo;
    VistaPizzeria vista;

    private JFileChooser jfc; //Objeto de tipo JFileChooser

    public ControladorPizza(ModeloPizza modelo, VistaPizzeria vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);//Aprovecho el constructor para hacer visible la vista 
//        cargarProductosTabla(); //Carga los datos al iniciar la ventana
    }

    public void iniciarControl() {

        vista.getBtnCrear().addActionListener(l -> abrirDialogCrear());
        vista.getBtnModificar().addActionListener(l -> abrirYCargarDatosEnElDialog());
        vista.getBtnGuardar().addActionListener(l -> crearEditarPizza());
        vista.getBtnFoto().addActionListener(l -> seleccionarFoto());
        vista.getBtnEliminar().addActionListener(l -> eliminarProducto());
        vista.getBtnImprimir().addActionListener(l -> imprimirPizza());

    }

    public void abrirDialogCrear() {
        vista.getjDlgCrearPizza().setName("Crear nueva pizza");
        vista.getjDlgCrearPizza().setSize(546, 486);
        vista.getjDlgCrearPizza().setTitle("Crear nueva pizza");
        vista.getjDlgCrearPizza().setVisible(true);
        vista.getjDlgCrearPizza().setLocationRelativeTo(null);

        //Limpiar los datos del jDialog
        limpiarDatos();
    }

    private void crearEditarPizza() {
        if ("Crear nueva pizza".equals(vista.getjDlgCrearPizza().getName())) {

            //INSERTAR
            String nombre = vista.getTxtNombre().getText();
            double precio = (Double) vista.getSpinnerPvp().getValue();
            double costo = (Double) vista.getSpinnerCosto().getValue();
            String ingredinte = vista.getTxtIngredientes().getText();

            ModeloPizza producto = new ModeloPizza();

            producto.setNombre(nombre);
            producto.setPvp(precio);
            producto.setCosto(costo);
            producto.setIngredientes(ingredinte);

            if (vista.getLblCargarFoto().getIcon() == null) { //Verifico si el label esta vacio o no

                if (producto.crearProductoSinFoto()) {
                    vista.getjDlgCrearPizza().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Producto Creado Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo crear el producto");
                }

            } else {

                //Foto
                try {

                    FileInputStream foto = new FileInputStream(jfc.getSelectedFile());
                    int longitud = (int) jfc.getSelectedFile().length();

                    producto.setFoto(foto);
                    producto.setLongitud(longitud);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ControladorPizza.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (producto.crearPizzaFoto()) {
                    vista.getjDlgCrearPizza().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Producto Creado Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo crear el producto");
                }
            }

        } else {

            //EDITAR
            int codigo = Integer.parseInt(vista.getTxtCodigo().getText());
            String nombre = vista.getTxtNombre().getText();
            double precio = (Double) vista.getSpinnerPvp().getValue();
            double costo = (Double) vista.getSpinnerCosto().getValue();
            String ingredientes = vista.getTxtIngredientes().getText();

            ModeloPizza producto = new ModeloPizza();

            producto.setCodigo(codigo);
            producto.setNombre(nombre);
            producto.setPvp(precio);
            producto.setIngredientes(ingredientes);
            producto.setCosto(costo);

            if (vista.getLblCargarFoto().getIcon() == null) {
                if (producto.modificarProductoSinFoto()) {

                    vista.getjDlgCrearPizza().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Producto creado Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo modificar el producto");
                }
            } else {

                //Foto
                try {

                    FileInputStream img = new FileInputStream(jfc.getSelectedFile());
                    int longitud = (int) jfc.getSelectedFile().length();
                    producto.setFoto(img);
                    producto.setLongitud(longitud);
                } catch (FileNotFoundException | NullPointerException ex) {
                    Logger.getLogger(ControladorPizza.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (producto.modificarPizzaFoto()) {

                    vista.getjDlgCrearPizza().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Producto creado Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo modificar el producto");
                }
            }
        }

//        cargarProductosTabla; //Actualizo la tabla con los datos
    }

    public void seleccionarFoto() {

        vista.getLblCargarFoto().setIcon(null);
        jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int estado = jfc.showOpenDialog(null);

        if (estado == JFileChooser.APPROVE_OPTION) {
            try {
                Image imagen = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(vista.getLblCargarFoto().getWidth(), vista.getLblCargarFoto().getHeight(), Image.SCALE_DEFAULT);
                vista.getLblCargarFoto().setIcon(new ImageIcon(imagen));
                vista.getLblCargarFoto().updateUI();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(vista, "Error: " + ex);
            }
        }
    }

    public void eliminarProducto() {

        int fila = vista.getTblListaPizza().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Aun no ha seleccionado una fila");
        } else {

            int response = JOptionPane.showConfirmDialog(vista, "¿Seguro que desea eliminar esta información?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {

                int codigo;
                codigo = (Integer) vista.getTblListaPizza().getValueAt(fila, 0);

                if (modelo.eliminarProducto(codigo)) {
                    JOptionPane.showMessageDialog(null, "El producto fue eliminado exitosamente");
//                    cargarProductosTabla();//Actualizo la tabla con los datos
                } else {
                    JOptionPane.showMessageDialog(null, "Error: El producto no se pudo eliminar");
                }
            }
        }

    }

    public void abrirYCargarDatosEnElDialog() {

        int seleccion = vista.getTblListaPizza().getSelectedRow();

        if (seleccion == -1) {
            JOptionPane.showMessageDialog(null, "Aun no ha seleccionado una fila");
        } else {

            int codigo = (Integer) vista.getTblListaPizza().getValueAt(seleccion, 0);
            modelo.listaProductosTabla().forEach((pe) -> {
                if (pe.getCodigo() == codigo) {

                    //Abre el jDialog y carga los datos en el jDialog
                    vista.getjDlgCrearPizza().setName("Editar");
                    vista.getjDlgCrearPizza().setLocationRelativeTo(vista);
                    vista.getjDlgCrearPizza().setSize(546, 486);
                    vista.getjDlgCrearPizza().setTitle("Editar");
                    vista.getjDlgCrearPizza().setVisible(true);
                    vista.getTxtNombre().setText(pe.getNombre());
                    vista.getSpinnerPvp().setValue(pe.getPvp());
                    vista.getSpinnerCosto().setValue(pe.getCosto());
                    vista.getTxtIngredientes().setText(pe.getIngredientes());

                    vista.getLblCargarFoto().setIcon(modelo.ConsultarFotoJDialog(codigo)); //Llamo al metodo 'ConsultarFoto' del modelo
                }
            });
        }
    }

    public void imprimirPizza() {
        ConectPG cpg = new ConectPG(); //Instaciamos la conexion para abrir la conexion con la BD/
        try {
            //Para imprimir el reporte, esdecir que haga una vista previa del reporte, es como una consulta/
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/Examen.jasper"));
             // Para mandar a mostar
                   JasperPrint jp = JasperFillManager.fillReport(jr, null, cpg.getCon());
             //Para hacer una vista previa
            //
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ControladorPizza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarDatos() {

        vista.getTxtNombre().setText("");
        vista.getSpinnerPvp().setValue(0);
        vista.getSpinnerCosto().setValue(0);
        vista.getTxtIngredientes().setText("");
        vista.getLblCargarFoto().setIcon(null);

        vista.getTxtCodigo().setVisible(false);

    }
}
