package br.com.alura.forum.dto

import jakarta.validation.constraints.NotNull

data class RespostaForm (
    @field:NotNull
    val mensagem: String,
    val idAutor: Long,
    val solucao: Boolean
)
