package org.superbiz.moviefun

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.databind.ObjectReader

import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

object CsvUtils {

    @Throws(IOException::class)
    fun <T> readFromCsv(objectReader: ObjectReader, inputStream: InputStream): List<T> {
        val results = ArrayList<T>()

        val iterator = objectReader.readValues<T>(inputStream)

        while (iterator.hasNext()) {
            results.add(iterator.nextValue())
        }

        return results
    }
}
