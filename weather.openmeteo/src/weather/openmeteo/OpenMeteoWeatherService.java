package weather.openmeteo;

import weather.api.WeatherReport;
import weather.api.WeatherService;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenMeteoWeatherService implements WeatherService {

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public WeatherReport getCurrentWeather(double latitude, double longitude) {
        String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current_weather=true",
                latitude, longitude
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.print("RESPONSE" + response);
            return parse(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error fetching weather", e);
        }
    }

    private WeatherReport parse(String json) {
        JSONObject obj = new JSONObject(json);
        JSONObject current = obj.getJSONObject("current_weather");

        double temperature = current.getDouble("temperature");
        double windSpeed = current.getDouble("windspeed");

        return new WeatherReport(temperature, windSpeed);
    }
}
