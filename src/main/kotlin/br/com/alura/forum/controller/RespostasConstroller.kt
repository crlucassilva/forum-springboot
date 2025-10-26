package br.com.alura.forum.controller

import br.com.alura.forum.dto.RespostaForm
import br.com.alura.forum.model.Resposta
import br.com.alura.forum.service.RespostaService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/topicos/{id}/respostas")
class RespostasConstroller(private val service: RespostaService) {

    @PostMapping
    fun cadastrar(@PathVariable id:Long, @RequestBody @Valid dto: RespostaForm) {
        service.cadastrar(id, dto)
    }

    @GetMapping
    fun listar(@PathVariable id: Long): List<Resposta> {
        return service.listar(id)
    }
}