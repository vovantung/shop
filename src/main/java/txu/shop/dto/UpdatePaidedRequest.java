package txu.shop.dto;

import lombok.Getter;

@Getter
public class UpdatePaidedRequest {
    String orderId;
    Boolean paided;
}
