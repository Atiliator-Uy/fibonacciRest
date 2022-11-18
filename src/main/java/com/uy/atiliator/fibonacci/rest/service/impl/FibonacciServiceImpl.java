package com.uy.atiliator.fibonacci.rest.service.impl;

import com.uy.atiliator.fibonacci.rest.service.FibonacciService;
import com.uy.atiliator.fibonacci.rest.domain.Fibonacci;
import com.uy.atiliator.fibonacci.rest.repository.FibonacciRepository;
import com.uy.atiliator.fibonacci.rest.service.dto.FibonacciDTO;
import com.uy.atiliator.fibonacci.rest.service.mapper.FibonacciMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Fibonacci}.
 */
@Service
@Transactional
public class FibonacciServiceImpl implements FibonacciService {

    private final Logger log = LoggerFactory.getLogger(FibonacciServiceImpl.class);

    private final FibonacciRepository fibonacciRepository;

    private final FibonacciMapper fibonacciMapper;

    public FibonacciServiceImpl(FibonacciRepository fibonacciRepository, FibonacciMapper fibonacciMapper) {
        this.fibonacciRepository = fibonacciRepository;
        this.fibonacciMapper = fibonacciMapper;
    }

    /**
     * Save a fibonacci.
     *
     * @param fibonacciDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FibonacciDTO save(FibonacciDTO fibonacciDTO) {
        log.debug("Request to save Fibonacci : {}", fibonacciDTO);
        Fibonacci fibonacci = fibonacciMapper.toEntity(fibonacciDTO);
        fibonacci = fibonacciRepository.save(fibonacci);
        return fibonacciMapper.toDto(fibonacci);
    }

    /**
     * Get all the fibonaccis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FibonacciDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fibonaccis");
        return fibonacciRepository.findAll(pageable)
            .map(fibonacciMapper::toDto);
    }

    /**
     * Get one fibonacci by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FibonacciDTO> findOne(Long id) {
        log.debug("Request to get Fibonacci : {}", id);
        return fibonacciRepository.findById(id)
            .map(fibonacciMapper::toDto);
    }

    /**
     * Delete the fibonacci by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fibonacci : {}", id);
        fibonacciRepository.deleteById(id);
    }
}
