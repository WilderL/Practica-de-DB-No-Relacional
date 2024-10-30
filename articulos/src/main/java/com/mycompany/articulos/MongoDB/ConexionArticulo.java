
package com.mycompany.articulos.MongoDB;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WilderL
 */
public class ConexionArticulo {
    private static final Logger LOGGER = Logger.getLogger(ConexionArticulo.class.getName());
    private final MongoCollection<Document> collection;

    public ConexionArticulo() {
        this.collection = Conexion.obtenerInstancia().getDatabase().getCollection("Articulo");
    }

    public void insertarArticulo(Document articulo) {
        try {
            collection.insertOne(articulo);
            LOGGER.log(Level.INFO, "Artículo insertado: {0}", articulo.toJson());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al insertar artículo: {0}", e.getMessage());
        }
    }

    public void eliminarArticulo(String id) {
        try {
            Bson filtro = Filters.eq("_id", new org.bson.types.ObjectId(id));
            collection.deleteOne(filtro);
            LOGGER.log(Level.INFO, "Artículo eliminado con _id: {0}", id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar artículo: {0}", e.getMessage());
        }
    }

    public void actualizarArticulo(String id, Bson actualizacion) {
        try {
            Bson filtro = Filters.eq("_id", new org.bson.types.ObjectId(id));
            collection.updateOne(filtro, actualizacion);
            LOGGER.log(Level.INFO, "Artículo actualizado con _id: {0}", id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar artículo: {0}", e.getMessage());
        }
    }

    public Document buscarArticulo(String id) {
        try {
            Bson filtro = Filters.eq("_id", new org.bson.types.ObjectId(id));
            Document articulo = collection.find(filtro).first();
            if (articulo != null) {
                LOGGER.log(Level.INFO, "Artículo encontrado: {0}", articulo.toJson());
            }
            return articulo;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar artículo: {0}", e.getMessage());
            return null;
        }
    }
    
    public List<Document> mostrarTodos() {
        List<Document> listaArticulos = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                listaArticulos.add(doc);
            }
            LOGGER.log(Level.INFO, "Se recuperaron todos los documentos de la colección Articulo");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al recuperar documentos de Articulo: {0}", e.getMessage());
        }
        return listaArticulos;
    }
}

