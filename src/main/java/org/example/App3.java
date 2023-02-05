package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.example.entities.*;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class App3 {

    public static void main(String[] args) {

        String uri = "mongodb://ec2-34-224-83-192.compute-1.amazonaws.com:27017";

        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        try(MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("prueba").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Usuario> collection = database.getCollection("prueba", Usuario.class);

            Usuario u = new Usuario();
            u.setNombre("Alejandro");
            u.setPassword("1234");

            Direccion d1 = new Direccion();
            d1.setId(new ObjectId());
            d1.setCalle("Calle Mayor");
            d1.setNumero(10);

            Direccion d2 = new Direccion();
            d2.setId(new ObjectId());
            d2.setCalle("Avenida Valencia");
            d2.setNumero(20);

            List<Direccion> direcciones = new ArrayList<>();
            direcciones.add(d1);
            direcciones.add(d2);
            u.setDirecciones(direcciones);

            Mail m1 = new Mail();
            m1.setEmail("alejandro@prueba.com");

            Mail m2 = new Mail();
            m2.setEmail("alejandro@otraprueba.com");

            List<Mail> mails = new ArrayList<>();
            mails.add(m1);
            mails.add(m2);
            u.setEmails(mails);

            collection.insertOne(u);

            Usuario copiaU = collection.find().first();
            System.out.println("\nUsuario:");
            for(Usuario cursorUsuario : collection.find()) {
                System.out.println(cursorUsuario);
            }
        }
    }
}
