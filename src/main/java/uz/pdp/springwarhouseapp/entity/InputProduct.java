package uz.pdp.springwarhouseapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class InputProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Product product;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Double price;

    @Column
    private Date expireDate;

    @ManyToOne(optional = false)
    private Input input;

    public InputProduct(Product product, Double amount, Double price, Date expireDate, Input input) {
        this.product = product;
        this.amount = amount;
        this.price = price;
        this.expireDate = expireDate;
        this.input = input;
    }
}
