package com.uy.atiliator.fibonacci.rest.service.mapper;


import com.uy.atiliator.fibonacci.rest.domain.*;
import com.uy.atiliator.fibonacci.rest.service.dto.FibonacciDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fibonacci} and its DTO {@link FibonacciDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FibonacciMapper extends EntityMapper<FibonacciDTO, Fibonacci> {



    default Fibonacci fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fibonacci fibonacci = new Fibonacci();
        fibonacci.setId(id);
        return fibonacci;
    }
}
