package com.pramukh.productservice.Controller;

import com.pramukh.productservice.DTO.ProductRequestDto;
import com.pramukh.productservice.DTO.ProductResponseDto;
import com.pramukh.productservice.Model.ProductEntity;
import com.pramukh.productservice.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/products", consumes = "multipart/form-data")
    public ResponseEntity<String> getProducts(@RequestPart("product") List<ProductRequestDto> productRequestDto, @RequestPart("image") List<MultipartFile> file) throws IOException {
        productService.addProducts(productRequestDto, file);
        return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<ProductResponseDto> products = productService.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @GetMapping("/products/category/{category}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(@PathVariable String category) {
        List<ProductResponseDto> products = productService.getProductsByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{name}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByName(@PathVariable String name) {
        List<ProductResponseDto> products = productService.getProductsByName(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}

