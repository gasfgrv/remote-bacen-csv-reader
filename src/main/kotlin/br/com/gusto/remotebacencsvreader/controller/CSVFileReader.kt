package br.com.gusto.remotebacencsvreader.controller

import br.com.gusto.remotebacencsvreader.model.ParticipantePIX

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CSVFileReader {
    fun lerArquivoCSV(): List<ParticipantePIX> {
        val participantes = ArrayList<ParticipantePIX>()

        try {
            val dataArquivo = LocalDateTime
                    .now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd"))

            val csvFile = URL("https://www.bcb.gov.br/content/estabilidadefinanceira/spi/participantes-spi-$dataArquivo.csv")

            val bufferedReader = BufferedReader(InputStreamReader(csvFile.openStream()))

            val csvParser = CSVParserBuilder()
                    .withSeparator(';')
                    .build()

            val csvReader = CSVReaderBuilder(bufferedReader)
                    .withCSVParser(csvParser)
                    .build()

            val linhas = csvReader.readAll()

            linhas.drop(1)
                    .forEach{
                        val participante = ParticipantePIX(
                                it[0],
                                it[1],
                                it[2],
                                it[3],
                                it[4]
                        )
                        participantes.add(participante)
                    }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return participantes
    }
}