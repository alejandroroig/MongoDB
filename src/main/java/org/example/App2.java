package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.example.entities.Movie2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class App2
{
    public static void main( String[] args )
    {
        String uri = "mongodb+srv://miusuario:mipassword@miclusterad.619nifa.mongodb.net/?retryWrites=true&w=majority";

        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Movie2> collection = database.getCollection("movies", Movie2.class);

            // Creación
            Movie2 peli1 = new Movie2();
            peli1.setTitle("The Favourite");
            peli1.setGenres(new ArrayList<>(Arrays.asList("Drama", "History")));
            peli1.setRuntime(121);
            peli1.setRated("R");
            peli1.setYear(2018);
            peli1.setDirectors(new ArrayList<>(List.of("Yorgos Lanthimos")));
            peli1.setCast(new ArrayList<>(Arrays.asList("Olivia Colman", "Emma Stone", "Rachel Weisz")));
            peli1.setType("movie");

            // InsertOne
            collection.insertOne(peli1);

            Movie2 peli2 = new Movie2();
            peli2.setTitle("Jurassic World: Fallen Kingdom");
            peli2.setGenres(new ArrayList<>(Arrays.asList("Action", "Sci-Fi")));
            peli2.setRuntime(130);
            peli2.setRated("PG-13");
            peli2.setYear(2018);
            peli2.setDirectors(new ArrayList<>(List.of("J. A. Bayona")));
            peli2.setCast(new ArrayList<>(Arrays.asList("Chris Pratt", "Bryce Dallas Howard", "Rafe Spall")));
            peli2.setType("movie");

            Movie2 peli3 = new Movie2();
            peli3.setTitle("Tag");
            peli3.setGenres(new ArrayList<>(Arrays.asList("Comedy", "Action")));
            peli3.setRuntime(105);
            peli3.setRated("R");
            peli3.setYear(2018);
            peli3.setDirectors(new ArrayList<>(List.of("Jeff Tomsic")));
            peli3.setCast(new ArrayList<>(Arrays.asList("Annabelle Wallis", "Jeremy Renner", "Jon Hamm")));
            peli3.setType("movie");

            // InsertMany
            collection.insertMany(new ArrayList<>(Arrays.asList(peli2, peli3)));

            // Read
            System.out.println("\nPelis insertadas del año 2018");
            for(Movie2 cursorPeli : collection.find(eq("year", 2018))) {
                System.out.println(cursorPeli);
            }

            System.out.println("\nPelis de comedia y acción de 2015 o posteriores");
            // for(Movie2 cursorPeli : collection.find(and(and(in("genres", "Comedy"), in("genres", "Action")), gte("year", 2015))).sort(orden).limit(5)) {

            Bson filtroGeneros = and(in("genres", "Comedy"), in("genres", "Action"));
            Bson filtroAnyo = gte("year", 2015);
            Bson orden = Sorts.descending("runtime");

            for(Movie2 cursorPeli : collection.find(and(filtroGeneros, filtroAnyo)).sort(orden).limit(5)) {
                System.out.println(cursorPeli);
            }

            // Update
            collection.updateOne(eq("title", "Tag"),
                    Updates.set("plot", "One month every year, five highly competitive friends hit the ground running for a no-holds-barred game of tag"));

            // Read
            System.out.println("\nTag modificada");
            for(Movie2 cursorPeli : collection.find(eq("title", "Tag"))) {
                System.out.println(cursorPeli);
            }

            // Delete
            collection.deleteMany(gte("year", 2018));

            System.out.println("\nEliminadas pelis");
            for(Movie2 cursorPeli : collection.find(eq("year", 2018))) {
                System.out.println(cursorPeli);
            }
        }
    }
}
