package org.openjfx.util;
import java.util.HashMap;
import java.util.Map;

public class DetailHelper {

public static Map<String, String> parseItemDetails(String detailsString) {
    Map<String, String> detailsMap = new HashMap<>();
    if (detailsString == null || detailsString.trim().isEmpty()) {
        return detailsMap;
    }

    // Ta bort eventuellt inledande komma och trimma
    String cleanedDetails = detailsString.trim();
    if (cleanedDetails.startsWith(",")) {
        cleanedDetails = cleanedDetails.substring(1).trim();
    }

    // Splitta strängen vid varje komma som följs av ett ord och ett kolon (för att undvika problem om värden innehåller komman)
    String[] pairs = cleanedDetails.split(",\\s*(?=[^:]+:)"); // Splitta på komma följt av noll eller fler, positiv lookahead för "text:"

    for (String pair : pairs) {
        if (pair.trim().isEmpty()) continue;

        String[] keyValue = pair.split(":", 2); // Splitta på första kolonet, max 2 delar
        if (keyValue.length == 2) {
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            detailsMap.put(key, value);
        }
    }
    return detailsMap;
}
}
