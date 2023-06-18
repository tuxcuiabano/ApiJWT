/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ficdev.apijwt.resources;

import br.edu.ficdev.apijwt.Usuario;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 *
 * @author pedroclarindodasilvaneto
 */
@Path("/login")
public class LoginResource {


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Usuario usuario) {
        try {
            if (usuario.getUsuario().equals("ficdev@risc.com.br")
                    && usuario.getSenha().equals("1234")) {
                String jwtToken = Jwts.builder()
                        .setSubject(usuario.getUsuario())
                        .setIssuer("localhost:8081")
                        .setIssuedAt(new Date())
                        .setExpiration(
                                Date.from(
                                        LocalDateTime.now().plusMinutes(15L)
                                                .atZone(ZoneId.systemDefault())
                                                .toInstant()))
                        .compact();

                return Response.status(Response.Status.OK).entity(jwtToken).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Usuário e/ou senha inválidos").build();
            }
        } catch (Exception ex) {
            return Response.status(
                    Response.Status.INTERNAL_SERVER_ERROR
            ).entity(ex.getMessage())
                    .build();
        }
    }
}
