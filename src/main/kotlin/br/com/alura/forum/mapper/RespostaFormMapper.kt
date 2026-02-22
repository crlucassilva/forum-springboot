package br.com.alura.forum.mapper

import br.com.alura.forum.dto.RespostaForm
import br.com.alura.forum.model.Resposta
import br.com.alura.forum.service.TopicoService
import br.com.alura.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class RespostaFormMapper(
    val usuarioService: UsuarioService,
    val topicoService: TopicoService
) {
   fun map(t: RespostaForm, idTopico: Long): Resposta {
        return Resposta(
            mensagem = t.mensagem,
            autor = usuarioService.buscarPorId(t.idAutor),
            topico = topicoService.obterTopico(idTopico),
            solucao = t.solucao)
    }
}