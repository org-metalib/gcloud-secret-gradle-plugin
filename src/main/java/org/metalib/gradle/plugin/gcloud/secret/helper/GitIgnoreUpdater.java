package org.metalib.gradle.plugin.gcloud.secret.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.metalib.gradle.plugin.gcloud.secret.exception.FileWriteException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.metalib.gradle.plugin.gcloud.secret.helper.Finals.GITIGNORE_FILE;
import static org.metalib.gradle.plugin.gcloud.secret.helper.Finals.GIT_DIR;

/**
 * Updates the .gitignore file in the project directory to include the secret directory.
 * this will prevent the secret files from being committed to the repository.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GitIgnoreUpdater {

    /**
     * Updates the .gitignore file in the project directory to include the secret directory.
     * @param projectDir The project directory.
     * @param secretDir The secret directory.
     * @return A boolean indicating whether the .gitignore file was updated.
     */
    public static boolean update(File projectDir, File secretDir) {
        if (!new File(projectDir, GIT_DIR).isDirectory()) {
            return false;
        }
        final var projectDirPath = projectDir.getAbsolutePath();
        if (!secretDir.getAbsolutePath().startsWith(projectDirPath)) {
            return false;
        }
        final var gitIgnoreFile = new File(projectDir, GITIGNORE_FILE);
        final var lines = new ArrayList<String>();
        if (gitIgnoreFile.exists()) {
            try {
                lines.addAll(Files.readAllLines(gitIgnoreFile.toPath()));
            } catch (IOException e) {
                throw new FileWriteException(".gitignore read exception", e);
            }
        }
        final var secretDirPath = secretDir.getAbsolutePath().substring(projectDirPath.length()+1);
        final var secretDirPaths = Optional.of(secretDirPath).filter(v -> !secretDirPath.endsWith("/"))
                .map(v -> Set.of(v + "/", v)).orElseGet(() -> Set.of(secretDirPath));
        if (lines.stream().noneMatch(secretDirPaths::contains)) {
            lines.add(secretDirPath);
            // update .gitignore
            try {
                Files.writeString(gitIgnoreFile.toPath(), String.join("\n", lines) + "\n");
            } catch (IOException e) {
                throw new FileWriteException(".gitignore write exception", e);
            }
            return true;
        }
        return false;
    }
}
