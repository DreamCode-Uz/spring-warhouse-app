package uz.pdp.springwarhouseapp.entity.template;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class AbsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean active = true;
}
