package com.github.maikoncanut.mcpspringbatch.batchs.processors;

import com.github.maikoncanut.mcpspringbatch.models.Pessoa;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PessoaUpperCaseProcessor implements ItemProcessor<Pessoa, Pessoa> {

    @Override
    public Pessoa process(final Pessoa pessoa) throws Exception {

        final var response = new Pessoa();

        response.setId(pessoa.getId());
        response.setIdade(pessoa.getIdade());
        response.setNome(pessoa.getNome().toUpperCase());
        response.setSobreNome(pessoa.getSobreNome().toUpperCase());

        return response;
    }

}
