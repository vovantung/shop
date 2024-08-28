package txu.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
    String productId;
    private String userId;
    private Integer quantity;
}
