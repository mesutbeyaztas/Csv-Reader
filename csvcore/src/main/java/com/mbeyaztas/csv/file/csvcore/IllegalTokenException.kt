package com.mbeyaztas.csv.file.csvcore

class IllegalTokenException(next: Char, ref: String) : IllegalArgumentException(
    "Illegal token: $next while parsing $ref"
)
