package com.uy.atiliator.fibonacci.rest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Fibonacci.
 */
@Entity
@Table(name = "fibonacci")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fibonacci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_fibonacci")
    private Integer numeroFibonacci;

    @Column(name = "enesimo")
    private Integer enesimo;

    @Column(name = "contador_rest")
    private Integer contadorRest;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroFibonacci() {
        return numeroFibonacci;
    }

    public Fibonacci numeroFibonacci(Integer numeroFibonacci) {
        this.numeroFibonacci = numeroFibonacci;
        return this;
    }

    public void setNumeroFibonacci(Integer numeroFibonacci) {
        this.numeroFibonacci = numeroFibonacci;
    }

    public Integer getEnesimo() {
        return enesimo;
    }

    public Fibonacci enesimo(Integer enesimo) {
        this.enesimo = enesimo;
        return this;
    }

    public void setEnesimo(Integer enesimo) {
        this.enesimo = enesimo;
    }

    public Integer getContadorRest() {
        return contadorRest;
    }

    public Fibonacci contadorRest(Integer contadorRest) {
        this.contadorRest = contadorRest;
        return this;
    }

    public void setContadorRest(Integer contadorRest) {
        this.contadorRest = contadorRest;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fibonacci)) {
            return false;
        }
        return id != null && id.equals(((Fibonacci) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fibonacci{" +
            "id=" + getId() +
            ", numeroFibonacci=" + getNumeroFibonacci() +
            ", enesimo=" + getEnesimo() +
            ", contadorRest=" + getContadorRest() +
            "}";
    }
}
