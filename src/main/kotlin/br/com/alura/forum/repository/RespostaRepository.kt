package br.com.alura.forum.repository

import br.com.alura.forum.model.Resposta
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface RespostaRepository: JpaRepository<Resposta, Long> {

    fun findByTopicoId(topicoId: Long, paginacao: Pageable): Page<Resposta>
}