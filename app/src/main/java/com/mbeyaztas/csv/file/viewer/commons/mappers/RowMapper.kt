package com.mbeyaztas.csv.file.viewer.commons.mappers

import com.mbeyaztas.csv.file.csvcore.CsvFile

class RowMapper {

    fun csvFileToArray(csvFile: CsvFile): MutableList<Array<String>> {
        return csvFile.rowList.map { it.colList.toTypedArray() }.toMutableList()
    }

}