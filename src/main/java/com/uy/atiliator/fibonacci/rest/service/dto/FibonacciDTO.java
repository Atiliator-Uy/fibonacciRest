package com.uy.atiliator.fibonacci.rest.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.uy.atiliator.fibonacci.rest.domain.Fibonacci} entity.
 */
public class FibonacciDTO implements Serializable {
    
    private Long id;

    private Integer numeroFibonacci;

    private Integer enesimo;

    private Integer contadorRest;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroFibonacci() {
        return numeroFibonacci;
    }

    public void setNumeroFibonacci(Integer numeroFibonacci) {
        this.numeroFibonacci = numeroFibonacci;
    }

    public Integer getEnesimo() {
        return enesimo;
    }

    public void setEnesimo(Integer enesimo) {
        this.enesimo = enesimo;
    }

    public Integer getContadorRest() {
        return contadorRest;
    }

    public void setContadorRest(Integer contadorRest) {
        this.contadorRest = contadorRest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FibonacciDTO fibonacciDTO = (FibonacciDTO) o;
        if (fibonacciDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fibonacciDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FibonacciDTO{" +
            "id=" + getId() +
            ", numeroFibonacci=" + getNumeroFibonacci() +
            ", enesimo=" + getEnesimo() +
            ", contadorRest=" + getContadorRest() +
            "}";
    }
}
