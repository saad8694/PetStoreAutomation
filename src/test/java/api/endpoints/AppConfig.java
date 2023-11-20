package api.endpoints;

import java.util.ResourceBundle;

public class AppConfig {

    private static ResourceBundle config;

    static {
        String specifiedEnvironment = System.getProperty("env");
        String defaultEnvironment = "dev"; // Default to "dev" if not specified

        // Check if the specified environment is valid, otherwise use the default
        String environment = isValidEnvironment(specifiedEnvironment) ? specifiedEnvironment : defaultEnvironment;

        config = ResourceBundle.getBundle("config-" + environment);
    }

    public static String getBaseUrl() {
        return config.getString("base_url");
    }

    // Add other configuration properties as needed


    // Helper method to check if the specified environment is valid
    private static boolean isValidEnvironment(String environment) {
        return environment != null && (environment.equals("dev") || environment.equals("qa"));
    }
}