package info.johtani.sample.es.client.search.ltr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import info.johtani.sample.es.client.Logger;

import java.util.List;

// for Learn to Rank demo
@JsonIgnoreProperties(ignoreUnknown=true)
public class TMDBDocument {

    public void printDoc() {
        Logger.log(" id: ["+this.id+"]");
        Logger.log(" title: ["+this.title+"]");
    }

    private String id;
    private String title;
    private String overview;
    private List<String> directors;
    private String cast;
    private List<String> genres;
    private String release_date;
    private String poster_path;

    public TMDBDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
