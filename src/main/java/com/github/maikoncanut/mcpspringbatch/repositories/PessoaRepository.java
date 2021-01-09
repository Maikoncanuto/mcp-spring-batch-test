package com.github.maikoncanut.mcpspringbatch.repositories;

import com.github.maikoncanut.mcpspringbatch.models.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
