import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Movie {
    public static final String API_KEY = "fd60446c";
    ArrayList<String> actorsList;
    String rating;
    int ImdbVotes;
    int yearReleased;

    public Movie(ArrayList<String> actorsList, String rating, int ImdbVotes) {
        this.ImdbVotes = ImdbVotes;
        this.actorsList = actorsList;
        this.rating = rating;
    }

    public Movie(ArrayList<String> actorsList, String rating, int ImdbVotes, int yearReleased) {
        this.ImdbVotes = ImdbVotes;
        this.actorsList = actorsList;
        this.rating = rating;
        this.yearReleased = yearReleased;
    }

    @SuppressWarnings("deprecation")
    /*
      Retrieves data for the specified movie.

      @param title the name of the title for which MovieData should be retrieved
     * @return a string representation of the MovieData, or null if an error occurred
     */

    public String getMovieData(String title) {
        try {
            URL url = new URL("https://www.omdbapi.com/?t=" + title + "&apikey=" + API_KEY);
            URLConnection Url = url.openConnection();
            Url.setRequestProperty("apikey", API_KEY);
            BufferedReader reader = new BufferedReader(new InputStreamReader(Url.getInputStream()));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine())!=null) {
                stringBuilder.append(line);
            }
            reader.close();

            return stringBuilder.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getImdbVotesViaApi(String moviesInfoJson) {
        JSONObject jo = new JSONObject(moviesInfoJson);
        // parse json
        String imdbVotes = jo.getString("imdbVotes");
        for (int i = 0; i < imdbVotes.length(); i++) {
            if (imdbVotes.charAt(i) == ',') {
                imdbVotes = imdbVotes.replace(",", "");
            }
        }

        int ImdbVotes = Integer.parseInt(imdbVotes);
        this.ImdbVotes = ImdbVotes;
        return ImdbVotes;
    }

    public String getRatingViaApi(String moviesInfoJson) {
        JSONObject jo = new JSONObject(moviesInfoJson);
        JSONArray ja = new JSONArray(jo.getJSONArray("Ratings"));
        JSONObject rating = ja.getJSONObject(0);
        String value = rating.getString("Value");
        this.rating = value;
        return value;
    }

    public void getActorListViaApi(String movieInfoJson) {
        JSONObject jo = new JSONObject(movieInfoJson);
        // parse json
        String actors  = jo.getString("Actors");
        this.actorsList = new ArrayList<>(Arrays.asList(actors.split(", ")));
    }

    public void getMovieYearRelease(String movieInfoJson) {
        JSONObject jo = new JSONObject(movieInfoJson);
        // parse json
        String year = jo.getString("Year");
        this.yearReleased = Integer.parseInt(year);
    }
}
