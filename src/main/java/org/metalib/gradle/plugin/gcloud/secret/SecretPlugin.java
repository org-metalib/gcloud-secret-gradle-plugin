package org.metalib.gradle.plugin.gcloud.secret;

import lombok.NoArgsConstructor;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import static org.metalib.gradle.plugin.gcloud.secret.helper.Finals.GCLOUD_SECRETS;
import static org.metalib.gradle.plugin.gcloud.secret.helper.Finals.GCLOUD_SECRET_CLEAN;
import static org.metalib.gradle.plugin.gcloud.secret.helper.Finals.GCLOUD_SECRET_DELETE;
import static org.metalib.gradle.plugin.gcloud.secret.helper.Finals.GCLOUD_SECRET_GET;
import static org.metalib.gradle.plugin.gcloud.secret.helper.Finals.GCLOUD_SECRET_SET;

@NoArgsConstructor
public class SecretPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getExtensions().create(GCLOUD_SECRETS, SecretExtension.class);
        final var tasks = project.getTasks();
        tasks.register(GCLOUD_SECRET_GET, SecretGet.class);
        tasks.register(GCLOUD_SECRET_SET, SecretSet.class);
        tasks.register(GCLOUD_SECRET_DELETE, SecretDelete.class);
        tasks.register(GCLOUD_SECRET_CLEAN, SecretClean.class);
    }
}
