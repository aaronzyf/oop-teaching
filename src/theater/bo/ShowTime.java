package theater.bo;

import theater.annotations.TableDataField;
import theater.enums.AnsiColor;
import theater.tools.CommandLineUtils;
import theater.tools.StringUtils;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class ShowTime {
    @Override
    public String toString() {
        return "ShowTime{" +
                "movie=" + movie +
                ", date=" + date +
                ", price=" + price +
                '}';
    }

    @TableDataField(name = "movie",width = 20)
    private Movie movie;
    @TableDataField(name = "Show time",width = 30)
    private Date date;

    public Seat[][] getSeats() {
        return seats;
    }

    private  Seat[][] seats;

    private void initializeSeats() {
        this.seats = new Seat[10][10];

        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[0].length; j++) {
                seats[i][j] = new Seat(i + 1, j + 1, true); // initialize all seats to be available
            }
        }
    }

    public void printSeats() {
        AtomicInteger index = new AtomicInteger();
        System.out.println();
        System.out.print("seat status example : ");
        CommandLineUtils.setPrintColor(AnsiColor.BRIGHT_GREEN);
        System.out.print("□ = available");
        CommandLineUtils.resetColor();
        System.out.print("\t,\t");
        CommandLineUtils.setPrintColor(AnsiColor.BRIGHT_RED);
         System.out.println("■ = sold");
        System.out.println();
        for (int i = 0; i < seats.length; i++) {
            System.out.print("\t");
            for (int j = 0; j < seats[0].length; j++) {
                CommandLineUtils.setPrintColor(seats[i][j].isAvailable() ? AnsiColor.BRIGHT_GREEN : AnsiColor.BRIGHT_RED);
                System.out.print("□");
                System.out.print( StringUtils.padString(String.valueOf(index.incrementAndGet()),6) +"\t");
            }
            System.out.println();
            CommandLineUtils.resetColor();
        }

    }

    public Date getDate() {
        return date;
    }

    @TableDataField(name = "Price",width = 12)
    private float price;

    public ShowTime(){
        initializeSeats();
    }
    public ShowTime(Movie movie, Date date, float price) {
        initializeSeats();
        this.movie = movie;
        this.date = date;
        this.price = price;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Date getDateTime() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}