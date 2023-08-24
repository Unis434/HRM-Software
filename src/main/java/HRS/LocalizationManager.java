package HRS;

import java.util.HashMap;
import java.util.Map;

public class LocalizationManager {
    private Map<String, Map<String, String>> languageResources;

    public LocalizationManager() {
        // Initialize language resource maps
        languageResources = new HashMap<>();

        // Load English language resources
        Map<String, String> englishResources = new HashMap<>();
        englishResources.put("welcome", "Welcome to the Sierra Leone Payroll System!");
        englishResources.put("greeting", "Hello, {0}!");
        englishResources.put("error", "An error occurred: {0}");

        // Load Krio language resources
        Map<String, String> krioResources = new HashMap<>();
        krioResources.put("welcome", "Welcam tu di Salone Payrol Sistem!");
        krioResources.put("greeting", "Mi Fambul, Kabor  {0}!");
        krioResources.put("error", "Wan erra bin hapun: {0}");

        // Add language resources to the manager
        languageResources.put("en", englishResources); // English
        languageResources.put("kri", krioResources);   // Krio
    }

    public String localize(String languageCode, String key, Object... params) {
        // Check if the specified language is supported
        if (languageResources.containsKey(languageCode)) {
            Map<String, String> resources = languageResources.get(languageCode);
            if (resources.containsKey(key)) {
                String localizedText = resources.get(key);

                // Replace placeholders with parameters
                for (int i = 0; i < params.length; i++) {
                    localizedText = localizedText.replace("{" + i + "}", params[i].toString());
                }

                return localizedText;
            }
        }

        // If the language or key is not found, return a default message
        return "Localization not available for key: " + key;
    }

    public static void main(String[] args) {
        LocalizationManager localizationManager = new LocalizationManager();

        // Example usage for English
        String englishGreeting = localizationManager.localize("en", "greeting", "Alice");
        System.out.println(englishGreeting);

        // Example usage for Krio
        String krioGreeting = localizationManager.localize("kri", "greeting", "oo");
        System.out.println(krioGreeting);
    }
}
