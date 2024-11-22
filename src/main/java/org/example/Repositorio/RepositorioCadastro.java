package org.example.Repositorio;

import org.example.Log.Loggable;
import org.example.entities.ContaUsuario;
import org.example.infraestrutura.DataBaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepositorioCadastro implements RepositorioGenerico<ContaUsuario>, Loggable<ContaUsuario> {
    DataBaseConfig connection = new DataBaseConfig();
    @Override
    public void adicionar(ContaUsuario contaUsuario) {
        String sql = "INSERT INTO Cadastro(nome,email,senha) VALUES (?,?,?)";

        try(Connection conn = connection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, contaUsuario.getNome());
            stmt.setString(2, contaUsuario.getEmail());
            stmt.setString(3, contaUsuario.getSenha());

            stmt.executeUpdate();
            logInfo("Usuário cadastrado com sucesso");
        } catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao cadastrar");
        }

    }

    @Override
    public ArrayList<ContaUsuario> exibir() {
        String sql = "SELECT * FROM Cadastro";
        ArrayList<ContaUsuario> contaUsuarios = new ArrayList<>();

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ContaUsuario contaUsuario = new ContaUsuario();
                contaUsuario.setId(rs.getInt("id"));
                contaUsuario.setNome(rs.getString("nome"));
                contaUsuario.setEmail(rs.getString("email"));
                contaUsuario.setSenha(rs.getString("senha")); // Adicionado a senha
                contaUsuarios.add(contaUsuario);
            }
            logInfo("Listagem de usuários realizada com sucesso");

        } catch (SQLException e) {
            e.printStackTrace();
            logInfo("Erro ao listar usuário");
        }

        return contaUsuarios;
    }


    @Override
    public void editar(ContaUsuario contaUsuario, int id) {
        String sql = "UPDATE Cadastro SET nome = ?, email = ?, senha = ? WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contaUsuario.getNome());
            stmt.setString(2, contaUsuario.getEmail());
            stmt.setString(3, contaUsuario.getSenha());
            stmt.setInt(4, id); // Corrigido para usar o ID do parâmetro

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                logInfo("Usuário com ID " + id + " atualizado com sucesso");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logInfo("Erro ao atualizar usuário com ID " + id);
        }
    }



    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM Cadastro WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1,id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0){
                logInfo("Usuário removido com sucesso");
            }

        } catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao deletar");
        }

    }

    @Override
    public ContaUsuario buscarPorId(int id) {
        String sql = "SELECT * FROM Cadastro WHERE id = ?";
        ContaUsuario contaUsuario = null;

        try(Connection conn = connection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                contaUsuario = new ContaUsuario();
                contaUsuario.setId(rs.getInt("id"));
                contaUsuario.setNome(rs.getString("nome"));
                contaUsuario.setEmail(rs.getString("email"));
                contaUsuario.setSenha(rs.getString("senha"));
            }
            return contaUsuario;

        } catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro na busca por id");
        }

        return contaUsuario;
    }

    public ContaUsuario acharPorEmail(String email) {
        String sql = "SELECT * FROM Cadastro WHERE email = ?";
        ContaUsuario contaUsuario = null;

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                contaUsuario = new ContaUsuario();
                contaUsuario.setId(rs.getInt("id"));
                contaUsuario.setNome(rs.getString("nome"));
                contaUsuario.setEmail(rs.getString("email"));
                contaUsuario.setSenha(rs.getString("senha"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            logInfo("Erro ao buscar por email");
        }

        return contaUsuario;
    }
}
