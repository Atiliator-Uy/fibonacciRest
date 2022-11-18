package com.uy.atiliator.fibonacci.rest.web.rest;

import com.uy.atiliator.fibonacci.rest.FibonacciRestApp;
import com.uy.atiliator.fibonacci.rest.domain.Fibonacci;
import com.uy.atiliator.fibonacci.rest.repository.FibonacciRepository;
import com.uy.atiliator.fibonacci.rest.service.FibonacciService;
import com.uy.atiliator.fibonacci.rest.service.dto.FibonacciDTO;
import com.uy.atiliator.fibonacci.rest.service.mapper.FibonacciMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FibonacciResource} REST controller.
 */
@SpringBootTest(classes = FibonacciRestApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class FibonacciResourceIT {

    private static final Integer DEFAULT_NUMERO_FIBONACCI = 1;
    private static final Integer UPDATED_NUMERO_FIBONACCI = 2;

    private static final Integer DEFAULT_ENESIMO = 1;
    private static final Integer UPDATED_ENESIMO = 2;

    private static final Integer DEFAULT_CONTADOR_REST = 1;
    private static final Integer UPDATED_CONTADOR_REST = 2;

    @Autowired
    private FibonacciRepository fibonacciRepository;

    @Autowired
    private FibonacciMapper fibonacciMapper;

    @Autowired
    private FibonacciService fibonacciService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFibonacciMockMvc;

    private Fibonacci fibonacci;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fibonacci createEntity(EntityManager em) {
        Fibonacci fibonacci = new Fibonacci()
            .numeroFibonacci(DEFAULT_NUMERO_FIBONACCI)
            .enesimo(DEFAULT_ENESIMO)
            .contadorRest(DEFAULT_CONTADOR_REST);
        return fibonacci;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fibonacci createUpdatedEntity(EntityManager em) {
        Fibonacci fibonacci = new Fibonacci()
            .numeroFibonacci(UPDATED_NUMERO_FIBONACCI)
            .enesimo(UPDATED_ENESIMO)
            .contadorRest(UPDATED_CONTADOR_REST);
        return fibonacci;
    }

    @BeforeEach
    public void initTest() {
        fibonacci = createEntity(em);
    }

    @Test
    @Transactional
    public void createFibonacci() throws Exception {
        int databaseSizeBeforeCreate = fibonacciRepository.findAll().size();

        // Create the Fibonacci
        FibonacciDTO fibonacciDTO = fibonacciMapper.toDto(fibonacci);
        restFibonacciMockMvc.perform(post("/api/fibonaccis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fibonacciDTO)))
            .andExpect(status().isCreated());

        // Validate the Fibonacci in the database
        List<Fibonacci> fibonacciList = fibonacciRepository.findAll();
        assertThat(fibonacciList).hasSize(databaseSizeBeforeCreate + 1);
        Fibonacci testFibonacci = fibonacciList.get(fibonacciList.size() - 1);
        assertThat(testFibonacci.getNumeroFibonacci()).isEqualTo(DEFAULT_NUMERO_FIBONACCI);
        assertThat(testFibonacci.getEnesimo()).isEqualTo(DEFAULT_ENESIMO);
        assertThat(testFibonacci.getContadorRest()).isEqualTo(DEFAULT_CONTADOR_REST);
    }

    @Test
    @Transactional
    public void createFibonacciWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fibonacciRepository.findAll().size();

        // Create the Fibonacci with an existing ID
        fibonacci.setId(1L);
        FibonacciDTO fibonacciDTO = fibonacciMapper.toDto(fibonacci);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFibonacciMockMvc.perform(post("/api/fibonaccis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fibonacciDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fibonacci in the database
        List<Fibonacci> fibonacciList = fibonacciRepository.findAll();
        assertThat(fibonacciList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFibonaccis() throws Exception {
        // Initialize the database
        fibonacciRepository.saveAndFlush(fibonacci);

        // Get all the fibonacciList
        restFibonacciMockMvc.perform(get("/api/fibonaccis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fibonacci.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroFibonacci").value(hasItem(DEFAULT_NUMERO_FIBONACCI)))
            .andExpect(jsonPath("$.[*].enesimo").value(hasItem(DEFAULT_ENESIMO)))
            .andExpect(jsonPath("$.[*].contadorRest").value(hasItem(DEFAULT_CONTADOR_REST)));
    }
    
    @Test
    @Transactional
    public void getFibonacci() throws Exception {
        // Initialize the database
        fibonacciRepository.saveAndFlush(fibonacci);

        // Get the fibonacci
        restFibonacciMockMvc.perform(get("/api/fibonaccis/{id}", fibonacci.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fibonacci.getId().intValue()))
            .andExpect(jsonPath("$.numeroFibonacci").value(DEFAULT_NUMERO_FIBONACCI))
            .andExpect(jsonPath("$.enesimo").value(DEFAULT_ENESIMO))
            .andExpect(jsonPath("$.contadorRest").value(DEFAULT_CONTADOR_REST));
    }

    @Test
    @Transactional
    public void getNonExistingFibonacci() throws Exception {
        // Get the fibonacci
        restFibonacciMockMvc.perform(get("/api/fibonaccis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFibonacci() throws Exception {
        // Initialize the database
        fibonacciRepository.saveAndFlush(fibonacci);

        int databaseSizeBeforeUpdate = fibonacciRepository.findAll().size();

        // Update the fibonacci
        Fibonacci updatedFibonacci = fibonacciRepository.findById(fibonacci.getId()).get();
        // Disconnect from session so that the updates on updatedFibonacci are not directly saved in db
        em.detach(updatedFibonacci);
        updatedFibonacci
            .numeroFibonacci(UPDATED_NUMERO_FIBONACCI)
            .enesimo(UPDATED_ENESIMO)
            .contadorRest(UPDATED_CONTADOR_REST);
        FibonacciDTO fibonacciDTO = fibonacciMapper.toDto(updatedFibonacci);

        restFibonacciMockMvc.perform(put("/api/fibonaccis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fibonacciDTO)))
            .andExpect(status().isOk());

        // Validate the Fibonacci in the database
        List<Fibonacci> fibonacciList = fibonacciRepository.findAll();
        assertThat(fibonacciList).hasSize(databaseSizeBeforeUpdate);
        Fibonacci testFibonacci = fibonacciList.get(fibonacciList.size() - 1);
        assertThat(testFibonacci.getNumeroFibonacci()).isEqualTo(UPDATED_NUMERO_FIBONACCI);
        assertThat(testFibonacci.getEnesimo()).isEqualTo(UPDATED_ENESIMO);
        assertThat(testFibonacci.getContadorRest()).isEqualTo(UPDATED_CONTADOR_REST);
    }

    @Test
    @Transactional
    public void updateNonExistingFibonacci() throws Exception {
        int databaseSizeBeforeUpdate = fibonacciRepository.findAll().size();

        // Create the Fibonacci
        FibonacciDTO fibonacciDTO = fibonacciMapper.toDto(fibonacci);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFibonacciMockMvc.perform(put("/api/fibonaccis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fibonacciDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fibonacci in the database
        List<Fibonacci> fibonacciList = fibonacciRepository.findAll();
        assertThat(fibonacciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFibonacci() throws Exception {
        // Initialize the database
        fibonacciRepository.saveAndFlush(fibonacci);

        int databaseSizeBeforeDelete = fibonacciRepository.findAll().size();

        // Delete the fibonacci
        restFibonacciMockMvc.perform(delete("/api/fibonaccis/{id}", fibonacci.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fibonacci> fibonacciList = fibonacciRepository.findAll();
        assertThat(fibonacciList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
