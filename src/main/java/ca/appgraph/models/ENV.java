package ca.appgraph.models;

/**
 * Enum representing different environments for applications
 */
public enum ENV {
    DEV, SIT, STAGE, PROD, DR;

    public static ENV fromString(String env) {
        try {
            return ENV.valueOf(env.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid environment type: " + env);
        }
    }
}
