package org.rmit.assignment.utils;

public class IdGeneratorUtils {
    public static String generateId(String prefix, long number) {
        return prefix + number;
    }

    public static String generateClaimId() {
        return generateId("f-", (long) (Math.random() * 10000000000L));
    }

    public static String generateCustomerId() {
        return generateId("c-", (int) (Math.random() * 10000000));
    }

    public static String generateBankingInfoId() {
        return generateId("b-", (int) (Math.random() * 10000000));
    }

    public static String generateInsuranceCardNumber() {
        // Generate the random 10 digits number
        return String.valueOf((long) (Math.random() * 10000000000L));
    }
}
