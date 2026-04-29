package ca.appgraph.models;

public enum EnvironmentType {
    DEV, SIT, STAGE, PROD, DR;

    public static EnvironmentType fromString(String env) {
        try {
            return EnvironmentType.valueOf(env.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid environment type: " + env);
        }
    }
}
