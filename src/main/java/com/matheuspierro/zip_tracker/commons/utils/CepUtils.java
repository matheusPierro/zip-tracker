package com.matheuspierro.zip_tracker.commons.utils;

import java.util.regex.Pattern;

public final class CepUtils {
    private static final Pattern CEP_PATTERN = Pattern.compile("\\d{8}");

    public static String normalizeCep(String cep) {
        if (cep == null) return null;
        return cep.replaceAll("\\D", ""); // remove non-digits
    }

    public static boolean isValidCep(String normalized) {
        return normalized != null && CEP_PATTERN.matcher(normalized).matches();
    }

    public static String formatWithHyphen(String normalized) {
        if (!isValidCep(normalized)) return normalized;
        return normalized.substring(0, 5) + "-" + normalized.substring(5);
    }
}
