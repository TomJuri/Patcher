package net.graphitemc.patcher

import org.gradle.api.Plugin
import org.gradle.api.Project

class PatcherPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("patcher", PatcherExtension::class.java)
        gextension = extension
        project.tasks.create("buildPatches", BuildPatchesTask::class.java).group = "Patcher"
        project.tasks.create("applyPatches", ApplyPatchesTask::class.java).group = "Patcher"
    }

    companion object {
        lateinit var gextension: PatcherExtension
    }
}