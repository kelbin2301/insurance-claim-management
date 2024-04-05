package org.rmit.assignment.utils;

public class IdGeneratorUtils {
    public static String generateId(String prefix, int number) {
        return prefix + number;
    }

    public static String generateClaimId() {
        return generateId("f-", (int) (Math.random() * 1000000000));
    }

    public static String generateCustomerId() {
        return generateId("c-", (int) (Math.random() * 10000000));
    }

    public static String generateBankingInfoId() {
        return generateId("b-", (int) (Math.random() * 10000000));
    }
}
