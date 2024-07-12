package org.metalib.gradle.plugin.gcloud.secret;

import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.metalib.gradle.plugin.gcloud.secret.helper.GitIgnoreUpdater;
import org.metalib.gradle.plugin.gcloud.secret.helper.SecretManager;

import javax.inject.Inject;

public class SecretDelete extends SecretTask {

    @Inject
    public SecretDelete(Project project) {
        super(project);
    }

    @TaskAction
    void run() {
        final var log = getLogger();
        log.info("Running Google Cloud Secret Put task");
        if (!validate()) {
            return;
        }
        final var projectName = projectName();
        log.info("Deleting secrets from project: {}", projectName);
        final var secrets = getSecrets();
        for (final var secret : SecretManager.deleteSecretsIfExists(projectName, secrets().toArray(new String[0])).entrySet()) {
            log.info("Secret: <" + secret.getKey() +
                    (Boolean.TRUE.equals(secret.getValue()) ? "> deleted." : "> not found."));
        }
        final var secretsDirectory = secretsDir();
        if (GitIgnoreUpdater.update(projectDir, secretsDirectory)) {
            log.info("Updated .gitignore file to include secret directory");
        }
    }
}
