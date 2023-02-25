package net.graphitemc.patcher

import difflib.DiffUtils
import difflib.Patch
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class ApplyPatchesTask : DefaultTask() {

    @TaskAction
    fun applyPatches() {
        // Loop through each patch file in the directory and apply it to the corresponding clean file
        PatcherPlugin.gextension.patchesDir.get().walk().forEach { patchFile ->
            if (patchFile.isFile) {
                // Construct paths for clean file and output file
                val patch = patchFile.toPath().subpath(PatcherPlugin.gextension.patchesDir.get().toPath().nameCount, patchFile.toPath().nameCount)
                val clean = PatcherPlugin.gextension.cleanSourceDir.get().toPath().resolve(patch).toString().replace(".patch", "")
                println(patch + " " + clean)
            /*
                val cleanFile = File(PatcherPlugin.gextension.cleanSourceDir.get(), relativePath.toString().replace(".patch", ""))
                val outputFile = File(PatcherPlugin.gextension.dirtySourceDir.get(), relativePath.toString().replace(".patch", ""))

                if (cleanFile.isFile && cleanFile.exists()) {
                    // Apply the patch to the clean file
                    val patch = patchFile.readText()
                    val original = cleanFile.readLines()
                    val patches = DiffUtils.parseUnifiedDiff(patch.lines())

                    val result = patches.applyTo(original)
                    // Write the patched file to the output directory
                        outputFile.createNewFile()
                            outputFile.writeText(result.joinToString("\n"))
                }*/
            }
        }
    }
}