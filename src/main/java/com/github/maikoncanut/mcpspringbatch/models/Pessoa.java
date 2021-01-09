package com.github.maikoncanut.mcpspringbatch.models;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Entity
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 4936099778969405376L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String nome;
    
    private String sobreNome;
    
    private Integer idade;

}
