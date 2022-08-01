package uz.pdp.springwarhouseapp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.springwarhouseapp.entity.template.AbsEntity;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Warehouse extends AbsEntity {
}
