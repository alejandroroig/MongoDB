package org.example.entities;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Direccion {
    private ObjectId id;
    private String calle;
    private int numero;
}
