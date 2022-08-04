package uz.pdp.springwarhouseapp.payload;

import lombok.Data;

import java.util.Date;

@Data
public class InputProductDTO {
    private Integer productId;
    private Double amount;
    private Double price;
    private Date expireDate;
    private Integer inputId;
}
