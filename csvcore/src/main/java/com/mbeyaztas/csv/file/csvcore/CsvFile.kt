package com.mbeyaztas.csv.file.csvcore

import java.util.*

class CsvFile {

    private var currentRow = -1
    var rowList: MutableList<Row> = ArrayList()
    var buffer = StringBuffer()

    fun newEmptyField() {
        val row = getCurrentRow()
        row.add("")
    }

    fun append(next: Char) {
        buffer.append(next)
    }

    private fun clear() {
        buffer = StringBuffer()
    }

    fun newField(increaseRowNext: Boolean) {
        val row = getCurrentRow()
        row.add(buffer.toString())
        clear()
        if (increaseRowNext) currentRow++
    }

    fun size(): Int {
        return rowList.size
    }

    fun getColumnSize(): Int {
        return rowList[0].colList.size
    }

    private fun getCurrentRow(): Row {
        checkRow()
        return rowList[currentRow]
    }

    private fun checkRow() {
        if (currentRow < 0) currentRow = 0
        while (rowList.size <= currentRow) rowList.add(Row())
    }

    class Row {
        var colList: MutableList<String> = ArrayList()

        fun add(col: String) {
            colList.add(col)
        }
    }
}