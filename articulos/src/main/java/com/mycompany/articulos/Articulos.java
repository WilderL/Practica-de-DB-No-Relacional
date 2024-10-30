
package com.mycompany.articulos;

import com.mycompany.articulos.MongoDB.Conexion;

/**
 *
 * @author WilderL, EAGutierrez04 
 */
public class Articulos {

    public static void main(String[] args) {
        // Establece la conexión única a MongoDB
        Conexion.obtenerInstancia().conectar();
        
        // Cierra la conexión al final
        Conexion.obtenerInstancia().cerrar();
    }
}
