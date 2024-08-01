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
@Table(name = "order_t")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdOrGenerated")
    @GenericGenerator(strategy = "org.hibernate.id.UUIDHexGenerator", name = "IdOrGenerated")
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    private Date createDatetime;

    private Date updateDatetime;


    @JsonIgnore
    @OneToMany(mappedBy = "id.orderEntity", cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItemEntities;
}
