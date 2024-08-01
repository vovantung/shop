package txu.shop.dao;

import com.amazonaws.util.StringUtils;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import txu.shop.base.BaseDao;
import txu.shop.entity.OrderEntity;

@Repository
public class OrderDao extends BaseDao<OrderEntity> {

    @Transactional
    public OrderEntity save(OrderEntity orderEntity) {
        if (StringUtils.isNullOrEmpty(orderEntity.getId())) {
            persist(orderEntity);
            return orderEntity;
        } else {
            return merge(orderEntity);
        }
    }

    @Override
    public OrderEntity findById(Object Id) {
        return super.findById(Id);
    }
    @Transactional
    public OrderEntity getById(String orderId) {
        String queryString = "SELECT o FROM OrderEntity AS o WHERE o.id=:orderId";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("orderId", orderId);
        return getSingle(query);
    }

    @Transactional
    public void remove(OrderEntity orderEntity) {
        orderEntity = merge(orderEntity);
        getEntityManager().remove(orderEntity);
    }


}
