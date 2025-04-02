package br.campotech.common.recuperarsenha;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class ConviteLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    private Usuario usuario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @Temporal(TemporalType.TIMESTAMP)
    private Date usadoEm;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getUsadoEm() {
        return usadoEm;
    }

    public void setUsadoEm(Date usadoEm) {
        this.usadoEm = usadoEm;
    }

    public UUID getId() {
        return id;
    }
}
