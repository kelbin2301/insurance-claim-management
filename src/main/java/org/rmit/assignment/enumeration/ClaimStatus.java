package org.rmit.assignment.enumeration;

public enum ClaimStatus {
    NEW("new"),
    PROCESSING("processing"),
    DONE("done");

    private final String value;


    ClaimStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ClaimStatus fromValue(String value) {
        for (ClaimStatus status : ClaimStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
