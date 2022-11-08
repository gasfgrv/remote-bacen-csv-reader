package br.com.gusto.remotebacencsvreader.model

data class ParticipantePIX(
    var codIspb: String,
    var nomeParticipante: String,
    var tipoParticipante: String,
    var codBanco: String,
    var dataInclusao: String
)