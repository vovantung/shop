package txu.shop.service;

import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import txu.common.exception.NotFoundException;
import txu.shop.dao.*;
import txu.shop.dto.OrderDetail;
import txu.shop.entity.*;
import txu.shop.util.TXUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final ProductDao productDao;
    private final OrderItemDao orderItemDao;

    @Transactional
    public void placeOrder(String username, List<OrderDetail> orderDetails){

        UserEntity user = userDao.getByUsername(username);
        if(user == null){
            throw new NotFoundException(String.format("UserId '%s' is not found", username));
        }

        OrderEntity order = new OrderEntity();
        order.setCreateDatetime(DateTime.now().toDate());
        order.setUpdateDatetime(DateTime.now().toDate());
        order.setUserEntity(user);

        order.setOrderItemEntities(processOrderItem(order,orderDetails));
        orderDao.save(order);
    }

    @Transactional
    public void updateOrder(String orderId, List<OrderDetail> orderDetails){

//        UserEntity user = userDao.getByUsername(username);
//        if(user == null){
//            throw new NotFoundException(String.format("UserId '%s' is not found", username));
//        }

        OrderEntity order = orderDao.getById(orderId);
        if (order == null){
            throw new NotFoundException("Not found order with id " + orderId);
        }
        removeOldOrderItemsOfOrder(order,orderDetails);

        order.setUpdateDatetime(DateTime.now().toDate());
        order.setOrderItemEntities(processOrderItem(order,orderDetails));
        orderDao.save(order);
    }

    private void removeOldOrderItemsOfOrder(OrderEntity order,  List<OrderDetail> orderDetails){
        List<String> items = new ArrayList<>();
        for (OrderDetail od:orderDetails){
            items.add(od.getProductId());
        }
        List<String> itemsOld = orderItemDao.getOrderItems(order.getId());
        orderItemDao.removeByIds(TXUtil.leftList(itemsOld, items));
    }

    private List<OrderItemEntity> processOrderItem(OrderEntity order, List<OrderDetail> orderDetails){
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        for (OrderDetail orderDetail: orderDetails){
            ProductEntity product = productDao.findById(orderDetail.getProductId());
            if (product != null) {
                OrderItemEntity orderItemEntity = new OrderItemEntity();
                OrderItemProfiles orderItemProfiles = new OrderItemProfiles();
                orderItemProfiles.setOrderEntity(order);
                orderItemProfiles.setProductEntity(product);
                orderItemEntity.setId(orderItemProfiles);
                orderItemEntity.setQuantity(orderDetail.getQuality());
                orderItemEntities.add(orderItemEntity);
            }
        }
       return orderItemEntities;
    }

    @Transactional
    public void deleteOrder(String orderId){
        OrderEntity orderEntity = orderDao.getById(orderId);
        orderDao.remove(orderEntity);
    }
}
