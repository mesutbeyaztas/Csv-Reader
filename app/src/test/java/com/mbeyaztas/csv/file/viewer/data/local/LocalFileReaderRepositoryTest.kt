package com.mbeyaztas.csv.file.viewer.data.local

import android.content.Context
import android.net.Uri
import com.google.common.truth.Truth.assertThat
import com.mbeyaztas.csv.file.csvcore.IllegalTokenException
import com.mbeyaztas.csv.file.viewer.commons.utils.Result
import com.mbeyaztas.csv.file.viewer.data.local.fakes.CsvTestData
import com.mbeyaztas.csv.file.viewer.data.local.fakes.CsvTestResultData
import com.mbeyaztas.csv.file.viewer.data.local.utils.sameContentWith
import com.mbeyaztas.csv.file.viewer.presentation.list.utils.CoroutineTestRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.io.ByteArrayInputStream

@ExperimentalCoroutinesApi
class LocalFileReaderRepositoryTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val context = mockk<Context>()
    private lateinit var readerRepository: LocalFileReaderRepository


    @Before
    fun init() {
        readerRepository = LocalFileReaderRepository(coroutineTestRule.testDispatcher, this.context)
        mockkStatic(Uri::class)
    }

    @Test
    fun `read return Success`() = runBlockingTest {
        val uri = ""
        val mockUri = Mockito.mock(Uri::class.java)
        every { Uri.parse(uri) } returns mockUri

        every {
            this@LocalFileReaderRepositoryTest.context.contentResolver.openInputStream(mockUri)
        } returns ByteArrayInputStream(CsvTestData().simple.toByteArray())

        val result = readerRepository.read(mockUri)
        assertThat(result).isInstanceOf(Result.Success::class.java)
    }

    @Test
    fun `read return Data`() = runBlockingTest {
        val uri = ""
        val mockUri = Mockito.mock(Uri::class.java)
        every { Uri.parse(uri) } returns mockUri

        every {
            this@LocalFileReaderRepositoryTest.context.contentResolver.openInputStream(mockUri)
        } returns ByteArrayInputStream(CsvTestData().simple.toByteArray())

        val result = readerRepository.read(mockUri)
        assertThat(
            (result as Result.Success).data?.rowList?.get(0)?.colList?.sameContentWith(
                CsvTestResultData().simple
            )
        )
    }

    @Test
    fun `read return Error`() = runBlockingTest {
        val uri = null
        val mockUri = Mockito.mock(Uri::class.java)
        every { Uri.parse(uri) } returns mockUri

        every {
            this@LocalFileReaderRepositoryTest.context.contentResolver.openInputStream(mockUri)
        } throws IllegalTokenException('K', "ref")

        val result = readerRepository.read(mockUri)
        assertThat(result).isInstanceOf(Result.Error::class.java)
    }

}