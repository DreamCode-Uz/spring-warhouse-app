package uz.pdp.springwarhouseapp.payload;

import lombok.Data;

@Data
public class OutputProductDTO {
    private Integer productId;
    private Double amount;
    private Double price;
    private Integer outputId;
}
