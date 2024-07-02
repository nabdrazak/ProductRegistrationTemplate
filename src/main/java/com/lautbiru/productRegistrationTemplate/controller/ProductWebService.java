package com.lautbiru.productRegistrationTemplate.controller;

import com.lautbiru.productRegistrationTemplate.exception.ProductNotFoundException;
import com.lautbiru.productRegistrationTemplate.model.Product;
import com.lautbiru.productRegistrationTemplate.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/template")
public class ProductWebService {

    @Autowired
    RestTemplate restTemplate;

    private static final String URL_PREFIX = "http://localhost:9009/products/";

    @GetMapping(value = "/products/")
    public ResponseEntity<Object> getProductList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return restTemplate.exchange(URL_PREFIX, HttpMethod.GET, entity, Object.class);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable("id") String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Product> entity = new HttpEntity<Product>(headers);

        try {
            return restTemplate.exchange(URL_PREFIX +id, HttpMethod.GET, entity, Object.class);
        } catch (Exception pex) {
            pex.printStackTrace();
            throw new ProductNotFoundException();
        }
    }

    @PostMapping(value = "/products/")
    public ResponseEntity<String> createProduct(@RequestBody ProductWrapper productWrapper) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<ProductWrapper> entity = new HttpEntity<ProductWrapper>(productWrapper, headers);

        return restTemplate.exchange(URL_PREFIX, HttpMethod.POST, entity, String.class);
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);

        try {
            return restTemplate.exchange(URL_PREFIX +id, HttpMethod.PUT, entity, String.class);
        } catch (Exception pex) {
            throw new ProductNotFoundException();
        }
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Product> entity = new HttpEntity<Product>(headers);

        try {
            return restTemplate.exchange(URL_PREFIX +id, HttpMethod.DELETE, entity, String.class);
        } catch (Exception pex) {
            throw new ProductNotFoundException();
        }

    }
}
