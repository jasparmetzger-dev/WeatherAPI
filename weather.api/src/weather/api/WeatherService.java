package weather.api;

public interface WeatherService {
    WeatherReport getCurrentWeather(double longitude, double latitude);
}
