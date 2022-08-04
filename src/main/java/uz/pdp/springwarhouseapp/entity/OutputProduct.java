package uz.pdp.springwarhouseapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class OutputProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Product product;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(optional = false)
    private Output output;

    public OutputProduct(Product product, Double amount, Double price, Output output) {
        this.product = product;
        this.amount = amount;
        this.price = price;
        this.output = output;
    }
}
