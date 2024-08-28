package txu.shop.dto;

import lombok.Getter;

@Getter
public class UpdateQuantityOrderItemRequest {
    String orderId;
    String productId;
    Integer quantity;
}
