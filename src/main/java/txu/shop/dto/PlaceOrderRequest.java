package txu.shop.dto;
import lombok.Getter;
import java.util.List;
@Getter
public class PlaceOrderRequest {
    String username;
    List<OrderDetail> orderDetails;
}

