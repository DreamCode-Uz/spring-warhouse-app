package uz.pdp.springwarhouseapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Output {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Timestamp date;

    @ManyToOne
    private Warehouse warehouse;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Currency currency;

    private String factureNumber;

    @Column(nullable = false, unique = true)
    private String code;

    public Output(Timestamp date, Warehouse warehouse, Client client, Currency currency, String factureNumber, String code) {
        this.date = date;
        this.warehouse = warehouse;
        this.client = client;
        this.currency = currency;
        this.factureNumber = factureNumber;
        this.code = code;
    }
}
