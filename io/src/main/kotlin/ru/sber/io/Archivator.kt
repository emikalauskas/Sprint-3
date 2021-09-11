package ru.sber.io

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

/**
 * Реализовать методы архивации и разархивации файла.
 * Для реализиации использовать ZipInputStream и ZipOutputStream.
 */
class Archivator {

    /**
     * Метод, который архивирует файл logfile.log в архив logfile.zip.
     * Архив должен располагаться в том же каталоге, что и исходной файл.
     */
    fun zipLogfile() {
        val file = File("io/logfile.log")
        val zipFile = File("io/logfile.zip")

        ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile))).use { output ->
            FileInputStream(file).use { input ->
                BufferedInputStream(input).use { origin ->
                    val entry = ZipEntry("logfile.log")
                    output.putNextEntry(entry)
                    origin.copyTo(output, 1024)
                }
            }
        }
    }

    /**
     * Метод, который извлекает файл из архива.
     * Извлечь из архива logfile.zip файл и сохарнить его в том же каталоге с именем unzippedLogfile.log
     */
    fun unzipLogfile() {
        val zipFile = File("io/logfile.zip")
        val unzippedFile = File("io/unzippedLogfile.log")

        ZipFile(zipFile).use { zip ->
            val entry = zip.getEntry("logfile.log")
            zip.getInputStream(entry).use { input ->
                unzippedFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
    }
}

fun main() {
    val archivator = Archivator()
    archivator.zipLogfile()
    archivator.unzipLogfile()
}