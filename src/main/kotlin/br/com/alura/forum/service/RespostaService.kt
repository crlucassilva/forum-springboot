package br.com.alura.forum.service

import br.com.alura.forum.dto.AtualizacaoRespostaForm
import br.com.alura.forum.dto.RespostaForm
import br.com.alura.forum.dto.RespostaView
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.RespostaFormMapper
import br.com.alura.forum.mapper.RespostaViewMapper
import br.com.alura.forum.repository.RespostaRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class RespostaService(
    private val repository: RespostaRepository,
    private val topicoService: TopicoService,
    private val respostaViewMapper: RespostaViewMapper,
    private val respostaFormMapper: RespostaFormMapper,
    private val notFoundMessage: String = "Resposta não encontrada!") {

    fun listar(idTopico: Long): List<RespostaView> {
        return repository.findAll().stream().map { r ->
            respostaViewMapper.map(r)
        }.collect(Collectors.toList())
    }

    fun cadastrar(idTopico: Long, form: RespostaForm): RespostaView {
        val resposta = respostaFormMapper.map(form)
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