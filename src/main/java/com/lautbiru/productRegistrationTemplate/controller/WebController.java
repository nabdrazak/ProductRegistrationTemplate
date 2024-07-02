package com.lautbiru.productRegistrationTemplate.controller;

import com.lautbiru.productRegistrationTemplate.model.Product;
import com.lautbiru.productRegistrationTemplate.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/home")
public class WebController {

    @Autowired
    RestTemplate restTemplate;

    private static final String URL_PREFIX = "http://localhost:9009/products/";

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/list-products")
    public ModelAndView getProducts() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view-products");
        return modelAndView;
    }

    @GetMapping("/save-product")
    public String postProduct(Model model) {
        model.addAttribute("product", new Product());
        return "add-item";
    }

    @PostMapping("/map-product")
    public String mapProduct(@ModelAttribute Product product, Model model) {
        ProductWrapper productWrapper = new ProductWrapper();
        List<Product> products = new ArrayList<>();
        products.add(product);

        productWrapper.setProducts(products);
        model.addAttribute("product", product);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<ProductWrapper> entity = new HttpEntity<ProductWrapper>(productWrapper, headers);

        restTemplate.exchange(URL_PREFIX, HttpMethod.POST, entity, String.class);

        return "view-products";
    }
}
