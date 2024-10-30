
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
public class ConexionEstudiante {
    private static final Logger LOGGER = Logger.getLogger(ConexionEstudiante.class.getName());
    private final MongoCollection<Document> collection;

    public ConexionEstudiante() {
        this.collection = Conexion.obtenerInstancia().getDatabase().getCollection("Estudiante");
    }

    public void insertarEstudiante(Document estudiante) {
        try {
            collection.insertOne(estudiante);
            LOGGER.log(Level.INFO, "Estudiante insertado: {0}", estudiante.toJson());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al insertar estudiante: {0}", e.getMessage());
        }
    }

    public void eliminarEstudiante(String id) {
        try {
            Bson filtro = Filters.eq("_id", new org.bson.types.ObjectId(id));
            collection.deleteOne(filtro);
            LOGGER.log(Level.INFO, "Estudiante eliminado con _id: {0}", id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar estudiante: {0}", e.getMessage());
        }
    }

    public void actualizarEstudiante(String id, Bson actualizacion) {
        try {
            Bson filtro = Filters.eq("_id", new org.bson.types.ObjectId(id));
            collection.updateOne(filtro, actualizacion);
            LOGGER.log(Level.INFO, "Estudiante actualizado con _id: {0}", id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar estudiante: {0}", e.getMessage());
        }
    }

    public Document buscarEstudiante(String id) {
        try {
            Bson filtro = Filters.eq("_id", new org.bson.types.ObjectId(id));
            Document estudiante = collection.find(filtro).first();
            if (estudiante != null) {
                LOGGER.log(Level.INFO, "Estudiante encontrado: {0}", estudiante.toJson());
            }
            return estudiante;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar estudiante: {0}", e.getMessage());
            return null;
        }
    }
    
    public List<Document> mostrarTodos() {
        List<Document> listaEstudiantes = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                listaEstudiantes.add(doc);
            }
            LOGGER.log(Level.INFO, "Se recuperaron todos los documentos de la colección Estudiante");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al recuperar documentos de Estudiante: {0}", e.getMessage());
        }
        return listaEstudiantes;
    }
}
