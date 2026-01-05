module weather.openmeteo {
    requires weather.api;
    requires java.net.http;

    provides weather.api.WeatherService
            with weather.openmeteo.OpenMeteoWeatherService;
}