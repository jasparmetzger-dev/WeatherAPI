module weather.openmeteo {
    requires weather.api;
    requires java.net.http;
    requires org.json;


    provides weather.api.WeatherService
            with weather.openmeteo.OpenMeteoWeatherService;
}