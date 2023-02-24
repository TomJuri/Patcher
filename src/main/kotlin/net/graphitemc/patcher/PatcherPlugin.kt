package net.graphitemc.patcher

import org.gradle.api.Plugin
import org.gradle.api.Project

class PatcherPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("patcher", PatcherExtension::class.java)
        project.tasks.create("patch", BuildPatchesTask::class.java)
    }
}