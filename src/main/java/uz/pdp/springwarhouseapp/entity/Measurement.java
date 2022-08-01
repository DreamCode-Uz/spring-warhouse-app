package uz.pdp.springwarhouseapp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.springwarhouseapp.entity.template.AbsEntity;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity // O'LCHOV
public class Measurement extends AbsEntity {
}
