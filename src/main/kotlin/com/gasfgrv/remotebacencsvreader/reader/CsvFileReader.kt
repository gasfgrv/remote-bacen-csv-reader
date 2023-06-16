package com.gasfgrv.remotebacencsvreader.reader

import com.gasfgrv.remotebacencsvreader.model.ParticipantePix
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Value

class CsvFileReader {

    @Value("\${bacen.url}")
    private lateinit var url: String

    private lateinit var participantesPix: List<ParticipantePix>

    private fun popularArquivo(): List<ParticipantePix> {
        val dataArquivo = LocalDateTime
            .now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd"))

        val csvFile = URL("$url$dataArquivo.csv")
        val inputStreamReader = InputStreamReader(csvFile.openStream())
        val bufferedReader = BufferedReader(inputStreamReader)

        val csvParser = CSVParserBuilder()
            .withSeparator(';')
            .build()

        val csvReader = CSVReaderBuilder(bufferedReader)
            .withCSVParser(csvParser)
            .build()

        val participantes = csvReader
            .readAll()
            .drop(1)
            .map { participante ->
                ParticipantePix(participante[0], participante[1], participante[2], participante[3], participante[4])
            }

        csvReader.close()
        bufferedReader.close()
        inputStreamReader.close()

        return participantes
    }

    fun getParticipantes(): List<ParticipantePix> {
        this.participantesPix = popularArquivo()
        return this.participantesPix.toList()
    }

}
