package com.example.NoboCRUD.controller;

import com.example.NoboCRUD.entity.Product;
import com.example.NoboCRUD.entity.Sale;
import com.example.NoboCRUD.service.ProductService;
import com.example.NoboCRUD.service.SaleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class NoboCrudController {
    private final ProductService productService;
    private final SaleService saleService;

    public NoboCrudController(ProductService productService, SaleService saleService) {
        this.productService = productService;
        this.saleService = saleService;
    }

    //Products

    @PostMapping("/rest/addNewProduct")
    public Product addNewProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PostMapping("/rest/addNewProducts")
    public List<Product> addNewProducts(@RequestBody List<Product> products) {
        return productService.saveAllProducts(products);
    }

    @GetMapping("/rest/findAllProducts")
    public List<Product> findAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/rest/findProductById/{id}")
    public Product findProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @GetMapping("/rest/findProductByName/{name}")
    public List<Product> findProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @PutMapping("/rest/updateProduct")
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @PutMapping("/rest/updateProductName/{id}/{name}")
    public Product updateProductName(@PathVariable UUID id, @PathVariable String name) {
        return productService.updateProductName(id, name);
    }

    @PutMapping("/rest/updateProductPrice/{id}/{price}")
    public Product updateProductPrice(@PathVariable UUID id, @PathVariable String price) {
        return productService.updateProductPrice(id, price);
    }

    @PutMapping("/rest/updateProductStock/{id}/{stock}")
    public Product updateProductStock(@PathVariable UUID id, @PathVariable String stock) {
        return productService.updateProductStock(id, stock);
    }

    @DeleteMapping("/rest/deleteProductById/{id}")
    public String deleteProductById(@PathVariable UUID id) {
        return productService.deleteProductById(id);
    }

    //Sales

    @PostMapping("/rest/addNewSale/{productName}/{quantity}/{clientName}")
    public Sale addNewSale(@PathVariable String productName, @PathVariable String quantity, @PathVariable String clientName) {
        return saleService.saveSale(productName, quantity, clientName);
    }

    @PostMapping("/rest/addNewSales/{clientName}")
    public List<Sale> addNewSales(@RequestBody Map<String, String> productAndQuantityMap, @PathVariable String clientName) {
        return saleService.saveAllSales(productAndQuantityMap, clientName);
    }

    @GetMapping("/rest/findAllSales")
    public List<Sale> findAllSales() {
        return saleService.getSales();
    }

    @PutMapping("/rest/updateSale/{id}/{clientName}/{quantity}")
    public Sale updateSale(@PathVariable UUID id, @PathVariable String clientName, @PathVariable int quantity) {
        return saleService.updateSale(id, clientName, quantity);
    }

    @DeleteMapping("/rest/deleteSale/{id}")
    public String deleteSale (@PathVariable UUID id){
        return saleService.deleteSale(id);
    }

    @GetMapping("/rest/findSalesByClientName/{clientName}")
    public List<Sale> findSalesByClientName(@PathVariable String clientName){
        return saleService.getSaleByClientName(clientName);
    }

    @GetMapping("/rest/fiendSaleById/{id}")
    public Sale findSaleById(@PathVariable UUID id){
        return saleService.getSaleById(id);
    }


}
