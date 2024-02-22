package com.example.NoboCRUD.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.NoboCRUD.entity.Product;
import com.example.NoboCRUD.entity.Sale;
import com.example.NoboCRUD.repository.ProductRepository;
import com.example.NoboCRUD.repository.SaleRepository;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class SaleServiceTests {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private SaleService saleService;

    @Test
    void testSaveSale_WhenValidProductAndQuantity_ShouldSaveSale() {
        String productName = "Monitor";
        String quantity = "2";
        String clientName = "John Doe";

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(productName);
        product.setPrice(100.0);
        product.setStock(10);

        when(productRepository.findByName(productName)).thenReturn(product);

        Sale savedSale = saleService.saveSale(productName, quantity, clientName);

        assertNotNull(savedSale);
        assertEquals(clientName, savedSale.getClientName());
        assertEquals(Double.parseDouble(quantity), savedSale.getQuantity());
        assertEquals(200.0, savedSale.getSubTotal());
        assertEquals(36.0, savedSale.getTaxes());
        assertEquals(236.0, savedSale.getTotal());
        assertEquals(1, savedSale.getSaleProducts().size());
    }

    @Test
    public void testUpdateSale_WhenValidClientIdAndQuantity_ShouldUpdateSale() {
        UUID saleId = UUID.randomUUID();
        String clientName = "John Doe";
        Integer newQuantity = 5;

        Sale sale = new Sale();
        sale.setId(saleId);
        sale.setClientName(clientName);
        sale.setProductName("Monitor");
        sale.setQuantity(2);
        sale.setSubTotal(200.0);
        sale.setTaxes(36.0);
        sale.setTotal(236.0);

        Product product = new Product();
        product.setName("Monitor");
        product.setPrice(100.0);
        product.setStock(10);

        when(saleRepository.findById(saleId)).thenReturn(Optional.of(sale));
        when(productRepository.findByName("Monitor")).thenReturn(product);

        Sale updatedSale = saleService.updateSale(saleId, clientName, newQuantity);

        assertNotNull(updatedSale);
        assertEquals(clientName, updatedSale.getClientName());
        assertEquals(newQuantity, updatedSale.getQuantity());
        assertEquals(500.0, updatedSale.getSubTotal());
        assertEquals(90.0, updatedSale.getTaxes());
        assertEquals(590.0, updatedSale.getTotal());
        assertEquals(1, updatedSale.getSaleProducts().size());
    }

    @Test
    public void testDeleteSale_WhenValidSaleId_ShouldDeleteSale() {
        UUID saleId = UUID.randomUUID();
        Sale sale = new Sale();
        sale.setId(saleId);
        sale.setClientName("John Doe");
        sale.setProductName("Monitor");
        sale.setQuantity(2);
        sale.setSubTotal(200.0);
        sale.setTaxes(36.0);
        sale.setTotal(236.0);

        when(saleRepository.findById(saleId)).thenReturn(Optional.of(sale));

        String result = saleService.deleteSale(saleId);

        assertNotNull(result);
        assertEquals("Sale " + saleId + " deleted successfully", result);
        verify(saleRepository, times(1)).deleteById(saleId);
    }

}
