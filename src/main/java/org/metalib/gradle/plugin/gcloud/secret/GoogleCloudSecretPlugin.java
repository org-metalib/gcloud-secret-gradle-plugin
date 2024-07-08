package org.metalib.gradle.plugin.gcloud.secret;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GoogleCloudSecretPlugin implements Plugin<Project> {

    public GoogleCloudSecretPlugin() {
        // Empty
    }

    @Override
    public void apply(Project project) {
        project.task("hello").doLast(task -> {
            System.out.println("Google Cloud Secret Plugin says hello");
        });
        System.out.println("Google Cloud Secret Plugin");
    }
}
