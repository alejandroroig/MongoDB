package org.example.entities;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class Usuario {
    private ObjectId id;
    private String nombre;
    private String password;
    private List<Direccion> direcciones;
    private List<Mail> emails;

}
