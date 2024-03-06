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

    Movie(ArrayList<String> actorsList, String rating, int ImdbVotes, int yearReleased) {
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
            URL url = new URL("https://www.omdbapi.com/?t="+title+"&apikey=" + API_KEY);
            URLConnection Url = url.openConnection();
            Url.setRequestProperty("apikey", API_KEY);
            BufferedReader reader = new BufferedReader(new InputStreamReader(Url.getInputStream()));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine())!=null) {
                stringBuilder.append(line);
            }
            reader.close();
            //handle an error if the chosen movie is not found
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
        String info = jo.getString("imdbVotes");
        for (int i = 0; i < info.length(); i++) {
            if (info.charAt(i) == ',') {
                info = info.replace(",", "");
            }
        }

        int ImdbVotes = Integer.parseInt(info);
        this.ImdbVotes = ImdbVotes;
        return ImdbVotes;
    }

    public String getRatingViaApi(String moviesInfoJson) {
        JSONObject jo = new JSONObject(moviesInfoJson);
        JSONArray ja = new JSONArray(jo.getJSONArray("Ratings"));
        JSONObject rating = ja.getJSONObject(0);
        String info = rating.getString("Value");
        this.rating = info;
        return info;
    }

    public void getActorListViaApi(String movieInfoJson) {
        JSONObject jo = new JSONObject(movieInfoJson);
        // parse json
        String info  = jo.getString("Actors");
        this.actorsList = new ArrayList<>(Arrays.asList(info.split(", ")));
    }

    public void getMovieYearRelease(String movieInfoJson) {
        JSONObject jo = new JSONObject(movieInfoJson);
        // parse json
        String info = jo.getString("Year");
        this.yearReleased = Integer.parseInt(info);
    }
}