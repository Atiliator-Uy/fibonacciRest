package com.uy.atiliator.fibonacci.rest.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FibonacciMapperTest {

    private FibonacciMapper fibonacciMapper;

    @BeforeEach
    public void setUp() {
        fibonacciMapper = new FibonacciMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(fibonacciMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(fibonacciMapper.fromId(null)).isNull();
    }
}
