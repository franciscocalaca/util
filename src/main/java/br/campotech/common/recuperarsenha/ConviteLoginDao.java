package br.campotech.common.recuperarsenha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ConviteLoginDao extends JpaRepository<ConviteLogin, UUID> {
}
