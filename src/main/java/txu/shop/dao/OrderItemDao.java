package txu.shop.dao;

import com.amazonaws.util.StringUtils;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import txu.shop.base.BaseDao;
import txu.shop.entity.CartItemEntity;
import txu.shop.entity.OrderItemEntity;
import txu.shop.entity.ProductCategoryEntity;

import java.util.List;

@Repository
public class OrderItemDao extends BaseDao<OrderItemEntity> {
    @Transactional
    public OrderItemEntity save(OrderItemEntity orderItemEntity) {
        if (orderItemEntity.getId().getOrderEntity().getId() == null && orderItemEntity.getId().getProductEntity().getId() ==null) {
            persist(orderItemEntity);
            return orderItemEntity;
        } else {
            return merge(orderItemEntity);
        }
    }


    public OrderItemEntity getByOrderIdProductId(String orderId, String productId){
        String queryString = "SELECT oi FROM OrderItemEntity AS oi WHERE oi.id.orderEntity.id=:orderId AND oi.id.productEntity.id=:productId";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("orderId", orderId);
        query.setParameter("productId", productId);
        return getSingle(query);
    }

    public List<OrderItemEntity> getOrderItemsT(String orderId){
        String queryString = "SELECT oi FROM OrderItemEntity AS oi WHERE oi.id.orderEntity.id=:orderId";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
    public List<String> getOrderItems(String orderId){
        String queryString = "SELECT oi.id.productEntity.id FROM OrderItemEntity AS oi WHERE oi.id.orderEntity.id=:orderId";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    @Transactional
    public void removeByIds(List<String> Ids, String orderId){
        String queryString = "DELETE FROM OrderItemEntity AS oi";
        if (Ids !=null){
            queryString += " WHERE oi.id.productEntity.id IN :Ids AND oi.id.orderEntity.id =:orderId";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("Ids", Ids);
            query.setParameter("orderId", orderId);
            query.executeUpdate();
        }
    }

    @Transactional
    public void removeByOrderIdProductId( String orderId,String productId){
        String queryString = "DELETE FROM OrderItemEntity AS oi";

            queryString += " WHERE oi.id.productEntity.id =:productId AND oi.id.orderEntity.id =:orderId";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("productId", productId);
            query.setParameter("orderId", orderId);
            query.executeUpdate();

    }

//    @Override
//    public OrderItemEntity findById(Object Id) {
//        return super.findById(Id);
//    }

    @Transactional
    public void remove(OrderItemEntity orderItemEntity) {
        orderItemEntity = merge(orderItemEntity);
        getEntityManager().remove(orderItemEntity);
    }
}
