package br.com.gusto.remotebacencsvreader.controller

import br.com.gusto.remotebacencsvreader.model.ParticipantePIX
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CSVFileReader {
    fun lerArquivoCSV(): List<ParticipantePIX> {
        val participantes = ArrayList<ParticipantePIX>()

        val dataArquivo = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd"))

        val url = "https://www.bcb.gov.br/content/estabilidadefinanceira/spi/participantes-spi-$dataArquivo.csv"

        val csvFile = URL(url)

        try {
            val bufferedReader = BufferedReader(InputStreamReader(csvFile.openStream()))

            val csvParser = CSVParserBuilder()
                .withSeparator(';')
                .build()

            val csvReader = CSVReaderBuilder(bufferedReader)
                .withCSVParser(csvParser)
                .build()

            val linhas = csvReader.readAll()

            linhas.drop(1).forEach { participante ->
                val participantePIX = ParticipantePIX(
                    participante[0],
                    participante[1],
                    participante[2],
                    participante[3],
                    participante[4]
                )
                participantes.add(participantePIX)
            }

            csvReader.close()
            bufferedReader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return participantes
    }
}