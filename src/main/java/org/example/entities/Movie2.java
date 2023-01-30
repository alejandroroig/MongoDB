package org.example.entities;

import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class Movie2 {
    ObjectId id;
    String title;
    @ToString.Exclude
    String plot;
    List<String> genres;
    int runtime;
    String rated;
    int year;
    List<String> directors;
    List<String> cast;
    String type;


}
