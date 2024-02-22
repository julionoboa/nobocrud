package com.example.NoboCRUD.service;

import com.example.NoboCRUD.entity.Product;
import com.example.NoboCRUD.entity.Sale;
import com.example.NoboCRUD.repository.ProductRepository;
import com.example.NoboCRUD.repository.SaleRepository;
import com.example.NoboCRUD.utils.ParseNumbers;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.NoboCRUD.utils.ParseNumbers.parseToInt;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product){
        product.setModifiedDate(LocalDateTime.now());
        return productRepository.save(product);
    }

    public List<Product> saveAllProducts(List<Product> products){
        return productRepository.saveAll(products);
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(UUID id){
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getProductByName(String name){
        return productRepository.findAllByName(name);
    }

    public String deleteProductById(UUID id){
        if (!productRepository.existsById(id)){
            throw new EntityNotFoundException("Product with ID " + id + " not found.");
        }
        productRepository.deleteById(id);
        return "Product " + id + " deleted successfully.";
    }

    public Product updateProduct(Product product){
        Product existingProduct = getExistingProduct(product.getId());
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        existingProduct.setModifiedDate(LocalDateTime.now());
        return productRepository.save(existingProduct);
    }

    public Product updateProductName(UUID id, String productName){
        Product existingProduct = getExistingProduct(id);
        existingProduct.setName(productName);
        existingProduct.setModifiedDate(LocalDateTime.now());
        return productRepository.save(existingProduct);
    }

    public Product updateProductPrice(UUID id, String price){
        Product existingProduct = getExistingProduct(id);
        if (ParseNumbers.parseToDouble(price)){
            existingProduct.setPrice(Double.parseDouble(price));
            existingProduct.setModifiedDate(LocalDateTime.now());
            return productRepository.save(existingProduct);
        }else{
            throw new NumberFormatException("'Price' must be a number.");
        }

    }

    public Product updateProductStock(UUID id, String stock){
        Product existingProduct = getExistingProduct(id);
        if (ParseNumbers.parseToInt(stock)) {
            existingProduct.setStock(Integer.parseInt(stock));
            existingProduct.setModifiedDate(LocalDateTime.now());
            return productRepository.save(existingProduct);
        }else{
            throw new NumberFormatException("'Stock' must be a number.");
        }
    }

    private Product getExistingProduct(UUID id){
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product with ID " + id + " not found.")
        );
    }
}
