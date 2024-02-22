package com.example.NoboCRUD.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Sale {
    @Id
    @GeneratedValue
    private UUID id;
    private String clientName;
    private String productName;
    private int quantity;
    private double subTotal;
    private double taxes;
    private double total;
    private LocalDateTime modifiedDate;

    @JsonIgnore
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleProduct> saleProducts = new ArrayList<>();
}
