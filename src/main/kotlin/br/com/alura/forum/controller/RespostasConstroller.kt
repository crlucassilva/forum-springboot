package br.com.alura.forum.controller

import br.com.alura.forum.dto.AtualizacaoRespostaForm
import br.com.alura.forum.dto.RespostaForm
import br.com.alura.forum.dto.RespostaView
import br.com.alura.forum.service.RespostaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/topicos/{topicoId}/respostas")
class RespostasConstroller(private val service: RespostaService) {

    @PostMapping
    @Transactional
    fun cadastrar(
        @PathVariable topicoId:Long,
        @RequestBody @Valid form: RespostaForm,
        uriBuilder: UriComponentsBuilder): ResponseEntity<RespostaView> {
        val respostaView = service.cadastrar(topicoId, form)
        val uri = uriBuilder.path("/respostas/${respostaView.id}").build().toUri()
        return ResponseEntity.created(uri).body(respostaView)
    }

    @GetMapping
    fun listar(@PathVariable topicoId: Long): List<RespostaView> {
        return service.listar(topicoId)
    }

    @PutMapping
    @Transactional
    fun atualizar(@RequestBody @Valid form: AtualizacaoRespostaForm): ResponseEntity<RespostaView> {
        val respostaView = service.atualizar(form)
        return ResponseEntity.ok(respostaView)
    }

    @DeleteMapping("/{respostaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun deletar(@PathVariable respostaId: Long) {
        service.deletar(respostaId)
    }
}