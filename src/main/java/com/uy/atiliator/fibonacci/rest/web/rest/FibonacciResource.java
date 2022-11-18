package com.uy.atiliator.fibonacci.rest.web.rest;

import com.uy.atiliator.fibonacci.rest.service.FibonacciService;
import com.uy.atiliator.fibonacci.rest.web.rest.errors.BadRequestAlertException;
import com.uy.atiliator.fibonacci.rest.service.dto.FibonacciDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.uy.atiliator.fibonacci.rest.domain.Fibonacci}.
 */
@RestController
@RequestMapping("/api")
public class FibonacciResource {

    private final Logger log = LoggerFactory.getLogger(FibonacciResource.class);

    private static final String ENTITY_NAME = "fibonacciRestFibonacci";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FibonacciService fibonacciService;

    public FibonacciResource(FibonacciService fibonacciService) {
        this.fibonacciService = fibonacciService;
    }

    /**
     * {@code POST  /fibonaccis} : Create a new fibonacci.
     *
     * @param fibonacciDTO the fibonacciDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fibonacciDTO, or with status {@code 400 (Bad Request)} if the fibonacci has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fibonaccis")
    public ResponseEntity<FibonacciDTO> createFibonacci(@RequestBody FibonacciDTO fibonacciDTO) throws URISyntaxException {
        log.debug("REST request to save Fibonacci : {}", fibonacciDTO);
        if (fibonacciDTO.getId() != null) {
            throw new BadRequestAlertException("A new fibonacci cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FibonacciDTO result = fibonacciService.save(fibonacciDTO);
        return ResponseEntity.created(new URI("/api/fibonaccis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fibonaccis} : Updates an existing fibonacci.
     *
     * @param fibonacciDTO the fibonacciDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fibonacciDTO,
     * or with status {@code 400 (Bad Request)} if the fibonacciDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fibonacciDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fibonaccis")
    public ResponseEntity<FibonacciDTO> updateFibonacci(@RequestBody FibonacciDTO fibonacciDTO) throws URISyntaxException {
        log.debug("REST request to update Fibonacci : {}", fibonacciDTO);
        if (fibonacciDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FibonacciDTO result = fibonacciService.save(fibonacciDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fibonacciDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fibonaccis} : get all the fibonaccis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fibonaccis in body.
     */
    @GetMapping("/fibonaccis")
    public ResponseEntity<List<FibonacciDTO>> getAllFibonaccis(Pageable pageable) {
        log.debug("REST request to get a page of Fibonaccis");
        Page<FibonacciDTO> page = fibonacciService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fibonaccis/:id} : get the "id" fibonacci.
     *
     * @param id the id of the fibonacciDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fibonacciDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fibonaccis/{id}")
    public ResponseEntity<FibonacciDTO> getFibonacci(@PathVariable Long id) {
        log.debug("REST request to get Fibonacci : {}", id);
        Optional<FibonacciDTO> fibonacciDTO = fibonacciService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fibonacciDTO);
    }

    /**
     * {@code DELETE  /fibonaccis/:id} : delete the "id" fibonacci.
     *
     * @param id the id of the fibonacciDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fibonaccis/{id}")
    public ResponseEntity<Void> deleteFibonacci(@PathVariable Long id) {
        log.debug("REST request to delete Fibonacci : {}", id);
        fibonacciService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
