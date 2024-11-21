package org.example.repositories;

import org.example.entities.Cadastro;

public class CadastroRepository extends _BaseRepositoryImpl<Cadastro>{



    public CadastroRepository(Class<Cadastro> cadastroClass) {
        super(cadastroClass);
    }
}
