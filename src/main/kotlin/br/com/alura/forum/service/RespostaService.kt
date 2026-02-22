package br.com.alura.forum.service

import br.com.alura.forum.dto.AtualizacaoRespostaForm
import br.com.alura.forum.dto.RespostaForm
import br.com.alura.forum.dto.RespostaView
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.RespostaFormMapper
import br.com.alura.forum.mapper.RespostaViewMapper
import br.com.alura.forum.repository.RespostaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class RespostaService(
    private val repository: RespostaRepository,
    private val respostaViewMapper: RespostaViewMapper,
    private val respostaFormMapper: RespostaFormMapper,
    private val notFoundMessage: String = "Resposta não encontrada!") {

    fun listar(
        idTopico: Long,
        paginacao: Pageable
    ): Page<RespostaView> {
        return repository.findByTopicoId(idTopico, paginacao).map { r ->
            respostaViewMapper.map(r)
        }
    }

    fun cadastrar(idTopico: Long, form: RespostaForm): RespostaView {
        val resposta = respostaFormMapper.map(form, idTopico)
        repository.save(resposta)
        return RespostaViewMapper().map(resposta)
    }

    fun atualizar(form: AtualizacaoRespostaForm): RespostaView {
        val resposta = repository.findById(form.id)
            .orElseThrow{NotFoundException(notFoundMessage)}
        resposta.mensagem = form.mensagem
        return RespostaViewMapper().map(resposta)
    }

    fun deletar(id: Long) {
        repository.deleteById(id)
    }
}