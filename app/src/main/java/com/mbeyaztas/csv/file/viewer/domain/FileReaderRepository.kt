package com.mbeyaztas.csv.file.viewer.domain

import android.net.Uri
import com.mbeyaztas.csv.file.csvcore.CsvFile
import com.mbeyaztas.csv.file.viewer.commons.utils.Result

interface FileReaderRepository {

    suspend fun read(uri: Uri): Result<CsvFile?>

}