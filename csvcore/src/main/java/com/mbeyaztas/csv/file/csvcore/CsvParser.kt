package com.mbeyaztas.csv.file.csvcore

import java.nio.CharBuffer

class CsvParser(
    private var separator: Char,
    var file: CsvFile
) {

    companion object {
        const val QUOTE = '"'
        const val SPACE = ' '
        const val TAB = '\t'
        const val COMMA = ','
        const val LINE_FEED = '\n'
        const val CARRIAGE_RETURN = '\r'
    }

    enum class State {
        FIELD_START, NORMAL, QUOTED, POST_QUOTED
    }

    var buffer: CharBuffer = CharBuffer.allocate(256)
    private var parseState = State.FIELD_START

    fun parse() {
        while (buffer.remaining() > 0) {
            val next = buffer.get()
            when (parseState) {
                State.FIELD_START ->
                    // any whitespace or '\n' '\r' before a field is ignore
                    when (next) {
                        SPACE,
                        TAB,
                        LINE_FEED,
                        CARRIAGE_RETURN -> {
                            continue
                        }
                        QUOTE -> {
                            parseState = State.QUOTED
                        }
                        separator -> {
                            file.newEmptyField()
                        }
                        else -> {
                            file.append(next)
                            parseState = State.NORMAL
                        }
                    }
                State.NORMAL ->
                    // complete normal field
                    when (next) {
                        separator -> {
                            file.newField(false)
                            parseState = State.FIELD_START
                        }
                        LINE_FEED,
                        CARRIAGE_RETURN -> {
                            file.newField(true)
                            parseState = State.FIELD_START
                        }
                        QUOTE -> {
                            throw IllegalTokenException(next, buffer.toString())
                        }
                        else -> file.append(next)
                    }
                State.QUOTED ->
                    // find a quote pair
                    if (next == QUOTE) parseState = State.POST_QUOTED
                    else file.append(next)
                State.POST_QUOTED ->
                    // in a post quote, check if it is an escape or a field end
                    when (next) {
                        QUOTE -> {
                            file.append(next)
                            parseState = State.QUOTED
                        }
                        separator -> {
                            file.newField(false)
                            parseState = State.FIELD_START
                        }
                        LINE_FEED,
                        CARRIAGE_RETURN -> {
                            file.newField(true)
                            parseState = State.FIELD_START
                        }
                    }
            }
        }

        if (buffer.remaining() == 0) {
            file.newField(false)
        }

        // stay current State, and prepare for next
        buffer.clear()
    }


}