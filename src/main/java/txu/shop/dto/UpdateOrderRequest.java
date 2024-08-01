package txu.shop.dto;
import lombok.Getter;
import java.util.List;

@Getter
public class UpdateOrderRequest {
    String orderId;
    List<OrderDetail> orderDetails;
}

