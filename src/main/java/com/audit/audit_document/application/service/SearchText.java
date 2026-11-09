package com.audit.audit_document.application.service;

import java.text.Normalizer;
import java.util.Locale;

/*
 * Text search of the lists: lower case and without accents,
 * so that "reunion" finds "Réunion" (same rule as the front).
 */
final class SearchText {

    private SearchText() {
    }

    // True when the query is empty or is found in one of the values.
    static boolean matches(String query, String... values) {

        String normalizedQuery = normalize(query);

        if (normalizedQuery.isEmpty()) {
            return true;
        }

        for (String value : values) {

            if (normalize(value).contains(normalizedQuery)) {
                return true;
            }
        }

        return false;
    }

    private static String normalize(String text) {

        if (text == null) {
            return "";
        }

        return Normalizer.normalize(text.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT);
    }
}
