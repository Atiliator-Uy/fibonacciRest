package com.uy.atiliator.fibonacci.rest.web.rest.impl;

import com.uy.atiliator.fibonacci.rest.config.AsyncConfiguration;
import com.uy.atiliator.fibonacci.rest.repository.FibonacciRepository;
import com.uy.atiliator.fibonacci.rest.web.api.EnesimoFibonacciApiDelegate;

import com.uy.atiliator.fibonacci.rest.web.api.model.Fibonacci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedList;
import java.util.List;

@Service
public class EnesimoFibonacciApiImpl implements EnesimoFibonacciApiDelegate {

    private final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);

    @Autowired
    FibonacciRepository fibonacciRepository;

    @Override
    public ResponseEntity<List<Fibonacci>> enesimoFibonacciNumeroFibonacciGet(Integer numeroFibonacci) {
        log.debug("Atiliator ---> EnesimoFibonacciApiImpl.enesimoFibonacciGet");
        log.debug("numeroFibonacci : " + numeroFibonacci);

        List<Fibonacci> response = new LinkedList<Fibonacci>();

        // Sólo mayor o igual a 1
        if (numeroFibonacci <= 0) {
            throw new FigonacciNotFoundException();
        }

        // Calculo enesimo
        Integer enesimoFigonacci = enesimo(numeroFibonacci);

        if (enesimoFigonacci == 0) {
            throw new FigonacciNotFoundException();
        }

        // Busco si ya existe
        List<com.uy.atiliator.fibonacci.rest.domain.Fibonacci> fibonacciEntityList = findFibonacci(numeroFibonacci, enesimoFigonacci);

        // Si NO existe lo inserto
        if (fibonacciEntityList.size() == 0) {
            com.uy.atiliator.fibonacci.rest.domain.Fibonacci fibonacciEntity = new com.uy.atiliator.fibonacci.rest.domain.Fibonacci();
            fibonacciEntity.setNumeroFibonacci(numeroFibonacci);
            fibonacciEntity.enesimo(enesimoFigonacci);
            fibonacciEntity.setContadorRest(1);

            fibonacciEntity = fibonacciRepository.save(fibonacciEntity);

            Fibonacci fibonacci = new Fibonacci();
            fibonacci.numeroFibonacci(numeroFibonacci);
            fibonacci.setEnesimo(enesimoFigonacci);
            fibonacci.setContadorRest(fibonacciEntity.getContadorRest());
            fibonacci.setId(fibonacciEntity.getId().intValue());
            response.add(fibonacci);

        } else  { // Si existe lo actualizo

            for (com.uy.atiliator.fibonacci.rest.domain.Fibonacci fibonacciEntity : fibonacciEntityList) {
                fibonacciEntity.setContadorRest(fibonacciEntity.getContadorRest() + 1);
                fibonacciEntity = fibonacciRepository.save(fibonacciEntity);

                Fibonacci fibonacci = new Fibonacci();
                fibonacci.numeroFibonacci(numeroFibonacci);
                fibonacci.setEnesimo(enesimoFigonacci);
                fibonacci.setContadorRest(fibonacciEntity.getContadorRest());
                fibonacci.setId(fibonacciEntity.getId().intValue());
                response.add(fibonacci);
            }
        }

        return ResponseEntity.ok().body(response);

    }

    /**
     * Cálculo del n-esimo n →F (n)
     * @param numeroFibonacci
     * @return
     */
    private Integer enesimo(Integer numeroFibonacci) {
        Integer a = 0;
        Integer b = 1;
        Integer c;

        for (int x = 0; x < numeroFibonacci; x++) {
            c = a + b;
            a = b;
            b = c;

        }
        return a;
    }

    private List<com.uy.atiliator.fibonacci.rest.domain.Fibonacci> findFibonacci(Integer numeroFibonacci, Integer enesimoFigonacci) {
        com.uy.atiliator.fibonacci.rest.domain.Fibonacci fibonacciExample = new com.uy.atiliator.fibonacci.rest.domain.Fibonacci();

        fibonacciExample.setNumeroFibonacci(numeroFibonacci);
        fibonacciExample.setEnesimo(enesimoFigonacci);

        Example<com.uy.atiliator.fibonacci.rest.domain.Fibonacci> example = Example.of(fibonacciExample);

        return fibonacciRepository.findAll(example);

    }


    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No es un numero Figonacci.")
    public class FigonacciNotFoundException extends RuntimeException {
    }

}
