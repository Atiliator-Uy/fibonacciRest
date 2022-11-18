package com.uy.atiliator.fibonacci.rest.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uy.atiliator.fibonacci.rest.web.rest.TestUtil;

public class FibonacciTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fibonacci.class);
        Fibonacci fibonacci1 = new Fibonacci();
        fibonacci1.setId(1L);
        Fibonacci fibonacci2 = new Fibonacci();
        fibonacci2.setId(fibonacci1.getId());
        assertThat(fibonacci1).isEqualTo(fibonacci2);
        fibonacci2.setId(2L);
        assertThat(fibonacci1).isNotEqualTo(fibonacci2);
        fibonacci1.setId(null);
        assertThat(fibonacci1).isNotEqualTo(fibonacci2);
    }
}
