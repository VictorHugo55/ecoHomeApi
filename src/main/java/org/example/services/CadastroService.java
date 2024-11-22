package org.example.services;

import org.example.Log.Loggable;
import org.example.Repositorio.RepositorioCadastro;
import org.example.entities.ContaUsuario;

public class CadastroService implements Loggable<CadastroService> {

    private RepositorioCadastro repositorioCadastro = new RepositorioCadastro();

    public CadastroService(){
        RepositorioCadastro repositorioCadastro = new RepositorioCadastro();
    }

    public boolean Criar(ContaUsuario contaUsuario){
        java.util.Map<Boolean, String> validation = contaUsuario.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                repositorioCadastro.adicionar(contaUsuario);
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void Atualizar(ContaUsuario contaUsuario, int id) {
        try {
            java.util.Map<Boolean, String> validation = contaUsuario.validate();
            if (validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                repositorioCadastro.editar(contaUsuario, id);
                logInfo("Cadastro atualizado com sucesso");
            }
        } catch (Exception e) {
            logErro("Erro ao atualizar cadastro: " + e.getMessage());
            throw e;
        }
    }


    public ContaUsuario authenticate(String email, String senha) {
        ContaUsuario contaUsuario = repositorioCadastro.acharPorEmail(email);
        if (contaUsuario != null && contaUsuario.getSenha().equals(senha)) {
            return contaUsuario;
        }
        throw new IllegalArgumentException("Credenciais inválidas");
    }
}
