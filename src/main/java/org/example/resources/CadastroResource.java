package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.Repositorio.RepositorioCadastro;
import org.example.entities.ContaUsuario;
import org.example.entities.Login;
import org.example.services.CadastroService;

import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("cadastro")
public class CadastroResource {
    private RepositorioCadastro repositorioCadastro;
    private CadastroService cadastroService;

    public CadastroResource() {
        this.repositorioCadastro = new RepositorioCadastro();
        this.cadastroService = new CadastroService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ContaUsuario> getAllCadastro() {
        return repositorioCadastro.exibir();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCadastro(ContaUsuario contaUsuario) {
        if (contaUsuario.getNome() == null || contaUsuario.getNome().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("O nome é obrigatório").build();
        }
        if (contaUsuario.getEmail() == null || contaUsuario.getEmail().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("O email é obrigatório").build();
        }
        try {
            cadastroService.Criar(contaUsuario);
            return Response.status(Response.Status.CREATED).entity("Cadastro criado com sucesso").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCadastro(@PathParam("id") int id, ContaUsuario contaUsuario) {
        try {
            cadastroService.Atualizar(contaUsuario, id);
            return Response.status(Response.Status.OK).entity("Cadastro atualizado com sucesso").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCadastro(@PathParam("id") int id) {
        ContaUsuario contaUsuario = repositorioCadastro.buscarPorId(id);
        if (contaUsuario != null) {
            repositorioCadastro.excluir(id);
            return Response.status(Response.Status.OK).entity("Cadastro excluído com sucesso").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Cadastro não encontrado").build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Login loginRequest) {
        try {
            ContaUsuario contaUsuario = cadastroService.authenticate(loginRequest.getEmail(), loginRequest.getSenha());
            return Response.status(Response.Status.OK).entity(contaUsuario).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }
}
