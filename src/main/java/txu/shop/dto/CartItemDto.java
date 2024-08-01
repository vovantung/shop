package txu.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private String productId;
    private String username;
    private Integer quantity;
}
