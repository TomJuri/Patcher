package net.graphitemc.patcher

import difflib.DiffUtils
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectories
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.PrintStream

abstract class BuildPatchesTask : DefaultTask() {

    @get:InputDirectory
    abstract val cleanSourceInput: DirectoryProperty
    @get:InputDirectory
    abstract val dirtySourceInput: DirectoryProperty
    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun buildPatches() {
        val clean = cleanSourceInput.get().asFile
        val dirty = dirtySourceInput.get().asFile
        val output = outputDirectory.get().asFile
        output.deleteRecursively()
        output.mkdirs()

        // Loop through each file in the dirty directory and compare it to the clean directory
        dirty.walk().forEach { dirtyFile ->
            if (dirtyFile.isFile) {
                val relativePath = dirty.toPath().relativize(dirtyFile.toPath())
                val cleanFile = File(clean, relativePath.toString())

                if (cleanFile.isFile && cleanFile.exists()) {
                    // Compare the two files using a byte-by-byte comparison
                    if (!dirtyFile.readBytes().contentEquals(cleanFile.readBytes())) {
                        // Create patch file
                        val patchFile = File(output, relativePath.toString())
                        patchFile.parentFile.mkdirs()

                        val patchOutput = PrintStream(patchFile)
                        val patch = DiffUtils.diff(cleanFile.readLines(), dirtyFile.readLines())

                        DiffUtils.generateUnifiedDiff(
                            cleanFile.path, dirtyFile.path,
                            cleanFile.readLines(), patch, 0
                        ).forEach(patchOutput::println)

                        patchOutput.close()
                    }
                }
            }
        }
    }
}