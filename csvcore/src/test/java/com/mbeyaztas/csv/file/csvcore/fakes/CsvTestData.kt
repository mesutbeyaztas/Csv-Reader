package com.mbeyaztas.csv.file.csvcore.fakes

import com.mbeyaztas.csv.file.csvcore.CsvParser

class CsvTestData {
    val simple = "1, 2, 3, 4, 5"
    val quote = "1,\"2\", 3"
    val space = "test, test, test" + CsvParser.SPACE
    val tab = "test, test, test" + CsvParser.TAB
    val comma = "test, test, test" + CsvParser.COMMA
    val lineFeed = "test, test, test" + CsvParser.LINE_FEED
    val carriageReturn = "test, test, test" + CsvParser.CARRIAGE_RETURN
}