package ca.appgraph.models;

/**
 * Enum representing different environment types for applications
 */
public enum EnvironmentType {
    DEV, SIT, STAGE, PROD, DR;

    public static EnvironmentType fromString(String type) {
        try {
            return EnvironmentType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid environment type: " + type);
        }
    }
}
