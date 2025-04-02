package br.campotech.common.recuperarsenha;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface UsuarioDao extends JpaRepository<Usuario, UUID>{

    Usuario findByLogin(String email);

	List<Usuario> findAllByOrderByNome();

    @Modifying
    @Transactional
    @Query(value = "UPDATE usuario SET ativo = CASE WHEN ativo = true THEN false ELSE true END WHERE login = :login", nativeQuery = true)
    void inativar(@Param("login") String login);

	List<Usuario> findByLoginContainingIgnoreCase(String login);    
}
