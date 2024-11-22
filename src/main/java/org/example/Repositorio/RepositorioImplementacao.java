package org.example.Repositorio;

import org.example.entities._BaseEntity;

import java.util.ArrayList;

public abstract class RepositorioImplementacao<T extends _BaseEntity> implements RepositorioGenerico<T> {
    ArrayList<T> lista = new ArrayList<>();

    @Override
    public void adicionar(T entidade) {
        lista.add(entidade);
    }

    @Override
    public ArrayList<T> exibir() {
        return lista;
    }

    @Override
    public void editar(T entidade, int id) {
        T entidadeAtualizado = lista.stream().filter(x -> x.getId() == entidade.getId()).findFirst().get();
        lista.remove(entidadeAtualizado);
        lista.add(entidade);
    }

    @Override
    public void excluir(int id) {
        lista.remove(id);
    }

    public RepositorioImplementacao(ArrayList<T> lista) {
        this.lista = lista;
    }
}
