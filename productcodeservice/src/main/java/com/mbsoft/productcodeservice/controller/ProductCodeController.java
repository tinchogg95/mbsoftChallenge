package com.mbsoft.productcodeservice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/product-codes")
public class ProductCodeController {

    @GetMapping("/{code}/is-priority")
    public boolean isPriority(@PathVariable String code) {
        String codigoAlfabetico = code.split("-")[0];
        return codigoAlfabetico.startsWith("P") || codigoAlfabetico.startsWith("W");
    }

    @GetMapping("/{code}/verify")
    public boolean verify(@PathVariable String code) {
        String[] partes = code.split("-");
        if (partes.length != 3) return false;
        
        String parteNumerica = partes[1];
        int digitoVerificador = Integer.parseInt(partes[2]);
        
        int suma = calcularSuma(parteNumerica);
        return suma == digitoVerificador;
    }

    private int calcularSuma(String parteNumerica) {
        int suma = parteNumerica.chars()
                .map(Character::getNumericValue)
                .sum();
        
        while (suma > 9) {
            suma = String.valueOf(suma).chars()
                    .map(Character::getNumericValue)
                    .sum();
        }
        return suma;
    }

    @PostMapping("/sort")
    public List<String> sortProducts(@RequestBody List<String> productos) {
        return productos.stream()
                .sorted((p1, p2) -> {
                    String codigo1 = p1.split("-")[0];
                    String codigo2 = p2.split("-")[0];
                    return codigo1.compareTo(codigo2);
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/union")
    public List<String> union(@RequestBody List<List<String>> listasProductos) {
        return Stream.concat(listasProductos.get(0).stream(), listasProductos.get(1).stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @PostMapping("/intersection")
    public List<String> intersection(@RequestBody List<List<String>> listasProductos) {
        return listasProductos.get(0).stream()
                .filter(listasProductos.get(1)::contains)
                .distinct()
                .collect(Collectors.toList());
    }
}