package com.uy.atiliator.fibonacci.rest.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uy.atiliator.fibonacci.rest.web.rest.TestUtil;

public class FibonacciDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FibonacciDTO.class);
        FibonacciDTO fibonacciDTO1 = new FibonacciDTO();
        fibonacciDTO1.setId(1L);
        FibonacciDTO fibonacciDTO2 = new FibonacciDTO();
        assertThat(fibonacciDTO1).isNotEqualTo(fibonacciDTO2);
        fibonacciDTO2.setId(fibonacciDTO1.getId());
        assertThat(fibonacciDTO1).isEqualTo(fibonacciDTO2);
        fibonacciDTO2.setId(2L);
        assertThat(fibonacciDTO1).isNotEqualTo(fibonacciDTO2);
        fibonacciDTO1.setId(null);
        assertThat(fibonacciDTO1).isNotEqualTo(fibonacciDTO2);
    }
}
