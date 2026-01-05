# Java Modularity Weather Example (JPMS)

This project demonstrates **Java Platform Module System (JPMS)** using a
real-world example: fetching live weather data from an external HTTP API.

The focus is on:
- Explicit dependencies
- Strong encapsulation
- Service-based architecture
- CLI-only execution (no frameworks, no GUI)

---

## Project Structure

weather-real/
├── weather.api/
│ └── src/
│ ├── module-info.java
│ └── weather/api/
│ ├── WeatherService.java
│ └── WeatherReport.java
│
├── weather.openmeteo/
│ └── src/
│ ├── module-info.java
│ └── weather/openmeteo/
│ └── OpenMeteoWeatherService.java
│
└── weather.app/
└── src/
├── module-info.java
└── weather/app/
└── Main.java


---

## Module Responsibilities

### `weather.api`
- Defines public interfaces and domain models
- Contains **no implementation**
- Exported for other modules

```java
module weather.api {
    exports weather.api;
}