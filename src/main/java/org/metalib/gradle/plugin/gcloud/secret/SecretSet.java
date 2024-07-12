package org.metalib.gradle.plugin.gcloud.secret;

import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.metalib.gradle.plugin.gcloud.secret.exception.FileNotFoundException;
import org.metalib.gradle.plugin.gcloud.secret.exception.FileReadException;
import org.metalib.gradle.plugin.gcloud.secret.helper.GitIgnoreUpdater;
import org.metalib.gradle.plugin.gcloud.secret.helper.SecretManager;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;

import static java.lang.String.format;

public class SecretSet extends SecretTask {

    @Inject
    public SecretSet(Project project) {
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
        final var secretValues = new LinkedHashMap<String,String>();
        for (final var secretId : secrets()) {
            final var secretFile = new File(secretsDirectory, secretId);
            if (!secretFile.exists()) {
                throw new FileNotFoundException("secret file not found: " + secretFile.getAbsolutePath());
            }
            try {
                secretValues.put(secretId, new String(Files.readAllBytes(secretFile.toPath())));
            } catch (IOException e) {
                throw new FileReadException(format("secretFile: %s", secretFile), e);
            }
        }
        final var projectName = projectName();
        log.info("Setting secrets for project: {}", projectName);
        for (final var secret : SecretManager.upsertSecrets(projectName, secretValues).entrySet()) {
            log.info("Secret: {} updated: ", secret.getKey().getSecret());
        }
        if (GitIgnoreUpdater.update(projectDir, secretsDirectory)) {
            log.info("Updated .gitignore file to include secret directory");
        }
    }
}
