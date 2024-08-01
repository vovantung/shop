package txu.shop.dao;

import com.amazonaws.util.StringUtils;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import txu.shop.base.BaseDao;
import txu.shop.entity.OrderItemEntity;
import txu.shop.entity.ProductCategoryEntity;

import java.util.List;

@Repository
public class OrderItemDao extends BaseDao<OrderItemEntity> {
//    @Transactional
//    public OrderItemEntity save(OrderItemEntity orderItemEntity) {
//        if (StringUtils.isNullOrEmpty(orderItemEntity.getId())) {
//            persist(orderItemEntity);
//            return orderItemEntity;
//        } else {
//            return merge(orderItemEntity);
//        }
//    }

    public List<String> getOrderItems(String orderId){
        String queryString = "SELECT oi.id.orderEntity.id FROM OrderItemEntity AS oi WHERE oi.id.orderEntity.id=:orderId";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    @Transactional
    public void removeByIds(List<String> Ids){
        String queryString = "DELETE FROM OrderItemEntity AS oi";
        if (Ids !=null){
            queryString += " WHERE oi.id.orderEntity.id IN :Ids";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("Ids", Ids);
            query.executeUpdate();
        }
    }

    @Override
    public OrderItemEntity findById(Object Id) {
        return super.findById(Id);
    }

    @Transactional
    public void remove(OrderItemEntity orderItemEntity) {
        orderItemEntity = merge(orderItemEntity);
        getEntityManager().remove(orderItemEntity);
    }
}
