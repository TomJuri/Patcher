package net.graphitemc.patcher

import codechicken.diffpatch.cli.DiffOperation
import codechicken.diffpatch.util.archiver.ArchiveFormat
import com.github.javaparser.utils.LineSeparator.getLineEnding
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.nio.file.Path
import kotlin.io.path.readText
import kotlin.io.path.walk


abstract class BuildPatchesTask : DefaultTask() {

    @TaskAction
    fun buildPatches() {
        val clean: Path = PatcherPlugin.patcherExtension.cleanSourceDir.get().toPath()
        val dirty: Path = PatcherPlugin.patcherExtension.dirtySourceDir.get().toPath()
        val output: Path = PatcherPlugin.patcherExtension.patchesDir.get().toPath()
        val builder: DiffOperation.Builder = DiffOperation.builder()
            .aPath(clean)
            .bPath(dirty)
            .outputPath(output)
        val result = builder.build().operate()
        val exit = result.exit
        output.toFile().walk().forEach {
            if (it.isFile) {
                if (it.readText().contains("/dev/null")) {
                    it.delete()
                }
            }
        }
        if (exit != 0 && exit != 1) {
            throw RuntimeException("DiffPatch failed with exit code: $exit")
        }
    }
}