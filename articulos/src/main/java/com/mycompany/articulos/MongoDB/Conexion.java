package com.mycompany.articulos.MongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "hoja10";
    private static final Logger LOGGER = Logger.getLogger(Conexion.class.getName());
    
    private static Conexion instancia;
    private MongoClient mongoClient;
    private MongoDatabase database;

    // Constructor privado para patrón Singleton
    private Conexion() { }
    
    // Método para obtener la instancia única de la conexión
    public static Conexion obtenerInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
    
    public void conectar() {
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create(CONNECTION_STRING);
                database = mongoClient.getDatabase(DATABASE_NAME);
                LOGGER.log(Level.INFO, "Conexión a MongoDB establecida correctamente.");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al conectar a MongoDB: {0}", e.getMessage());
                throw new RuntimeException("No se pudo conectar a MongoDB", e);
            }
        } else {
            LOGGER.log(Level.WARNING, "La conexión a MongoDB ya está establecida.");
        }
    }
    
    public MongoDatabase getDatabase() {
        if (database == null) {
            throw new IllegalStateException("Debe conectarse a la base de datos primero llamando al método conectar().");
        }
        return database;
    }
    
    public void cerrar() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            LOGGER.log(Level.INFO, "Conexión a MongoDB cerrada.");
        } else {
            LOGGER.log(Level.WARNING, "No hay conexión abierta que cerrar.");
        }
    }
}
