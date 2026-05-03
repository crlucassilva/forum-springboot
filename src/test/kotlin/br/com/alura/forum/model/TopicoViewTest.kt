package br.com.alura.forum.model

import br.com.alura.forum.dto.TopicoView
import java.time.LocalDate
import java.time.LocalDateTime

object TopicoViewTest {
    fun build() = TopicoView(
        id = 1,
        titulo = "Titulo Test",
        mensagem = "Mensagem Test",
        status = StatusTopico.NAO_RESPONDIDA,
        dataCriacao = LocalDateTime.now(),
        dataAlteracao = LocalDate.now()
    )
}