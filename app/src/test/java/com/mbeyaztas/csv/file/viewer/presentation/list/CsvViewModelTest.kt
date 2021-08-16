package com.mbeyaztas.csv.file.viewer.presentation.list

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mbeyaztas.csv.file.csvcore.CsvFile
import com.mbeyaztas.csv.file.viewer.commons.utils.ResourceProvider
import com.mbeyaztas.csv.file.viewer.commons.utils.Result
import com.mbeyaztas.csv.file.viewer.domain.FileReaderRepository
import com.mbeyaztas.csv.file.viewer.presentation.list.utils.CoroutineTestRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class CsvViewModelTest {

    private lateinit var viewModel: CsvViewModel

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val fileReaderRepository: FileReaderRepository = mockk()
    private var resourceProvider: ResourceProvider = mockk()

    private val uriMock = Mockito.mock(Uri::class.java)

    @Before
    fun init() {
        viewModel = CsvViewModel(
            coroutineTestRule.testDispatcher, fileReaderRepository, resourceProvider
        )
        mockkStatic(Uri::class)
    }

    @Test
    fun `fetch data return loading`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            every { Uri.parse(any()) } returns null
            every { resourceProvider.getString(any()) } returns ""

            val list = mutableListOf<Result<CsvFile?>>()
            viewModel.csvData.observeForever { list.add(it) }

            viewModel.readCsv("")

            assertThat(list[0]).isInstanceOf(Result.Loading::class.java)
        }

    @Test
    fun `fetch data return error`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            val errorUri = "error uri"

            every { Uri.parse(errorUri) } returns null
            every { resourceProvider.getString(any()) } returns ""

            val list = mutableListOf<Result<CsvFile?>>()
            viewModel.csvData.observeForever { list.add(it) }

            viewModel.readCsv(errorUri)

            assertThat(list[1]).isInstanceOf(Result.Error::class.java)
        }

    @Test
    fun `fetch data return success`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            val csvFile = CsvFile()

            every { Uri.parse(any()) } returns uriMock
            coEvery { fileReaderRepository.read(any()) } returns Result.Success(csvFile)

            val list = mutableListOf<Result<CsvFile?>>()
            viewModel.csvData.observeForever { list.add(it) }

            viewModel.readCsv("")

            assertThat(list[1]).isInstanceOf(Result.Success::class.java)
        }

    @After
    fun finish() {
        unmockkStatic(Uri::class)
    }

}