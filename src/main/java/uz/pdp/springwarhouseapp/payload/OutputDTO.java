package uz.pdp.springwarhouseapp.payload;

import lombok.Data;

@Data
public class OutputDTO {
    private Integer warehouseId;
    private Integer clientId;
    private Integer currencyId;
    private String factureNumber;
}
