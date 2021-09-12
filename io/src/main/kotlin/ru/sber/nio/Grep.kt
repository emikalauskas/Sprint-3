package ru.sber.nio

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.isDirectory

/**
 * Реализовать простой аналог утилиты grep с использованием калссов из пакета java.nio.
 */
class Grep {
    /**
     * Метод должен выполнить поиск подстроки subString во всех файлах каталога logs.
     * Каталог logs размещен в данном проекте (io/logs) и внутри содержит другие каталоги.
     * Результатом работы метода должен быть файл в каталоге io(на том же уровне, что и каталог logs), с названием result.txt.
     * Формат содержимого файла result.txt следующий:
     * имя файла, в котором найдена подстрока : номер строки в файле : содержимое найденной строки
     * Результирующий файл должен содержать данные о найденной подстроке во всех файлах.
     * Пример для подстроки "22/Jan/2001:14:27:46":
     * 22-01-2001-1.log : 3 : 192.168.1.1 - - [22/Jan/2001:14:27:46 +0000] "POST /files HTTP/1.1" 200 - "-"
     */
    fun find(subString: String) {
        val path = "io/logs"
        val resultFile = "io/result.txt"

        if (!File(path).exists())
            return

        if (File(resultFile).exists())
            File(resultFile).delete()

        Files.walk(Paths.get(path)).forEach { file ->
            if (file.toString().endsWith(".log")) {
                val lines = File(file.toUri()).readLines()
                lines.forEach { line ->
                    if (line.contains(subString))
                        File(resultFile).appendText("${file.fileName} : ${lines.indexOf(line) + 1} : $line\n")
                }
            }
        }
    }
}

fun main() {
    val grep = Grep()
    grep.find("[23/Jan/2001:13:00:00 +0000]")
}