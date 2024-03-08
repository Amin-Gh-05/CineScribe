import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        runMenu();
    }

    public static void runMenu() {
        System.out.println("Welcome to CineScribe!");
        // scanner for user input
        Scanner read = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("Which one do you want to search?");
            System.out.println("-Movie");
            System.out.println("-Actor");
            System.out.println("-Exit");
            // get input from the user
            System.out.print("Enter Your choice: ");
            String choice = read.nextLine();
            switch (choice) {
                case "Movie" -> {
                    Movie movie = new Movie(new ArrayList<>(), "", 0, 0);
                    System.out.print("Enter the movie title: ");
                    String title = read.nextLine();
                    // generate the json by api
                    String movieData = movie.getMovieData(title);
                    // set movie attributes
                    movie.getActorListViaApi(movieData);
                    movie.getRatingViaApi(movieData);
                    movie.getImdbVotesViaApi(movieData);
                    movie.getMovieYearRelease(movieData);
                    // output movie actors
                    System.out.print("Actors: ");
                    for (String actr : movie.actorsList) {
                        System.out.print(actr + " ");
                    }
                    System.out.print("\n");
                    // output movie rating with count
                    System.out.println("IMDB rating: " + movie.rating + " by vote count of " + movie.ImdbVotes);
                    // output movie year of release
                    System.out.println("Year released: " + movie.yearReleased);
                }
                case "Actor" -> {
                    Actors actors = new Actors("", true);
                    System.out.print("Enter the actor name: ");
                    String name = read.nextLine();
                    // generate the json by api
                    String actorInfo = actors.getActorData(name);
                    // set actor attributes
                    actors.getNetWorthViaApi(actorInfo);
                    actors.isAlive(actorInfo);
                    if (!(actors.isAlive)) {
                        actors.getDateOfDeathViaApi(actorInfo);
                    } else {
                        actors.getAge(actorInfo);
                    }
                    // output actors networth
                    System.out.println("NetWorth: " + actors.netWorth);
                    // output date of death if passed and age if alive
                    if (actors.isAlive) {
                        System.out.println("Age: " + actors.age);
                    } else {
                        System.out.println("Date of death: " + actors.deathDate);
                    }
                }
                case "Exit" -> {
                    flag = false;
                    System.out.println("Thank you for using our app!");
                }
                case null, default -> System.out.println("Please enter a valid choice!");
            }
        }
    }
}
