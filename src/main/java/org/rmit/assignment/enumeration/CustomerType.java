package org.rmit.assignment.enumeration;

public enum CustomerType {
    POLICY_HOLDER("policy_holder"),
    DEPENDENT("dependent");

    private final String value;

    CustomerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CustomerType fromValue(String value) {
        for (CustomerType type : CustomerType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
