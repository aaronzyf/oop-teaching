package theater.bo;

import theater.tools.CommandLineUtils;
import theater.tools.StringUtils;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class Theater extends Venue {

    public ArrayList<ShowTime> getShowTimes() {
        return showTimes;
    }

    private final ArrayList<ShowTime> showTimes;

    public ArrayList<Movie> movies;
    public String releaseYear;
    public int genre;

    public Theater(String name, String location) {
        super(name, location);
        this.movies = new ArrayList<>();
        this.showTimes = new ArrayList<>();

    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }
    public void addMovie(String title, String director, int duration,int releaseYear) {
        addMovie(new Movie(title, director, duration,releaseYear));
    }

    public void removeMovie(String title) {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getTitle().equals(title)) {
                movies.remove(i);
                System.out.println("Movie removed successfully!");
                return;
            }
        }
        System.out.println("Movie not found!");
    }

    public void viewMovie(Movie movie) {

        if(!isNull(movie)){
            System.out.println(StringUtils.padString("Title",20)+"\t:\t" + movie.getTitle());
            System.out.println(StringUtils.padString("Director",20)+"\t:\t" + movie.getTitle());
            System.out.println(StringUtils.padString("Release Year",20)+"\t:\t" + movie.getReleaseYear());
            System.out.println(StringUtils.padString("Review",20)+"\t:\t");
            System.out.println(StringUtils.padString("Genre",20)+"\t:\t");
            System.out.println(StringUtils.padString("Duration",20)+"\t:\t" + movie.getDuration());
            System.out.println(StringUtils.padString("Rating",20)+"\t:\t");
            System.out.println(StringUtils.padString("Number of Ratings",20)+"\t:\t" );

        }else{
            System.out.println("movie not found");
        }
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void viewAllMovies() {
        CommandLineUtils.tableData(movies, Movie.class);
    }

    public void addShowtime(ShowTime showtime) {
        showTimes.add(showtime);
    }

    public void removeShowtime(ShowTime showtime) {
        showTimes.remove(showtime);
    }

    @Override
    public String toString() {
        return "Theater{" +
                "showTimes=" + showTimes +
                ", movies=" + movies +
                ", releaseYear='" + releaseYear + '\'' +
                ", genre=" + genre +
                '}';
    }

    public void viewShowtime(ShowTime showTime) {

            if (!isNull(showTime) && !isNull(showTime.getMovie())) {
                System.out.println(StringUtils.padString("Title",20)+"\t:\t " + showTime.getMovie().getTitle());
                System.out.println(StringUtils.padString("Date and Time",20)+"\t:\t " + showTime.getDateTime());
                System.out.println(StringUtils.padString("Price",20)+"\t:\t " + showTime.getPrice());
            }else {
                CommandLineUtils.errorMessage("cannot get the movie info of this show time  , maybe it's be deleted");
            }


    }

    public void viewAllShowtimes() {
        for (ShowTime showtime : showTimes) {
            System.out.println(showtime.getMovie().getTitle() + " - " + showtime.getDateTime());
        }
    }

    public void buyTicket(Seat seat) {
//        for (ShowTime showtime : showTimes) {
//            if (showtime.getMovie().getTitle().equals(title) && showtime.getDateTime().equals(date)) {
//                Seat seat = seats[row][column];
//                if (seat.isAvailable()) {
//                    seat.setAvailable(false);
//                    System.out.println("Ticket purchased successfully!");
//                    System.out.println("Movie: " + title);
//                    System.out.println("Date and Time: " + date);
//                    System.out.println("Seat: Row " + row + ", Column " + column);
//                    System.out.println("Price: $" + showtime.getPrice());
//                    return;
//                } else {
//                    System.out.println("Sorry, that seat is already taken.");
//                    return;
//                }
//            }
//        }
//        System.out.println("Sorry, there is no showtime for that movie on that date and time.");
    }


}
