package com.sanaesencia.backend.util;

public class RunValidator {

    public static boolean isValidRun(String run) {
        if (run == null) return false;
        String clean = run.replace(".", "").replace("-", "").trim().toUpperCase();
        if (clean.length() < 8 || clean.length() > 9) return false;

        String body = clean.substring(0, clean.length() - 1);
        char dv = clean.charAt(clean.length() - 1);

        try {
            int num = Integer.parseInt(body);
            int m = 0, s = 1;
            for (; num != 0; num /= 10) {
                s = (s + num % 10 * (9 - m++ % 6)) % 11;
            }
            char expectedDv = (char) (s != 0 ? s + 47 : 75); // 75 = 'K'
            return dv == expectedDv;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String normalizeRun(String run) {
        if (run == null) return null;
        String clean = run.replace(".", "").replace("-", "").trim().toUpperCase();
        if (clean.length() < 2) return clean;
        String body = clean.substring(0, clean.length() - 1);
        char dv = clean.charAt(clean.length() - 1);
        return body + "-" + dv;
    }
}
