/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModeloPizza;
import Modelo.Pizza;
import Vista.VistaPizzeria;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
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
//        buscarPersona();//Llama al metodo de "buscarPersona"
    }
    
//    public void cargarProductosTabla() {
//        vista.getTblListaPizza().setDefaultRenderer(Object.class, new ImagenTabla());//La manera de renderizar la tabla.
//        vista.getTblListaPizza().setRowHeight(100);
//
//        //Enlazar el modelo de tabla con mi controlador.
//        DefaultTableModel tblModel;
//        List<Pizza> listap = modelo.listaProductosTabla();//Enlazo al Modelo y obtengo los datos
//        Holder<Integer> i = new Holder<>(0);//Contador para las filas. 'i' funciona dentro de una expresion lambda
//
//        listap.stream().forEach(pe -> {
//            
//            tblModel.addRow(new Object[9]);//Creo una fila vacia
//            vista.getTblListaPizza().setValueAt(pe.getIdProducto(), i.value, 0);
//            vista.getTblListaPizza().setValueAt(pe.getNombre(), i.value, 1);
//            vista.getTblListaPizza().setValueAt(pe.getPrecio(), i.value, 2);
//            vista.getTblListaPizza().setValueAt(pe.getCantidad(), i.value, 3);
//            vista.getTblListaPizza().setValueAt(pe.getDescripcion(), i.value, 4);
//            
//            Image foto = pe.getImagen();
//            if (foto != null) {
//                
//                Image nimg = foto.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
//                ImageIcon icono = new ImageIcon(nimg);
//                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
//                renderer.setIcon(icono);
//                vista.getTbPersona().setValueAt(new JLabel(icono), i.value, 5);
//                
//            } else {
//                vista.getTbPersona().setValueAt(null, i.value, 5);
//            }
//            
//            i.value++;
//        });
//    }
//    
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
                    
//                    vista.getTxtIdentificacion().setText(String.valueOf(pe.getIdProducto()));
                    vista.getTxtNombre().setText(pe.getNombre());
                    vista.getSpinnerPvp().setValue(pe.getPvp());
                    vista.getSpinnerCosto().setValue(pe.getCosto());
                    vista.getTxtIngredientes().setText(pe.getIngredientes());
                    
                    vista.getLblCargarFoto().setIcon(modelo.ConsultarFotoJDialog(codigo)); //Llamo al metodo 'ConsultarFoto' del modelo
                }
            });
        }
    }
    
//    public void buscarPersona() {
//        
//        KeyListener eventoTeclado = new KeyListener() {//Crear un objeto de tipo keyListener(Es una interface) por lo tanto se debe implementar sus metodos abstractos
//
//            @Override
//            public void keyTyped(KeyEvent e) {
//                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//            
//            @Override
//            public void keyPressed(KeyEvent e) {
//                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//            
//            @Override
//            public void keyReleased(KeyEvent e) {
//                
//                vista.getTbProducto().setDefaultRenderer(Object.class, new ImagenTabla());//La manera de renderizar la tabla.
//                vista.getTbProducto().setRowHeight(100);
//
//                //Enlazar el modelo de tabla con mi controlador.
//                DefaultTableModel tblModel;
//                tblModel = (DefaultTableModel) vista.getTbPersona().getModel();
//                tblModel.setNumRows(0);//limpio filas de la tabla.
//
//                List<Producto> listap = modelo.buscarProducto(vista.getTxtBuscar().getText());//Enlazo al Modelo y obtengo los datos
//                Holder<Integer> i = new Holder<>(0);//Contador para las filas. 'i' funciona dentro de una expresion lambda
//
//                listap.stream().forEach(pe -> {
//                    
//                    tblModel.addRow(new Object[9]);//Creo una fila vacia
//                    vista.getTbProducto().setValueAt(pe.getIdProducto(), i.value, 0);
//                    vista.getTbProducto().setValueAt(pe.getNombre(), i.value, 1);
//                    vista.getTbProducto().setValueAt(pe.getPrecio(), i.value, 2);
//                    vista.getTbProducto().setValueAt(pe.getCantidad(), i.value, 3);
//                    vista.getTbProducto().setValueAt(pe.getDescripcion(), i.value, 4);
//                    
//                    Image foto = pe.getImagen();
//                    if (foto != null) {
//                        
//                        Image nimg = foto.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
//                        ImageIcon icono = new ImageIcon(nimg);
//                        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
//                        renderer.setIcon(icono);
//                        vista.getTbPersona().setValueAt(new JLabel(icono), i.value, 5);
//                        
//                    } else {
//                        vista.getTbPersona().setValueAt(null, i.value, 5);
//                    }
//                    
//                    i.value++;
//                });
//            }
//        };
//        
//        vista.getTxtBuscar().addKeyListener(eventoTeclado); //"addKeyListener" es un metodo que se le tiene que pasar como argumento un objeto de tipo keyListener 
//    }
    
    public void limpiarDatos() {
//        vista.getTxtIdentificacion().setText("");
        vista.getTxtNombre().setText("");
        vista.getSpinnerPvp().setValue(0);
        vista.getSpinnerCosto().setValue(0);
        vista.getTxtIngredientes().setText("");
        vista.getLblCargarFoto().setIcon(null);
        
        vista.getTxtCodigo().setVisible(false);
        
    }
}
