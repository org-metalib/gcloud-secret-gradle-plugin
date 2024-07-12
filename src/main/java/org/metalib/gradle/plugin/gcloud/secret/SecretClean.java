package org.metalib.gradle.plugin.gcloud.secret;

import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.metalib.gradle.plugin.gcloud.secret.exception.DirectoryCreateException;
import org.metalib.gradle.plugin.gcloud.secret.exception.FileDeleteException;
import org.metalib.gradle.plugin.gcloud.secret.helper.GitIgnoreUpdater;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SecretClean extends SecretTask {

    @Inject
    public SecretClean(Project project) {
        super(project);
    }

    @TaskAction
    void run() {
        final var log = getLogger();
        log.info("Running Google Cloud Secret Clean task");
        if (!validate()) {
            return;
        }
        final var secretsDirectory = secretsDir();
        if (!secretsDirectory.exists() && !secretsDirectory.mkdirs()) {
            throw new DirectoryCreateException("Failed to create secret directory: " + secretsDirectory.getAbsolutePath());
        }
        log.info("Clear secrets in: {}", secretsDirectory.getAbsolutePath());
        for (final var secret : secrets()) {
            final var secretFile = new File(secretsDirectory, secret);
            try {
                if (Files.deleteIfExists(secretFile.toPath())) {
                    log.info("Secret file " + secretFile.getAbsolutePath() + " deleted.");
                }
            } catch (IOException e) {
                throw new FileDeleteException("File delete exception " + secretFile.getAbsolutePath(), e);
            }
        }
        if (GitIgnoreUpdater.update(projectDir, secretsDirectory)) {
            log.info("Updated .gitignore file to include secret directory");
        }
    }
}
