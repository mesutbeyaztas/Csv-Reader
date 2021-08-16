package com.mbeyaztas.csv.file.csvcore

import com.google.common.truth.Truth.assertThat
import com.mbeyaztas.csv.file.csvcore.fakes.CsvTestData
import com.mbeyaztas.csv.file.csvcore.fakes.CsvTestResultData
import com.mbeyaztas.csv.file.csvcore.utils.sameContentWith
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.charset.Charset

class CsvReaderTest {

    private lateinit var csvReader: CsvReader
    private lateinit var csvTestData: CsvTestData
    private lateinit var csvTestResultData: CsvTestResultData

    @Before
    fun setup() {
        csvTestData = CsvTestData()
        csvTestResultData = CsvTestResultData()
    }

    @Test
    fun `success convert simple`() {
        val inputStream: InputStream = ByteArrayInputStream(csvTestData.simple.toByteArray())
        csvReader = CsvReader(inputStream, Charset.defaultCharset())

        val csvFile = csvReader.parse()
        assertThat(csvFile?.rowList?.get(0)?.colList!!.sameContentWith(csvTestResultData.simple))
    }

    @Test
    fun `success convert quote`() {
        val inputStream: InputStream = ByteArrayInputStream(csvTestData.quote.toByteArray())
        csvReader = CsvReader(inputStream, Charset.defaultCharset())

        val csvFile = csvReader.parse()
        assertThat(csvFile?.rowList?.get(0)?.colList!!.sameContentWith(csvTestResultData.quote))
    }

    @Test
    fun `success convert space`() {
        val inputStream: InputStream = ByteArrayInputStream(csvTestData.space.toByteArray())
        csvReader = CsvReader(inputStream, Charset.defaultCharset())

        val csvFile = csvReader.parse()
        assertThat(csvFile?.rowList?.get(0)?.colList!!.sameContentWith(csvTestResultData.space))
    }


}