package org.metalib.gradle.plugin.gcloud.secret;

import lombok.Getter;

//@Setter
@Getter
public class SecretExtension {
    String projectName;
    String[] secrets;
    String secretsDir;
}
