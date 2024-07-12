package org.metalib.gradle.plugin.gcloud.secret.exception;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String message) {
        super(message);
    }
}
