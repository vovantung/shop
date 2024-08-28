package txu.shop.service;

import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import txu.common.exception.BadParameterException;
import txu.common.exception.NotFoundException;
import txu.shop.dao.OrderItemDao;
import txu.shop.entity.CartItemEntity;
import txu.shop.entity.OrderItemEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemsService {

    private final OrderItemDao orderItemDao;
    public void removeByOrderIdProductId(String orderId, String productId){
        if(StringUtil.isNullOrEmpty(orderId) || StringUtil.isNullOrEmpty(productId)){
            log.error("Required orderId and productId must not null or empty");
            throw new BadParameterException("Required orderId and productId must not null or empty");
        }
        orderItemDao.removeByOrderIdProductId(orderId, productId);
    }

    public OrderItemEntity updateQuantity(String orderId, String productId, Integer quantity){
        var cart = orderItemDao.getByOrderIdProductId(orderId, productId);
        if (cart == null){
            throw new NotFoundException(String.format("Not found  ItemOrder with order '%s', productId '%s'", orderId, productId));
        }

        cart.setQuantity(quantity);
        return orderItemDao.save(cart);
    }

}
