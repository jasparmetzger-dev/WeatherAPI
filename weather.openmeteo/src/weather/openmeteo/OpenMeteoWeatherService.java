package weather.openmeteo;

import weather.api.WeatherReport;
import weather.api.WeatherService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenMeteoWeatherService implements WeatherService {
    @Override
    public WeatherReport getCurrentWeather(double latitude, double longitude) {

        String BASE_URL = "https://api.open-meteo.com/v1/forecast"
                    + "?longitude=" + longitude
                    + "latitude=" + latitude
                    + "&currentweather=true";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URL)).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return parse(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error fetching weather", e);
        }
    }

    private WeatherReport parse(String json) { //copied
        double temperature = extract(json, "\"temperature\":");
        double windSpeed = extract(json, "\"windspeed\":");
        return new WeatherReport(temperature, windSpeed);
    }

    private double extract(String json, String key) { //copied
        int start = json.indexOf(key) + key.length();
        int end = json.indexOf(",", start);
        return Double.parseDouble(json.substring(start, end));
    }

}
