package com.mbeyaztas.csv.file.csvcore

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

class CsvReader(inputStream: InputStream, encode: Charset) {

    private var reader: InputStreamReader = InputStreamReader(inputStream, encode)

    fun parse(separator: Char = CsvParser.COMMA): CsvFile? {
        val doc = CsvFile()
        val parser = CsvParser(separator, doc)
        return try {
            val buffer = parser.buffer
            while (reader.read(buffer) != -1) {
                buffer.flip()
                parser.parse()
            }
            doc
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

}