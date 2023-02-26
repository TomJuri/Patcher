package net.graphitemc.patcher

import codechicken.diffpatch.cli.DiffOperation
import codechicken.diffpatch.cli.PatchOperation
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.nio.file.Path

abstract class ApplyPatchesTask : DefaultTask() {

    @TaskAction
    fun applyPatches() {
        val clean: Path = PatcherPlugin.patcherExtension.cleanSourceDir.get().toPath()
        val dirty: Path = PatcherPlugin.patcherExtension.dirtySourceDir.get().toPath()
        val patches: Path = PatcherPlugin.patcherExtension.patchesDir.get().toPath()
        val builder: PatchOperation.Builder = PatchOperation.builder()
            .basePath(clean)
            .outputPath(dirty)
            .patchesPath(patches)
        val result = builder.build().operate()
        val exit = result.exit
        if (exit != 0 && exit != 1) {
            throw RuntimeException("DiffPatch failed with exit code: $exit")
        }
    }
}