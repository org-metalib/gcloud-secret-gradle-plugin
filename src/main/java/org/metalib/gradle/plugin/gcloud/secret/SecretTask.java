package org.metalib.gradle.plugin.gcloud.secret;

import lombok.Getter;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.metalib.gradle.plugin.gcloud.secret.helper.Finals.GCLOUD_SECRETS;
import static org.metalib.gradle.plugin.gcloud.secret.helper.Finals.GCLOUD_SECRET_GROUP;
import static org.metalib.gradle.plugin.gcloud.secret.helper.Finals.SECRETS_DIR;

@Getter
public abstract class SecretTask extends DefaultTask {

    @Internal
    final SecretExtension extension;

    @Internal
    final File projectDir;

    @Input
    @Optional
    String projectName;

    @Input @Optional
    String[] secrets;

    @Input @Optional
    String secretsDir;

    public SecretTask(Project project) {
        setGroup(GCLOUD_SECRET_GROUP);
        extension = project.getExtensions().findByType(SecretExtension.class);
        projectDir = project.getProjectDir();
    }

    String projectName() {
        final var result = null == projectName || projectName.isBlank()
                ? getExtension().getProjectName()
                : projectName;
        return null == result || result.isBlank()
                ? ""
                : result;
    }

    File secretsDir() {
        final var result = secretsDir == null || secretsDir.isBlank()
                ? extension.secretsDir
                : secretsDir;
        return null == result || result.isBlank()
                ? new File(projectDir, SECRETS_DIR)
                : new File(result);
    }

    List<String> secrets() {
        final var result = null == secrets || 0 == secrets.length
                ? extension.secrets
                : secrets;
        return null == result || 0 == result.length
                ? List.of()
                : Stream.of(result).distinct().collect(Collectors.toList());
    }

    boolean validate() {
        final var log = getLogger();
        final var projectName = projectName();
        if (projectName.isBlank()) {
            log.info("No secrets provided");
            return false;
        }
        final var secrets = secrets();
        if (secrets.isEmpty()) {
            log.info("No secrets provided");
            return false;
        }
        log.info("Project: {}", projectName);
        log.info("Secrets: {}", String.join(", ", secrets));
        final var secretsDir = secretsDir();
        log.info("Secrets Dir: {}", secretsDir);
        return true;
    }
}
