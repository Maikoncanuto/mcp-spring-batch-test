package com.github.maikoncanut.mcpspringbatch.controllers;

import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import com.github.maikoncanut.mcpspringbatch.models.Pessoa;
import com.github.maikoncanut.mcpspringbatch.services.PessoaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<Pessoa>> findAll() {
        return ok(pessoaService.findAll());
    }

}
