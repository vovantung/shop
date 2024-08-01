package txu.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "product_category")
public class ProductCategoryEntity implements Serializable {
    @EmbeddedId
    private ProductCategoryProfiles id;
}
