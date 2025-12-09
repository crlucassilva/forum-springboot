package br.com.alura.forum.service

import br.com.alura.forum.dto.AtualizacaoRespostaForm
import br.com.alura.forum.dto.RespostaForm
import br.com.alura.forum.dto.RespostaView
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.RespostaFormMapper
import br.com.alura.forum.mapper.RespostaViewMapper
import br.com.alura.forum.model.Resposta
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class RespostaService(
    private var respostas: List<Resposta>,
    private val topicoService: TopicoService,
    private val respostaViewMapper: RespostaViewMapper,
    private val respostaFormMapper: RespostaFormMapper,
    private val notFoundMessage: String = "Resposta não encontrada!") {

    fun listar(idTopico: Long): List<RespostaView> {
        return respostas.stream().map { r ->
            respostaViewMapper.map(r)
        }.collect(Collectors.toList())
    }

    fun cadastrar(idTopico: Long, form: RespostaForm): RespostaView {
        val resposta = respostaFormMapper.map(form)
        resposta.id = respostas.size.toLong() + 1
        resposta.topico = topicoService.obterTopico(idTopico)
        respostas = respostas.plus(resposta)
        return RespostaViewMapper().map(resposta)
    }

    fun atualizar(form: AtualizacaoRespostaForm): RespostaView {
        val resposta = respostas.stream().filter { r ->
            r.id == form.id
        }.findFirst().orElseThrow{NotFoundException(notFoundMessage)}
        val respostaAtualizada = Resposta(
            id = form.id,
            mensagem = form.mensagem,
            dataCriacao = resposta.dataCriacao,
            autor = resposta.autor,
            topico = resposta.topico,
            solucao = resposta.solucao
        )
        respostas = respostas.minus(resposta).plus(respostaAtualizada)
        return RespostaViewMapper().map(respostaAtualizada)
    }

    fun deletar(id: Long) {
        val resposta = respostas.stream().filter { r ->
            r.id == id
        }.findFirst().orElseThrow{ NotFoundException(notFoundMessage) }
        respostas = respostas.minus(resposta)
    }
}