package org.example.entities;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Mail {
    private ObjectId id;
    private String email;

    public Mail() {
        id = new ObjectId();
    }
}
