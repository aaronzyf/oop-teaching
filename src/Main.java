import theater.bo.Menu;
import theater.bo.Movie;
import theater.bo.Seat;
import theater.bo.ShowTime;
import theater.bo.Theater;
import theater.enums.AnsiColor;
import theater.tools.CommandLineUtils;
import theater.tools.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Main {


    private static final List<Menu> primaryMenu;
    static boolean menuLoop = true;

    static Theater theater;

    static {
        theater = new Theater("LUT Kino", "Yliopistonkatu");

        Movie tempMovie = new Movie();
        tempMovie.setTitle("mulan");
        tempMovie.setDuration(120);
        tempMovie.setReleaseYear(2022);
        tempMovie.setDirector("aaronzyf");

        theater.addMovie(tempMovie);


        ShowTime tempShowTime = new ShowTime();
        tempShowTime.setDate(new Date());
        tempShowTime.setPrice(20F);
        tempShowTime.setMovie(tempMovie);
        theater.addShowtime(tempShowTime);

        primaryMenu = new ArrayList<>();
        primaryMenu.add(new Menu("Add a movie", () -> {
            Movie movie = (Movie) CommandLineUtils.createObject(Movie.class);
            theater.addMovie(movie);
            CommandLineUtils.successMessage("Movie added successfully!");
            CommandLineUtils.pressAnyKeyToContinue();
        }));
        primaryMenu.add(new Menu("Remove a movie", () -> {
            //先列出来所有的电影 , 然后输入编号删除
            if (theater.movies.size() > 0) {
                List<Menu> moviesMenu =
                        theater.movies.stream()
                                      .map((Movie movie) -> new Menu(
                                              movie.getTitle(),
                                              () -> CommandLineUtils.deleteConfirm("Are you confirm to delete movie `" + movie.getTitle() + "`",
                                                      () -> {
                                                          theater.getMovies().remove(movie);
                                                          CommandLineUtils.setPrintColor(AnsiColor.GREEN);
                                                          CommandLineUtils.successMessage("Delete movie `" + movie.getTitle() + "` successfully!");
                                                          CommandLineUtils.resetColor();
                                                          CommandLineUtils.pressAnyKeyToContinue();
                                                      }))
                                      )
                                      .collect(Collectors.toList());
                CommandLineUtils.menu(moviesMenu);
            } else {
                CommandLineUtils.errorMessage("there are no movie in movies in theater `" + theater.getName() + "'");
                CommandLineUtils.pressAnyKeyToContinue();
            }


        }));
        primaryMenu.add(new Menu("View a movie", () -> {
            if (theater.movies.size() > 0) {
                CommandLineUtils.menu(theater.movies.stream()
                                                    .map((Movie movie) -> new Menu(
                                                            movie.getTitle(),
                                                            () -> {
                                                                System.out.println("Details of movie `" + movie.getTitle() + "`");
                                                                theater.viewMovie(movie);
                                                            })
                                                    )
                                                    .collect(Collectors.toList()));
            } else {
                CommandLineUtils.errorMessage("there are no movie in movies in theater `" + theater.getName() + "'");
            }

            CommandLineUtils.pressAnyKeyToContinue();
        }));
        primaryMenu.add(new Menu("View all movies", () -> {
            CommandLineUtils.successMessage("There are " + (theater.movies.size() == 0 ? "no" : theater.movies.size()) + " movies in theater `" + theater.getName() + "'");
            theater.viewAllMovies();
            CommandLineUtils.pressAnyKeyToContinue();
        }));
        primaryMenu.add(new Menu("Add a showtime", () -> {
            if (theater.movies.size() > 0) {
                CommandLineUtils.successMessage("Please select a movie to add show time!");
                CommandLineUtils.menu(theater.movies.stream()
                                                    .map((Movie movie) -> new Menu(
                                                            movie.getTitle(),
                                                            () -> {
                                                                System.out.println("Add show time  to movie `" + movie.getTitle() + "`");
                                                                ShowTime showtime = (ShowTime) CommandLineUtils.createObject(ShowTime.class);
                                                                showtime.setMovie(movie);
                                                                theater.addShowtime(showtime);
                                                                CommandLineUtils.successMessage("Showtime added successfully!");
                                                            })
                                                    )
                                                    .collect(Collectors.toList()));
            } else {
                CommandLineUtils.errorMessage("there are no movie in movies in theater `" + theater.getName() + "' , you should add movie first");
            }

            CommandLineUtils.pressAnyKeyToContinue();
        }));
        primaryMenu.add(new Menu("Remove a showtime", () -> {
            CommandLineUtils.menu(theater.getShowTimes().stream()
                                         .map((ShowTime showTime) -> new Menu(
                                                 showTime.getMovie().getTitle() + "[" + showTime.getDateTime().toLocaleString() + "]",
                                                 () -> {
                                                     CommandLineUtils.deleteConfirm(
                                                             "Do you confirm to delete show time at " + showTime.getDateTime().toString() + " of movie " + showTime.getMovie().getTitle(),
                                                             () -> theater.removeShowtime(showTime)
                                                     );
                                                     CommandLineUtils.successMessage("Showtime removed successfully!");
                                                     CommandLineUtils.pressAnyKeyToContinue();
                                                 })
                                         )
                                         .collect(Collectors.toList()));
        }));
        primaryMenu.add(new Menu("View a showtime", () -> {
            CommandLineUtils.menu(theater.getShowTimes().stream()
                                         .map((ShowTime showTime) -> new Menu(
                                                 showTime.getMovie().getTitle() + "[" + showTime.getDateTime().toLocaleString() + "]",
                                                 () -> {
                                                     System.out.println(showTime);
                                                     theater.viewShowtime(showTime);
                                                     CommandLineUtils.pressAnyKeyToContinue();
                                                 })
                                         )
                                         .collect(Collectors.toList()));
        }));
        primaryMenu.add(new Menu("View all showtime", () -> {
            System.out.println();
            CommandLineUtils.successMessage("there are  " + theater.getShowTimes().size() + " show times of theater `" + theater.getName() + "'");
            CommandLineUtils.tableData(theater.getShowTimes(), ShowTime.class);
            CommandLineUtils.pressAnyKeyToContinue();
        }));
        primaryMenu.add(new Menu("Buy a ticket", () -> {
            if (theater.movies.size() > 0) {
                System.out.println();
                CommandLineUtils.warningMessage("Step 1 : please select a move");
                CommandLineUtils.menu(theater.movies.stream()
                                                    .map((Movie movie) -> new Menu(
                                                            movie.getTitle(),
                                                            () -> {
                                                                CommandLineUtils.warningMessage("Step 2 : please select a showtime of movie '" + movie.getTitle() + "'");
                                                                if(theater.getShowTimes().size() > 0 ){
                                                                    CommandLineUtils.menu(
                                                                            theater.getShowTimes().stream()
                                                                                   .filter((ShowTime showTime) -> showTime.getMovie() == movie)
                                                                                   .map((ShowTime showTime) -> new Menu(
                                                                                           showTime.getDateTime().toLocaleString(),
                                                                                           () -> {
                                                                                               System.out.println();
                                                                                               System.out.println();
                                                                                               showTime.printSeats();
                                                                                               CommandLineUtils.warningMessage("Step 3.please select your seat number to purchase");

                                                                                               int seatNumber = CommandLineUtils.getIntegerInput();
                                                                                               if (seatNumber < 1 || seatNumber > 100) {
                                                                                                   CommandLineUtils.errorMessage(" No. " + seatNumber + " seat not exist ");
                                                                                               }

                                                                                               int row = (seatNumber - 1) / 10;
                                                                                               int col = (seatNumber - 1) % 10;

                                                                                               if (!showTime.getSeats()[row][col].isAvailable()) {
                                                                                                   CommandLineUtils.errorMessage(" No. " + seatNumber + " seat not available ");
                                                                                               } else {
                                                                                                   Seat selectedSeat = showTime.getSeats()[row][col];
                                                                                                   selectedSeat.setAvailable(false);
                                                                                                   CommandLineUtils.successMessage("Ticket purchased successfully!");
                                                                                                   System.out.println(StringUtils.padString("Movie",20)+"\t:\t "+movie.getTitle());
                                                                                                   System.out.println(StringUtils.padString("Date and Time",20)+"\t:\t " + showTime.getDate().toLocaleString());
                                                                                                   System.out.println(StringUtils.padString("Seat position",20)+"\t:\t " + "Row: "+ selectedSeat.getRow()+" Column : "+selectedSeat.getColumn() );
                                                                                                   System.out.println(StringUtils.padString("Price",20)+"\t:\t $"+showTime.getPrice());
                                                                                                   CommandLineUtils.pressAnyKeyToContinue();
                                                                                               }

                                                                                           })
                                                                                   )
                                                                                   .collect(Collectors.toList()));
                                                                }else{
                                                                    CommandLineUtils.errorMessage("there are no show time for movie '"+movie.getTitle()+"'");
                                                                }
                                                            })
                                                    )
                                                    .collect(Collectors.toList()));
            } else {
                CommandLineUtils.errorMessage("there are no movie in movies in theater `" + theater.getName() + "'");
            }

        }));
        primaryMenu.add(new Menu("View seating", () -> {
            if (theater.movies.size() > 0) {
                System.out.println();
                CommandLineUtils.warningMessage("Step 1 : please select a move");
                CommandLineUtils.menu(theater.movies.stream()
                                                    .map((Movie movie) -> new Menu(
                                                            movie.getTitle(),
                                                            () -> {
                                                                CommandLineUtils.warningMessage("Step 2 : please select a showtime of movie '" + movie.getTitle() + "'");
                                                                if(theater.getShowTimes().size() > 0 ){
                                                                    CommandLineUtils.menu(
                                                                            theater.getShowTimes().stream()
                                                                                   .filter((ShowTime showTime) -> showTime.getMovie() == movie)
                                                                                   .map((ShowTime showTime) -> new Menu(
                                                                                           showTime.getDateTime().toLocaleString(),
                                                                                           () -> {
                                                                                               System.out.println();
                                                                                               System.out.println();
                                                                                               showTime.printSeats();
                                                                                           })
                                                                                   )
                                                                                   .collect(Collectors.toList()));
                                                                }else{
                                                                    CommandLineUtils.errorMessage("there are no show time for movie '"+movie.getTitle()+"'");
                                                                }
                                                            })
                                                    )
                                                    .collect(Collectors.toList()));
            } else {
                CommandLineUtils.errorMessage("there are no movie in movies in theater `" + theater.getName() + "'");
            }
            CommandLineUtils.pressAnyKeyToContinue();
        }));

        primaryMenu.add(new Menu("Exit", () -> {
            menuLoop = false;
            System.out.println("select exit");
        }));
    }

    public static void main(String[] args) {

        CommandLineUtils.setPrintColor(AnsiColor.BRIGHT_MAGENTA);
        System.out.println("Welcome to the " + theater.getName() + " at " + theater.getLocation() + "!");
        CommandLineUtils.setPrintColor(AnsiColor.MAGENTA);
        while (menuLoop) {
            CommandLineUtils.menu(primaryMenu);
        }
        CommandLineUtils.resetColor();
    }
}