/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

/**
 *
 * @author Usuario
 */
public class ModeloPizza extends Pizza {

    ConectPG conpg = new ConectPG();

    public ModeloPizza() {
    }

    public ModeloPizza(int codigo, String nombre, String tamanio, String ingredientes, double costo, double pvp, FileInputStream foto, int longitud, Image imagen) {
        super(codigo, nombre, tamanio, ingredientes, costo, pvp, foto, longitud, imagen);
    }

    public boolean crearProductoSinFoto() {

        String sql = "INSERT INTO pizza(nombre, tamanio, ingredientes, costo,pvp, foto) VALUES ('" + getNombre() + "', " + getTamanio() + ", '" + getIngredientes() + "', " + getCosto() + "," + getPvp() + ",'null');";

        return conpg.accion(sql);
    }

    public boolean crearPizzaFoto() {
        try {
            String sql;

            sql = "INSERT INTO pizza ()";
            sql += "VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = conpg.getCon().prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setString(2, getIngredientes());
            ps.setDouble(3, getCosto());
            ps.setDouble(4, getPvp());
            ps.setBinaryStream(5, getFoto(), getLongitud());

            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPizza.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean modificarProductoSinFoto() { //modificar la instancia en la BD

        String sql = "UPDATE pizza SET nombre='" + getNombre() + "', pvp=" + getPvp() + ",  cosot=" + getCosto()+ ", ingredientes='" + getIngredientes() + "'";

        return conpg.accion(sql);
    }

    public boolean modificarPizzaFoto() {
        try {
            String sql;

            sql = "UPDATE producto SET nombre=?,tamanio=?,ingredientes=?,costo=?,pvp=?,foto=? Where idpizza=?";
            PreparedStatement ps = conpg.getCon().prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setString(2, getIngredientes());
            ps.setDouble(3, getCosto());
            ps.setDouble(4, getPvp());
            ps.setBinaryStream(5, getFoto(), getLongitud());
            ps.setInt(6, getCodigo());

            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPizza.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean eliminarProducto(int codigo) {

        String sql = "DELETE FROM producto WHERE idpizza = " + codigo + ";";

        return conpg.accion(sql);
    }

    public List<Pizza> buscarProducto(String nombre) {
        try {
            //Me retorna un "List" de "persona"
            List<Pizza> lista = new ArrayList<>();

            String sql = "Select * from pizza where nombre like '" + nombre + "%'";

            //ConectPG conpg = new ConectPG();
            ResultSet rs = conpg.consulta(sql); //La consulta nos devuelve un "ResultSet"
            byte[] bytea;

            //Pasar de "ResultSet" a "List"
            while (rs.next()) {
                //Crear las instancias de las personas
                Pizza producto = new Pizza();

                //Todo lo que haga en la sentencia define como voy a extraer los datos
                producto.setCodigo(rs.getInt("idpizza"));
                producto.setNombre(rs.getString("nombre"));
                producto.setIngredientes(rs.getString("ingredientes"));
                producto.setCosto(rs.getDouble("costo"));
                producto.setPvp(rs.getDouble("pvp"));
                bytea = rs.getBytes("foto");

                if (bytea != null) {

                    try {
                        producto.setImagen(obtenerImagen(bytea));
                    } catch (IOException ex) {
                        Logger.getLogger(ModeloPizza.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                lista.add(producto); //Agrego los datos a la lista
            }

            //Cierro la conexion a la BD
            rs.close();
            //Retorno la lista
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(ModeloPizza.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Pizza> listaProductosTabla() {
        try {
            //Me retorna un "List" de "persona"
            List<Pizza> lista = new ArrayList<>();

            String sql = "select * from pizza";

            ResultSet rs = conpg.consulta(sql); //La consulta nos devuelve un "ResultSet"
            byte[] bytea;

            //Pasar de "ResultSet" a "List"
            while (rs.next()) {
                //Crear las instancias de las personas
                Pizza producto = new Pizza();

                //Todo lo que haga en la sentencia define como voy a extraer los datos
                producto.setCodigo(rs.getInt("idpizza"));
                producto.setNombre(rs.getString("nombre"));
                producto.setTamanio(rs.getString("tamanio"));
                producto.setIngredientes(rs.getString("ingredientes"));
                producto.setPvp(rs.getDouble("pvp"));
                producto.setCosto(rs.getDouble("costo"));
                bytea = rs.getBytes("foto");

                if (bytea != null) {

                    try {
                        producto.setImagen(obtenerImagen(bytea));
                    } catch (IOException ex) {
                        Logger.getLogger(ModeloPizza.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                lista.add(producto); //Agrego los datos a la lista
            }

            //Cierro la conexion a la BD
            rs.close();
            //Retorno la lista
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(ModeloPizza.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

//    public List<Pizza> listaProductoJDialog() {
//        try {
//            //Me retorna un "List" de "persona"
//            List<Pizza> lista = new ArrayList<>();
//
//            String sql = "select idproducto,nombre, precio, cantidad, descripcion from producto";
//
//            ResultSet rs = conpg.consulta(sql); //La consulta nos devuelve un "ResultSet"
//
//            //Pasar de "ResultSet" a "List"
//            while (rs.next()) {
//                //Crear las instancias de las personas
//                Producto producto = new Producto();
//
//                //Todo lo que haga en la sentencia define como voy a extraer los datos
//                producto.setIdProducto(rs.getInt("idproducto"));
//                producto.setNombre(rs.getString("nombre"));
//                producto.setPrecio(rs.getDouble("precio"));
//                producto.setCantidad(rs.getInt("cantidad"));
//                producto.setDescripcion(rs.getString("descripcion"));
//
//                lista.add(producto); //Agrego los datos a la lista
//            }
//
//            //Cierro la conexion a la BD
//            rs.close();
//            //Retorno la lista
//            return lista;
//
//        } catch (SQLException ex) {
//            Logger.getLogger(ModeloProducto.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
    private Image obtenerImagen(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator it = ImageIO.getImageReadersByFormatName("png");
        ImageReader reader = (ImageReader) it.next();
        Object source = bis;
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        param.setSourceSubsampling(1, 1, 0, 0);
        return reader.read(0, param);
    }

    public ImageIcon ConsultarFotoJDialog(int codigo) {
        conpg.getCon();
        String sql = "select foto from \"pizza\" where idproducto = " + codigo + ";";
        ImageIcon foto;
        InputStream is;

        try {
            ResultSet rs = conpg.consulta(sql);
            while (rs.next()) {

                is = rs.getBinaryStream(1);

                BufferedImage bi = ImageIO.read(is);
                foto = new ImageIcon(bi);

                Image img = foto.getImage();
                Image newimg = img.getScaledInstance(118, 139, java.awt.Image.SCALE_SMOOTH);

                ImageIcon newicon = new ImageIcon(newimg);

                return newicon;
            }
        } catch (Exception ex) {

            return null;
        }

        return null;
    }
}
