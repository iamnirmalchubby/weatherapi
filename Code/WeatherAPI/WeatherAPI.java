package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Scanner;

public class WeatherAPI {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Get weather");
            System.out.println("2. Get Wind Speed");
            System.out.println("3. Get Pressure");
            System.out.println("0. Exit");

            int choice = sc.nextInt();
            if (choice == 0) {
            	System.out.println("Exited..");
                break;
            }

            System.out.println("Enter the date and time(YYYY-MM-DD HH:mm:ss):");
            String date = sc.next();
            String time = sc.next();

            switch (choice) {
                case 1:
                    getWeather(date, time);
                    break;
                case 2:
                    getWindSpeed(date, time);
                    break;
                case 3:
                    getPressure(date, time);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }

    private static void getWeather(String date, String time) {
        String apiUrl = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";
        String jsonResponse = makeHttpRequest(apiUrl);

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray forecasts = jsonObject.getJSONArray("list");

            for (int i = 0; i < forecasts.length(); i++) {
                JSONObject forecast = forecasts.getJSONObject(i);
                String forecastDate = forecast.getString("dt_txt").split(" ")[0];
                String forecastTime = forecast.getString("dt_txt").split(" ")[1];
                
				if (forecastDate.equals(date) && forecastTime.equals(time)) {
                    JSONObject main = forecast.getJSONObject("main");
                    double temperature = main.getDouble("temp");
                    System.out.println("Weather information for " + date + " " + time + ":");
                    System.out.println("Temperature: " + temperature + "°C");
                    return;
                }
            }

            System.out.println("Weather information for " + date + " " + time + " not found.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void getWindSpeed(String date, String time) {
        String apiUrl = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";
        String jsonResponse = makeHttpRequest(apiUrl);

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray forecasts = jsonObject.getJSONArray("list");

            for (int i = 0; i < forecasts.length(); i++) {
                JSONObject forecast = forecasts.getJSONObject(i);
                String forecastDate = forecast.getString("dt_txt").split(" ")[0];
                String forecastTime = forecast.getString("dt_txt").split(" ")[1];
                if (forecastDate.equals(date) && forecastTime.equals(time)) {
                    JSONObject wind = forecast.getJSONObject("wind");
                    double windSpeed = wind.getDouble("speed");
                    System.out.println("Wind speed for " + date + " " + time + ": " + windSpeed + " m/s");
                    return;
                }
            }

            System.out.println("Wind speed for " + date + " " + time + " not found.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void getPressure(String date, String time) {
        String apiUrl = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";
        String jsonResponse = makeHttpRequest(apiUrl);

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray forecasts = jsonObject.getJSONArray("list");

            for (int i = 0; i < forecasts.length(); i++) {
                JSONObject forecast = forecasts.getJSONObject(i);
                String forecastDate = forecast.getString("dt_txt").split(" ")[0];
                String forecastTime = forecast.getString("dt_txt").split(" ")[1];
                if (forecastDate.equals(date) && forecastTime.equals(time)) {
                    JSONObject main = forecast.getJSONObject("main");
                    double pressure = main.getDouble("pressure");
                    System.out.println("Pressure for " + date + " " +  time + ": " + pressure + " hPa");
                    return;
                }
            }

            System.out.println("Pressure for " + date + " " +  time + " not found.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String makeHttpRequest(String apiUrl) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
