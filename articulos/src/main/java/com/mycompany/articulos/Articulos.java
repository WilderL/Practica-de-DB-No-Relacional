
package com.mycompany.articulos;

import com.mycompany.articulos.MongoDB.Conexion;
import com.mycompany.articulos.UI.Main;
/**
 *
 * @author WilderL, EAGutierrez04 
 */
public class Articulos {

    public static void main(String[] args) {
        // Establece la conexión única a MongoDB
        Conexion.obtenerInstancia().conectar();
        
        Main window = new Main();
        window.setVisible(true);
        // Cierra la conexión al final
    }
}
