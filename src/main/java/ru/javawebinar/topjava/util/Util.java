package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Util {
    private Util() {
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

    public static String decodeText(String input, String encoding) throws IOException {
        return new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(input.getBytes()),
                        Charset.forName(encoding)))
                .readLine();
    }
}