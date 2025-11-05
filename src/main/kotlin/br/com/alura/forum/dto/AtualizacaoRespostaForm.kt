package br.com.alura.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class AtualizacaoRespostaForm (
    @field:NotNull
    val id: Long,
    @field:NotEmpty
    val mensagem: String
)
