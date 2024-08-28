package txu.shop.dao;

import com.amazonaws.util.StringUtils;
import jakarta.persistence.Query;
import org.apache.http.client.utils.DateUtils;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import txu.shop.base.BaseDao;
import txu.shop.dto.StatisticalDTO;
import txu.shop.entity.OrderEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class OrderDao extends BaseDao<OrderEntity> {

    public List<OrderEntity> getByUserId(String userId, boolean completed){
        Query query;
        String queryString = "SELECT o FROM OrderEntity AS o WHERE o.userEntity.id=:userId";
        queryString = queryString + " AND  o.delivered=:delivered AND o.paided=:paided";
        queryString += " ORDER BY o.createDatetime DESC";
        query = getEntityManager().createQuery(queryString);
        query.setParameter("delivered", true);
        query.setParameter("paided", false);
        query.setParameter("userId", userId);

        return getRessultList(query);
    }

    public List<OrderEntity> getByUserId_(String userId, Date from, Date to, boolean isAllOrder){
        String queryString = "select * from order_t " +
                " where userId = :userId" +
                " and createDatetime > :from" +
                " and createDatetime < :to";

        if(isAllOrder){
                queryString = queryString + " ORDER BY createDatetime DESC";
        }else {
                queryString = queryString +
                        " and paided = false" +
                        " and delivered = false" +
                        " union" +
                        " select * from order_t" +
                        " where userId = :userId" +
                        " and paided = false" +
                        " and delivered = true" +
                        " and createDatetime > :from " +
                        " and createDatetime <  :to" +
                        " ORDER BY createDatetime DESC";
        }

        Query query = getEntityManager().createNativeQuery(queryString, OrderEntity.class);
        query.setParameter("userId", userId);
        query.setParameter("from", from);
        query.setParameter("to", to);
        return getRessultList(query);
    }

public List<StatisticalDTO> statistical(Date from, Date to){
        String queryString = "select p.id, p.name, sum(oi.quantity * p.price) as total_mount, sum(oi.quantity) as total_quantity from order_t as o" +
                " join order_item as oi on o.id = oi.orderId" +
                " join product as p on p.id = oi.productId" +
                " where o.createDatetime > :from" +
                " and o.createDatetime <  :to" +
                " group by oi. productId" +
                " order by total_quantity desc";

        Query query = getEntityManager().createNativeQuery(queryString, StatisticalDTO.class);
        query.setParameter("from", from);
        query.setParameter("to", to);
    var rs = query.getResultList();;
        return rs;
    }

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
