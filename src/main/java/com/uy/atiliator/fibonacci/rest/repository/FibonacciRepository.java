package com.uy.atiliator.fibonacci.rest.repository;

import com.uy.atiliator.fibonacci.rest.domain.Fibonacci;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Fibonacci entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FibonacciRepository extends JpaRepository<Fibonacci, Long> {
}
