package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.example.entities.Movie;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String uri = "mongodb+srv://miusuario:mipassword@miclusterad.619nifa.mongodb.net/?retryWrites=true&w=majority";

        // Paso 1: Query a base de datos
        // Por defecto, intentará conectar al puerto 27017
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Seleccionamos la base de datos para trabajar
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            // Recogemos la colección "movies" en una colección de documentos de MongoDB
            MongoCollection<Document> collection = database.getCollection("movies");
            System.out.println("La colección movies tiene " + collection.countDocuments() + " documentos");
            Document doc = collection.find(eq("title", "Back to the Future")).first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("No matching documents found.");
            }
        }

        // Paso 2: Uso de CodecRegistry para mapear clases POJO a Documentos
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Movie> collection = database.getCollection("movies", Movie.class);
            Movie movie = collection.find(eq("title", "Back to the Future")).first();
            System.out.println(movie);
        }
    }
}
