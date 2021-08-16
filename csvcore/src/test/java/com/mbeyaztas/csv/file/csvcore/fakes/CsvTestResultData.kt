package com.mbeyaztas.csv.file.csvcore.fakes

import com.mbeyaztas.csv.file.csvcore.CsvParser

class CsvTestResultData {
    val simple = arrayListOf("1", "2", "3", "4", "5")
    val quote = arrayListOf("1", "2", "3")
    val space = arrayListOf("test", "test", "test")
    val tab = "test, test, test" + CsvParser.TAB
    val comma = "test, test, test" + CsvParser.COMMA
    val lineFeed = "test, test, test" + CsvParser.LINE_FEED
    val carriageReturn = "test, test, test" + CsvParser.CARRIAGE_RETURN
}