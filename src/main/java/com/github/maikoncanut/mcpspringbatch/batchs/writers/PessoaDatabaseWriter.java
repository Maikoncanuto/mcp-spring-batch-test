package com.github.maikoncanut.mcpspringbatch.batchs.writers;

import java.util.List;

import com.github.maikoncanut.mcpspringbatch.models.Pessoa;
import com.github.maikoncanut.mcpspringbatch.repositories.PessoaRepository;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class PessoaDatabaseWriter implements ItemWriter<Pessoa> {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public void write(final List<? extends Pessoa> pessoas) throws Exception {
        pessoaRepository.saveAll(pessoas);
    }

}
