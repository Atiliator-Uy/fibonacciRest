package com.uy.atiliator.fibonacci.rest.service;

import com.uy.atiliator.fibonacci.rest.service.dto.FibonacciDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.uy.atiliator.fibonacci.rest.domain.Fibonacci}.
 */
public interface FibonacciService {

    /**
     * Save a fibonacci.
     *
     * @param fibonacciDTO the entity to save.
     * @return the persisted entity.
     */
    FibonacciDTO save(FibonacciDTO fibonacciDTO);

    /**
     * Get all the fibonaccis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FibonacciDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fibonacci.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FibonacciDTO> findOne(Long id);

    /**
     * Delete the "id" fibonacci.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
