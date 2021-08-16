package com.mbeyaztas.csv.file.viewer.presentation.list

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbeyaztas.csv.file.csvcore.CsvFile
import com.mbeyaztas.csv.file.viewer.R
import com.mbeyaztas.csv.file.viewer.commons.utils.ResourceProvider
import com.mbeyaztas.csv.file.viewer.commons.utils.Result
import com.mbeyaztas.csv.file.viewer.domain.FileReaderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CsvViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val fileRepository: FileReaderRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _csvData = MutableLiveData<Result<CsvFile?>>()
    val csvData: LiveData<Result<CsvFile?>> = _csvData

    fun readCsv(uriText: String) {
        viewModelScope.launch(dispatcher) {
            _csvData.postValue(Result.Loading)
            val uri = Uri.parse(uriText)
            if (uri != null) {
                when (val resource = fileRepository.read(uri)) {
                    is Result.Success ->
                        _csvData.postValue(Result.Success(resource.data))
                    is Result.Error ->
                        _csvData.postValue(Result.Error(resource.message))
                }
            } else {
                _csvData.postValue(Result.Error(resourceProvider.getString(R.string.error_loading_file)))
            }
        }
    }

}