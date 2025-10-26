package br.com.alura.forum.service

import br.com.alura.forum.dto.RespostaForm
import br.com.alura.forum.mapper.RespostaFormMapper
import br.com.alura.forum.model.Resposta
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class RespostaService(
    private var respostas: List<Resposta>,
    private val topicoService: TopicoService,
    private val respostaFormMapper: RespostaFormMapper) {

    fun listar(idTopico: Long): List<Resposta> {
        return respostas.stream().filter { r ->
            r.topico.id == idTopico
        }.collect(Collectors.toList())
    }

    fun cadastrar(idTopico: Long, form: RespostaForm) {
        val resposta = respostaFormMapper.map(form)
        resposta.id = respostas.size.toLong() + 1
        resposta.topico = topicoService.obterTopico(idTopico)
        respostas = respostas.plus(resposta)
    }
}