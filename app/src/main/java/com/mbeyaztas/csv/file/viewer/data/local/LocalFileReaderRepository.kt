package com.mbeyaztas.csv.file.viewer.data.local

import android.content.Context
import android.net.Uri
import com.mbeyaztas.csv.file.csvcore.CsvFile
import com.mbeyaztas.csv.file.csvcore.CsvReader
import com.mbeyaztas.csv.file.viewer.R
import com.mbeyaztas.csv.file.viewer.commons.utils.Result
import com.mbeyaztas.csv.file.viewer.domain.FileReaderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalFileReaderRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val context: Context
) : FileReaderRepository {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun read(uri: Uri): Result<CsvFile?> {
        return withContext(dispatcher) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                Result.Success(inputStream?.let {
                    CsvReader(it).parse()
                })
            } catch (e: Exception) {
                Result.Error(e.message ?: context.getString(R.string.error_file_read))
            }
        }
    }

}