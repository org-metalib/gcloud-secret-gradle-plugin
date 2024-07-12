# Metalib Google Cloud Secret Management Plugin

## Getting started

```groovy
plugins {
    id 'org.metalib.gradle.plugin.gcloud.secret' version '0.0.4'
}
```

## Configuring the plugin
```groovy
gcloudSecrets {
    gcloudProjectName = "<google cloud project name>"
    gcloudSecretDir = "<relative or absolute directory to host secrets>"
    gcloudSecrets = [] 
}
```

## Tasks

The plugin adds the following tasks

| Name               | Description                                           |
|--------------------|-------------------------------------------------------|
| gcloudSecretPut    | updates or cretes secrets for gcloud project          | 
| gcloudSecretGet    | gets secrets to `gcloudSecretDir` from gcloud project |
| gcloudSecretDelete | gets secrets to `gcloudSecretDir` from gcloud project |
| gcloudSecretClean  | removes secrets from `gcloudSecretDir`                |

The plugin also updates `.gitignore` file if it find `.git` subfolder within the project folder
to exclude publishing secrets to the remote git repository.