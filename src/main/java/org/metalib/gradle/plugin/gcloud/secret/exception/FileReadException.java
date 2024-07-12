package org.metalib.gradle.plugin.gcloud.secret.exception;

public class FileReadException extends RuntimeException {
    public FileReadException(String message, Throwable e) {
        super(message, e);
    }
}
