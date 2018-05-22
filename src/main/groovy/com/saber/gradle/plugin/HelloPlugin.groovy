package com.saber.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

class HelloPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('hello', type: HelloTask)
    }
}

class HelloTask extends DefaultTask {
    @TaskAction
    def sayhello() {
        logger.lifecycle("Hello world !!!")
    }
}