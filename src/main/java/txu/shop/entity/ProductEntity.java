package txu.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "product")
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdOrGenerated")
    @GenericGenerator(strategy = "org.hibernate.id.UUIDHexGenerator", name = "IdOrGenerated")
    @Column(name = "id")
    private String id;

    private String name;

    private Float price;

    private String description;

    private Date createDatetime;

    private Date updateDatetime;

    @OneToOne(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private RateAvg rateAvg;

    @JsonIgnore
    @OneToMany(mappedBy = "id.productEntity", cascade = CascadeType.ALL)
    private List<ProductCategoryEntity> productCategoryEntities;

}
