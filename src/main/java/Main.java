import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        runMenu();
    }

    public static void runMenu() {
        // scanner for user input
        Scanner read = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            // clear cmd screen every loop
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // title ascii art
            System.out.println("""
                  ____ _            ____            _ _         \s
                 / ___(_)_ __   ___/ ___|  ___ _ __(_) |__   ___\s
                | |   | | '_ \\ / _ \\___ \\ / __| '__| | '_ \\ / _ \\
                | |___| | | | |  __/___) | (__| |  | | |_) |  __/
                 \\____|_|_| |_|\\___|____/ \\___|_|  |_|_.__/ \\___|""");
            System.out.print("\n");
            // main menu options
            System.out.println("Which one do you want to search?");
            System.out.println("-Movie");
            System.out.println("-Actor");
            System.out.println("-Exit");
            // get input from the user
            System.out.print("Enter Your choice: ");
            String choice = read.nextLine();
            // do as user expects
            switch (choice) {
                case "Movie" -> {
                    // create a Movie object with empty attributes
                    Movie movie = new Movie(new ArrayList<>(), "", 0, 0, "", "", "");
                    System.out.print("Enter the movie title: ");
                    String title = read.nextLine();

                    // generate the json by api
                    String movieData = movie.getMovieData(title);
                    JSONObject jo = new JSONObject(movieData);
                    if (Objects.equals(jo.getString("Response"), "False")) {
                        System.out.println("Movie not found!");
                        break;
                    }

                    // set movie attributes
                    movie.getActorListViaApi(movieData);
                    movie.getRatingViaApi(movieData);
                    movie.getImdbVotesViaApi(movieData);
                    movie.getMovieYearRelease(movieData);
                    movie.getRunTime(movieData);
                    movie.getMovieDirector(movieData);
                    movie.getMovieCountry(movieData);

                    // output movie attributes
                    System.out.println("Actors: ");
                    for (String actr : movie.actorsList) {
                        System.out.println("-" + actr);
                    }
                    System.out.println("IMDB rating: " + movie.rating + " by vote count of " + movie.ImdbVotes);
                    System.out.println("Year released: " + movie.yearReleased);
                    System.out.println("Runtime: " + movie.runTime);
                    System.out.println("Director: " + movie.director);
                    System.out.println("Country produced: " + movie.country);
                }
                case "Actor" -> {
                    // create an Actors object with empty attributes
                    Actors actors = new Actors("", true, "", 0, "", "");
                    System.out.print("Enter the actor name: ");
                    String name = read.nextLine();

                    // generate the json by api
                    String actorInfo = actors.getActorData(name);
                    if (Objects.equals(actorInfo, "[]")) {
                        System.out.println("Actor not found!");
                        break;
                    }

                    // set actor attributes
                    actors.getNetWorthViaApi(actorInfo);
                    actors.isAlive(actorInfo);
                    if (!(actors.isAlive)) {
                        actors.getDateOfDeathViaApi(actorInfo);
                    } else {
                        actors.getAge(actorInfo);
                    }
                    actors.getGender(actorInfo);
                    actors.getNationality(actorInfo);

                    // output actors attributes
                    System.out.println("NetWorth: " + actors.netWorth);
                    if (actors.isAlive) {
                        System.out.println("Age: " + actors.age);
                    } else {
                        System.out.println("Date of death: " + actors.deathDate);
                    }
                    System.out.println("Gender: " + actors.gender);
                    System.out.println("Nationality: " + actors.nationality);
                }
                case "Exit" -> {
                    flag = false;
                    System.out.println("Thank you for using our app!");
                }
                case null, default -> System.out.println("Please enter a valid choice!");
            }

            // hold back the program from running to let user see results
            System.out.print("\n");
            System.out.print("Please press enter...");
            read.nextLine();
        }
    }
}
