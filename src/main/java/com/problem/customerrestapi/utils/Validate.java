package com.problem.customerrestapi.utils;

import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;

public class Validate {
    // used for calculating the identifier check number
    static int[] COEFFICIENTS = new int []{7, 9, 10, 5, 8, 4, 2};
    static int PRIME_ELEVEN = 11;

    public static boolean validateBusinessId(@NotNull String businessId) {
        if (businessId.charAt(businessId.length() - 2) != '-') {
            return false;
        }
        int[] numbers = businessId.chars().filter((character) -> character >= '0' && character <= '9').map((character) -> Character.getNumericValue(character)).toArray();

        if (numbers.length != 8) {
            return false;
        }
        int actualCheckNumber = numbers[numbers.length - 1];

        int sum = IntStream.range(0, 7).map(idx -> COEFFICIENTS[idx] * numbers[idx]).reduce(0, Integer::sum);

        int modulo = sum % PRIME_ELEVEN;
        
        int calculatedCheckNumber = modulo > 1 ? PRIME_ELEVEN - modulo : modulo;

        return modulo != 1 && calculatedCheckNumber == actualCheckNumber;
    }
}
