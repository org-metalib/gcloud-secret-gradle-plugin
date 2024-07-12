package org.metalib.gradle.plugin.gcloud.secret.exception;

public class FileWriteException extends RuntimeException {
    public FileWriteException(String message, Throwable e) {
        super(message, e);
    }
}
