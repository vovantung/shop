package txu.shop.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class OrderItemProfiles implements Serializable {

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private OrderEntity orderEntity;
}
