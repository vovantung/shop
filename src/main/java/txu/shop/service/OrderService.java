package txu.shop.service;

import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import txu.common.exception.BadParameterException;
import txu.common.exception.NotFoundException;
import txu.shop.dao.*;
import txu.shop.dto.StatisticalDTO;
import txu.shop.entity.*;
import txu.shop.util.TXUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final ProductDao productDao;
    private final OrderItemDao orderItemDao;
    private final CartItemDao cartItemDao;

    public List<OrderEntity> getByUserId(String userId, Date from, Date to, boolean isAllOrder){
        from.setHours(0);
        from.setMinutes(0);
        from.setSeconds(0);
        to.setHours(23);
        to.setMinutes(59);
        to.setSeconds(59);
        return orderDao.getByUserId_(userId, from, to, isAllOrder);
    }
//    public List<StatisticalDTO> statistical(String userId, Date from, Date to, boolean isAllOrder){
public List<StatisticalDTO> statistical(Date from, Date to){
        from.setHours(0);
        from.setMinutes(0);
        from.setSeconds(0);
        to.setHours(23);
        to.setMinutes(59);
        to.setSeconds(59);
        return orderDao.statistical(from, to);
    }


    public List<OrderItemEntity> getOrderItems(String orderId){
         return orderItemDao.getOrderItemsT(orderId);
    }

    @Transactional
    public OrderEntity placeOrder(String username, String note){

        UserEntity user = userDao.getByUsername(username);
        if(user == null){
            throw new NotFoundException(String.format("UserId '%s' is not found", username));
        }

        List<CartItemEntity> cartItemEntities = cartItemDao.getByUerId(user.getId());


        OrderEntity order = new OrderEntity();
        order.setCreateDatetime(DateTime.now().toDate());
        order.setUpdateDatetime(DateTime.now().toDate());
        order.setDelivered(false);
        order.setPaided(false);
        order.setNote(note);
        order.setUserEntity(user);
        order.setOrderItemEntities(processOrderItem(order,cartItemEntities));
       return orderDao.save(order);
    }
    @Transactional
    public void updateDelivered(String orderId, boolean delivered){

        OrderEntity order = orderDao.getById(orderId);
        if (order == null){
            throw new NotFoundException("Not found order with id " + orderId);
        }
        order.setUpdateDatetime(DateTime.now().toDate());
        order.setDelivered(delivered);
        orderDao.save(order);
    }

    @Transactional
    public void updatePaided(String orderId, boolean paid){

        OrderEntity order = orderDao.getById(orderId);
        if (order == null){
            throw new NotFoundException("Not found order with id " + orderId);
        }
        order.setUpdateDatetime(DateTime.now().toDate());
        order.setPaided(paid);
        orderDao.save(order);
    }

    @Transactional
    public void updateNote(String orderId, String note){

        OrderEntity order = orderDao.getById(orderId);
        if (order == null){
            throw new NotFoundException("Not found order with id " + orderId);
        }
        order.setUpdateDatetime(DateTime.now().toDate());
        order.setNote(note);
        orderDao.save(order);
    }

    @Transactional
    public void updateOrder(String orderId, String username){

        UserEntity user = userDao.getByUsername(username);
        if(user == null){
            throw new NotFoundException(String.format("UserId '%s' is not found", username));
        }

        List<CartItemEntity> cartItemEntities = cartItemDao.getByUerId(user.getId());

        OrderEntity order = orderDao.getById(orderId);
        if (order == null){
            throw new NotFoundException("Not found order with id " + orderId);
        }
        removeOldOrderItemsOfOrder(order,cartItemEntities);

        order.setUpdateDatetime(DateTime.now().toDate());
        order.setOrderItemEntities(processOrderItem(order,cartItemEntities));
        orderDao.save(order);
    }

    private void removeOldOrderItemsOfOrder(OrderEntity order,  List<CartItemEntity> cartItemEntities){
        List<String> items = new ArrayList<>();
        for (CartItemEntity od:cartItemEntities){
            items.add(od.getId().getProductEntity().getId());
        }
        List<String> itemsOld = orderItemDao.getOrderItems(order.getId());
        orderItemDao.removeByIds(TXUtil.leftList(itemsOld, items), order.getId());
    }

    private List<OrderItemEntity> processOrderItem(OrderEntity order, List<CartItemEntity> cartItemEntities){
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        for (CartItemEntity cartItem: cartItemEntities){
            ProductEntity product = productDao.findById(cartItem.getId().getProductEntity().getId());
            if (product != null) {
                OrderItemEntity orderItemEntity = new OrderItemEntity();
                OrderItemProfiles orderItemProfiles = new OrderItemProfiles();
                orderItemProfiles.setOrderEntity(order);
                orderItemProfiles.setProductEntity(product);
                orderItemEntity.setId(orderItemProfiles);
                orderItemEntity.setQuantity(cartItem.getQuantity());
                orderItemEntities.add(orderItemEntity);
            }
        }
       return orderItemEntities;
    }

    @Transactional
    public void deleteOrder(String orderId){
        if(StringUtil.isNullOrEmpty(orderId)){
            log.error("Required orderId must not null or empty");
            throw new BadParameterException("Required orderId must not null or empty");
        }
        OrderEntity orderEntity = orderDao.getById(orderId);
        orderDao.remove(orderEntity);
    }


}
