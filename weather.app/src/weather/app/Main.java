package weather.app;

import weather.api.WeatherService;
import weather.api.WeatherReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.ServiceLoader;

public class Main {
    static void main(String[] args) {

        WeatherService service = ServiceLoader.load(WeatherService.class)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("No WeatherService found")
                );

        List<double[]> coordinates = getCoordinates();

        System.out.println("\nFetching coordinates for " + coordinates.size() + " locations...");

        for (double[] coord : coordinates) {
            double lat = coord[0];
            double lon = coord[1];

            WeatherReport report = service.getCurrentWeather(lat, lon);
            System.out.printf("Lat: %.4f, Lon: %.4f -> Temperature: %.1f °C, Wind: %.1f km/h%n",
                    lat, lon, report.temperature(), report.windspeed());
        }


    }
    public static List<double[]> getCoordinates() {

        Scanner scanner = new Scanner(System.in);
        List<double[]> coordinates = new ArrayList<>();

        System.out.println("Enter coordinates (latitude longitude), one per line.");
        System.out.println("Type 'done' when finished:");

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("done")) break;
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");

            if (parts.length != 2) {
                System.out.println("Invalid input. Enter latitude and longitude separated by a space. Try again.");
                continue;
            }
            try {
                double lat = Double.parseDouble(parts[0].replace(',', '.'));
                double lon = Double.parseDouble(parts[1].replace(',', '.'));
                coordinates.add(new double[]{lat, lon});
            } catch (NumberFormatException e) {
                System.out.println("Invalid coordinates. Try again.");
            }
        }

        return coordinates;
    }
}
