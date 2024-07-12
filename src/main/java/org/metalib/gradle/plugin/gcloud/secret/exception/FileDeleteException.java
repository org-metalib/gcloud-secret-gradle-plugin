package org.metalib.gradle.plugin.gcloud.secret.exception;

public class FileDeleteException extends RuntimeException {
    public FileDeleteException(String message, Throwable e) {
        super(message, e);
    }
}
