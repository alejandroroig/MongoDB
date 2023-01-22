package org.example.entities;

import java.util.List;

public class Pelicula {
    String plot;
    List<String> genres;
    String title;
    public String getPlot() {
        return plot;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }
    public List<String> getGenres() {
        return genres;
    }
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return "Película [\n  argumento=" + plot + ",\n  géneros=" + genres + ",\n  título=" + title + "\n]";
    }
}
