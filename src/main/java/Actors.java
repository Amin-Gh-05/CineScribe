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
    int age;
    String gender;
    String nationality;

    public Actors(String netWorth, boolean isAlive) {
        this.netWorth = netWorth;
        this.isAlive = isAlive;
    }

    public Actors(String netWorth, boolean isAlive, String deathDate, int age, String gender, String nationality) {
        this.netWorth = netWorth;
        this.isAlive = isAlive;
        this.deathDate = deathDate;
        this.gender = gender;
        this.nationality = nationality;
    }

    @SuppressWarnings({"deprecation"})
    /*
      Retrieves data for the specified actor.
      @param name for which Actor should be retrieved
     * @return a string representation of the Actors info or null if an error occurred
     */
    public String getActorData(String name) {
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/celebrity?name=" +
                    name.replace(" ", "+") + "&apikey=" + API_KEY);
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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getNetWorthViaApi(String actorsInfoJson) {
        // make a jsonarray of the string given
        JSONArray ja = new JSONArray(actorsInfoJson);
        JSONObject jo = ja.getJSONObject(0);
        // parse json
        double net_worth = jo.getDouble("net_worth");
        this.netWorth = Double.toString(net_worth);
        return net_worth;
    }

    public boolean isAlive(String actorsInfoJson) {
        // make a jsonarray of the string given
        JSONArray ja = new JSONArray(actorsInfoJson);
        JSONObject jo = ja.getJSONObject(0);
        // parse json
        boolean is_alive = jo.getBoolean("is_alive");
        this.isAlive = is_alive;
        return is_alive;
    }

    public String getDateOfDeathViaApi(String actorsInfoJson) {
        // make a jsonarray of the string given
        JSONArray ja = new JSONArray(actorsInfoJson);
        JSONObject jo = ja.getJSONObject(0);
        // parse json
        String death = jo.getString("death");
        this.deathDate = death;
        return death;
    }

    public void getAge(String actorsInfoJson) {
        // make a jsonarray of the string given
        JSONArray ja = new JSONArray(actorsInfoJson);
        JSONObject jo = ja.getJSONObject(0);
        // parse json
        this.age = jo.getInt("age");
    }

    public void getGender(String actorsInfoJson) {
        // make a jsonarray of the string given
        JSONArray ja = new JSONArray(actorsInfoJson);
        JSONObject jo = ja.getJSONObject(0);
        // parse json
        this.gender = jo.getString("gender");
    }

    public void getNationality(String actorsInfoJson) {
        // make a jsonarray of the string given
        JSONArray ja = new JSONArray(actorsInfoJson);
        JSONObject jo = ja.getJSONObject(0);
        // parse json
        this.nationality = jo.getString("nationality");
    }
}
