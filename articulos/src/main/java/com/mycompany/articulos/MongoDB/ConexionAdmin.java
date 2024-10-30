
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
public class ConexionAdmin {
    private static final Logger LOGGER = Logger.getLogger(ConexionAdmin.class.getName());
    private final MongoCollection<Document> collection;

    public ConexionAdmin() {
        this.collection = Conexion.obtenerInstancia().getDatabase().getCollection("admin");
    }

    public void insertarAdmin(Document admin) {
        try {
            collection.insertOne(admin);
            LOGGER.log(Level.INFO, "Admin insertado: {0}", admin.toJson());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al insertar admin: {0}", e.getMessage());
        }
    }

    public void eliminarAdmin(String id) {
        try {
            Bson filtro = Filters.eq("_id", new org.bson.types.ObjectId(id));
            collection.deleteOne(filtro);
            LOGGER.log(Level.INFO, "Admin eliminado con _id: {0}", id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar admin: {0}", e.getMessage());
        }
    }

    public void actualizarAdmin(String id, Bson actualizacion) {
        try {
            Bson filtro = Filters.eq("_id", new org.bson.types.ObjectId(id));
            collection.updateOne(filtro, actualizacion);
            LOGGER.log(Level.INFO, "Admin actualizado con _id: {0}", id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar admin: {0}", e.getMessage());
        }
    }

    public Document buscarAdmin(String id) {
        try {
            Bson filtro = Filters.eq("_id", new org.bson.types.ObjectId(id));
            Document admin = collection.find(filtro).first();
            if (admin != null) {
                LOGGER.log(Level.INFO, "Admin encontrado: {0}", admin.toJson());
            }
            return admin;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar admin: {0}", e.getMessage());
            return null;
        }
    }
    
    public List<Document> mostrarTodos() {
        List<Document> listaAdmins = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                listaAdmins.add(doc);
            }
            LOGGER.log(Level.INFO, "Se recuperaron todos los documentos de la colección admin");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al recuperar documentos de admin: {0}", e.getMessage());
        }
        return listaAdmins;
    }
}