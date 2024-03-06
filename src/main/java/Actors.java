import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
public class Actors {
    public static final String API_KEY = "ZXMUN2HCOKB4B+TUfs0jNw==47tTwUHPq1FbZsj3";
    String netWorth;
    Boolean isAlive;
    String deathDate;

    public Actors(String netWorth, boolean isAlive) {
        this.netWorth = netWorth;
        this.isAlive = isAlive;
    }

    Actors(String netWorth, boolean isAlive, String deathDate) {
        this.netWorth = netWorth;
        this.isAlive = isAlive;
        this.deathDate = deathDate;
    }
    @SuppressWarnings({"deprecation"})
    /*
      Retrieves data for the specified actor.
      @param name for which Actor should be retrieved
     * @return a string representation of the Actors info or null if an error occurred
     */
    public String getActorData(String name) {
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/celebrity?name="+
                    name.replace(" ", "+")+"&apikey="+API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", API_KEY);
            System.out.println(connection);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                return response.toString();
            } else {
                return "Error: " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public double getNetWorthViaApi(String actorsInfoJson) {
        // make a jsonarray of the string given
        JSONArray ja = new JSONArray(actorsInfoJson);
        JSONObject jo = ja.getJSONObject(0);
        // parse json
        double info = jo.getDouble("net_worth");
        this.netWorth = Double.toString(info);
        return info;
    }

    public boolean isAlive(String actorsInfoJson) {
        // make a jsonarray of the string given
        JSONArray ja = new JSONArray(actorsInfoJson);
        JSONObject jo = ja.getJSONObject(0);
        // parse json
        boolean info = jo.getBoolean("is_alive");
        this.isAlive = info;
        return info;
    }

    public String getDateOfDeathViaApi(String actorsInfoJson) {
        // make a jsonarray of the string given
        JSONArray ja = new JSONArray(actorsInfoJson);
        JSONObject jo = ja.getJSONObject(0);
        // parse json
        String info = jo.getString("death");
        this.deathDate = info;
        return info;
    }

}
