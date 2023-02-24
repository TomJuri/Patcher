package net.graphitemc.patcher

import org.gradle.api.provider.Property
import java.io.File

interface PatcherExtension {
    val cleanSourceDir: Property<File>
    val dirtySourceDir: Property<File>
    val patchesDir: Property<File>
    val cleanBinFile: Property<File>
    val dirtyBinFile: Property<File>
}