package txu.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

//import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "order_item")
public class OrderItemEntity implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdOrGenerated")
//    @GenericGenerator(strategy = "org.hibernate.id.UUIDHexGenerator", name = "IdOrGenerated")
//    @Column(name = "id")
//    private String id;

//    @ManyToOne
//    @JoinColumn(name = "orderId")
//    private OrderEntity orderEntity;
//
//    @ManyToOne
//    @JoinColumn(name = "productId")
//    private  ProductEntity productEntity;

    @EmbeddedId
    OrderItemProfiles id;

    private int quantity;

    private Date createDatetime;

    private Date updateDatetime;
}
