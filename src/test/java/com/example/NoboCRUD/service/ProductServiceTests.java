package com.example.NoboCRUD.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.NoboCRUD.entity.Product;
import com.example.NoboCRUD.repository.ProductRepository;

class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveProduct_WhenSavingProduct_ShouldReturnSavedProduct() {
        Product product = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product savedProduct = productService.saveProduct(product);
        assertNotNull(savedProduct);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getProductById_WhenProductExists_ShouldReturnFoundProduct() {
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Product foundProduct = productService.getProductById(productId);
        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void updateProductStock_WhenValidStockInput_ShouldUpdateProductStock() {
        UUID productId = UUID.randomUUID();
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Name");
        existingProduct.setPrice(100.0);
        existingProduct.setStock(50);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("New Name");
        updatedProduct.setPrice(150.0);
        updatedProduct.setStock(75);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(updatedProduct);

        assertNotNull(result);
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getPrice(), result.getPrice());
        assertEquals(updatedProduct.getStock(), result.getStock());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }
}
