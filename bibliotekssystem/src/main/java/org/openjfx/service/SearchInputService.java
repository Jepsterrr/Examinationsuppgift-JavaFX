package org.openjfx.service;

public class SearchInputService {

    public static boolean isPotentialISBNAttempt(String inputText) {
        if (inputText == null) {
            return false;
        }

        // Rensa mellanslag osv
        String cleanedText = inputText.replaceAll("[\\s-]+", "");

        int length = cleanedText.length();

        // 2. Kontrollera längden på den rensade strängen. ISBN är antingen 10 eller 13 tecken. Vi tillåter ett lite snävare intervall
        if (length < 9 || length > 13) {
            return false;
        }

        // 3. Kontrollera tecknen i den rensade strängen
        for (int i = 0; i < length; i++) {
            char currentChar = cleanedText.charAt(i);
            if (Character.isDigit(currentChar)) {
                // Tecknet är en siffra, fortsätt
                continue;
            } else {
                // Tecknet är inte en siffra, det är troligtvis inte ett försök till ISBN 
                return false;
            }
        }
        
        return true;
    }
}
