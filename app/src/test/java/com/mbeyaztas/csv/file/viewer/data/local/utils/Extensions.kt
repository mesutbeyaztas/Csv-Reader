package com.mbeyaztas.csv.file.viewer.data.local.utils

infix fun <T> Collection<T>.sameContentWith(collection: Collection<T>?) =
    collection?.let { this.size == it.size && this.containsAll(it) }
