package org.metalib.gradle.plugin.gcloud.secret.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Finals {
    public static final String GITIGNORE_FILE = ".gitignore";
    public static final String GIT_DIR = ".git";

    public static final String GCLOUD_SECRET_GROUP = "gcloud-secrets";
    public static final String GCLOUD_SECRETS = "gcloudSecrets";
    public static final String GCLOUD_SECRET_SET = "gcloudSecretSet";
    public static final String GCLOUD_SECRET_GET = "gcloudSecretGet";
    public static final String GCLOUD_SECRET_DELETE = "gcloudSecretDelete";
    public static final String GCLOUD_SECRET_CLEAN = "gcloudSecretClean";
    public static final String SECRETS_DIR = ".secrets";
}
