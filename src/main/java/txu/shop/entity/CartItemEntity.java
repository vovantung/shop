package txu.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

//import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItemEntity implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdOrGenerated")
//    @GenericGenerator(strategy = "org.hibernate.id.UUIDHexGenerator", name = "IdOrGenerated")
//    @Column(name = "id")
//    private String id;

    @EmbeddedId
    CartItemProfiles id;


    private Date createDatetime;

    private Date updateDatetime;

    private Integer quantity;
}
