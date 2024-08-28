package txu.shop.dto;

import lombok.Getter;

@Getter
public class UpdateDeliveredRequest {
    String orderId;
    Boolean delivered;
}
