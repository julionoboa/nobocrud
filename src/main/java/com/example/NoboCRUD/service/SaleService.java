package com.example.NoboCRUD.service;

import com.example.NoboCRUD.entity.Product;
import com.example.NoboCRUD.entity.Sale;
import com.example.NoboCRUD.entity.SaleProduct;
import com.example.NoboCRUD.repository.ProductRepository;
import com.example.NoboCRUD.repository.SaleRepository;
import com.example.NoboCRUD.utils.ParseNumbers;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    public Sale saveSale(String productName, String quantity, String clientName) {
        if (ParseNumbers.parseToInt(quantity)) {
            int quantityParsed = Integer.parseInt(quantity);
            Product product = productRepository.findByName(productName);
            if (product != null) {
                if (product.getStock() >= quantityParsed) {
                    Sale sale = new Sale();
                    populateSale(product, sale, clientName, quantityParsed);

                    SaleProduct saleProduct = new SaleProduct();
                    saleProduct.setSale(sale);
                    saleProduct.setProduct(product);
                    saleProduct.setQuantity(quantityParsed);

                    sale.getSaleProducts().add(saleProduct);

                    saleRepository.save(sale);
                    product.setStock(product.getStock() - quantityParsed);
                    productRepository.save(product);

                    return sale;
                } else {
                    throw new EntityNotFoundException("One or more products are not available in the required quantity.");
                }
            } else {
                throw new EntityNotFoundException("Product does not exists");
            }
        } else {
            throw new NumberFormatException("'Quantity' must be a number.");
        }
    }

    public List<Sale> saveAllSales(Map<String, String> productAndQuantityMap, String clientName) {
        List<Sale> sales = new ArrayList<>();

        for (Map.Entry<String, String> entry : productAndQuantityMap.entrySet()) {
            String productName = entry.getKey();
            String quantity = entry.getValue();

            Sale sale = saveSale(productName, quantity, clientName);
            sales.add(sale);
        }
        return sales;
    }

    public List<Sale> getSales() {
        return saleRepository.findAll();
    }

    public Sale updateSale(UUID id, String clientName, Integer quantity) {
        Sale sale = saleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Sale with ID " + id + " not found")
        );
        Product product = productRepository.findByName(sale.getProductName());
        if (product != null) {
            int oldQuantity = sale.getQuantity();

            sale.getSaleProducts().clear();

            populateSale(product, sale, clientName, quantity);

            product.setStock((product.getStock() - quantity) + oldQuantity);

            SaleProduct saleProduct = new SaleProduct();
            saleProduct.setSale(sale);
            saleProduct.setProduct(product);
            saleProduct.setQuantity(quantity);
            sale.getSaleProducts().add(saleProduct);

            saleRepository.save(sale);
            productRepository.save(product);
        }
        return sale;
    }

    public String deleteSale(UUID id) {
        Sale sale = saleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Sale with ID " + id + "not found.")
        );
        sale.getSaleProducts().clear();
        saleRepository.deleteById(id);
        return "Sale " + id + " deleted successfully";
    }

    public List<Sale> getSaleByClientName(String clientName) {
        return saleRepository.findByClientName(clientName);
    }

    public Sale getSaleById(UUID id){
        return saleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Sale with ID " + id + "not found.")
        );
    }

    private double calculateTotal(double price, Integer quantity) {
        double total = 0;
        total = total + (price * quantity);
        return total;
    }

    private void populateSale(Product product, Sale sale, String clientName, Integer quantity) {
        double total = calculateTotal(product.getPrice(), quantity);
        sale.setModifiedDate(LocalDateTime.now());
        sale.setClientName(clientName);
        sale.setProductName(product.getName());
        sale.setQuantity(quantity);
        sale.setSubTotal(total);
        sale.setTaxes(total * 0.18);
        sale.setTotal(total + sale.getTaxes());
    }
}
