package txu.shop.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import txu.shop.base.BaseApi;
import txu.shop.dto.DeleteOrderRequest;
import txu.shop.dto.OrderDetail;
import txu.shop.dto.PlaceOrderRequest;
import txu.shop.dto.UpdateOrderRequest;
import txu.shop.service.OrderService;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderApi  extends BaseApi {
    private final OrderService orderService;

    @PostMapping()
    public void placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest){
        orderService.placeOrder(placeOrderRequest.getUsername(), placeOrderRequest.getOrderDetails());
    }

    @PutMapping()
    public void updateOrder(@RequestBody UpdateOrderRequest updateOrderRequest){
        orderService.updateOrder(updateOrderRequest.getOrderId(), updateOrderRequest.getOrderDetails());
    }

    @DeleteMapping
    public void deleteOrder(@RequestBody DeleteOrderRequest deleteOrderRequest){
        orderService.deleteOrder(deleteOrderRequest.getOrderId());
    }

}
