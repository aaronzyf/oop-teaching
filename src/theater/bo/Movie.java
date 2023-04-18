package theater.bo;

import theater.annotations.TableDataField;

import java.util.ArrayList;

public class Movie {
    @TableDataField(name = "Title",width = 30)
    private String title;
    @TableDataField(name = "Release year",width = 12)
    private int releaseYear;
    @TableDataField(name = "Director",width = 16)
    private String director;
    @TableDataField(name = "Duration",width = 12)
    private int duration;
    private ArrayList<String> genres;

    public Movie(){

    }

    public Movie(String title, String director, int duration,int releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.duration = duration;
        this.genres = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return title;
    }
}

