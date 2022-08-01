package uz.pdp.springwarhouseapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.springwarhouseapp.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // TAMINOTCHI
public class Supplier extends AbsEntity {

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;
}
