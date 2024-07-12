package org.metalib.gradle.plugin.gcloud.secret;

import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.metalib.gradle.plugin.gcloud.secret.exception.DirectoryCreateException;
import org.metalib.gradle.plugin.gcloud.secret.exception.FileWriteException;
import org.metalib.gradle.plugin.gcloud.secret.helper.GitIgnoreUpdater;
import org.metalib.gradle.plugin.gcloud.secret.helper.SecretManager;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SecretGet extends SecretTask {

    @Inject
    public SecretGet(Project project) {
        super(project);
    }

    @TaskAction
    void run() {
        final var log = getLogger();
        log.info("Running Google Cloud Secret Put task");
        if (!validate()) {
            return;
        }
        final var secretsDirectory = secretsDir();
        if (!secretsDirectory.exists() && !secretsDirectory.mkdirs()) {
            throw new DirectoryCreateException("Failed to create secret directory: " + secretsDirectory.getAbsolutePath());
        }
        final var projectName = projectName();
        log.info("Retrieving secrets from project: {}", projectName);
        for (final var secret : SecretManager.retrieveSecret(projectName, secrets().toArray(new String[0])).entrySet()) {
            final var secretFile = new File(secretsDirectory, secret.getKey());
            try {
                Files.write(secretFile.toPath(), secret.getValue().getBytes());
            } catch (IOException e) {
                throw new FileWriteException("Secret File: " + secretFile, e);
            }
            log.info("Secret: {} retrieved: {}", secret.getKey(), secretFile.getAbsolutePath());
        }
        if (GitIgnoreUpdater.update(projectDir, secretsDirectory)) {
            log.info("Updated .gitignore file to include secret directory");
        }
    }
}
