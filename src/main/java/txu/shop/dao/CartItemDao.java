package txu.shop.dao;

import com.amazonaws.util.StringUtils;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import txu.shop.base.BaseDao;
import txu.shop.entity.CartItemEntity;

import java.util.List;

@Repository
public class CartItemDao extends BaseDao<CartItemEntity> {

    @Transactional
    public CartItemEntity save(CartItemEntity cartItemEntity) {
        if (StringUtils.isNullOrEmpty(cartItemEntity.getId())) {
            persist(cartItemEntity);
            return cartItemEntity;
        } else {
            return merge(cartItemEntity);
        }
    }

    @Override
    public CartItemEntity findById(Object Id) {
        return super.findById(Id);
    }

    @Transactional
    public void remove(CartItemEntity cartItemEntity) {
        cartItemEntity = merge(cartItemEntity);
        getEntityManager().remove(cartItemEntity);
    }

    @Transactional
    public CartItemEntity getByUerIdProductId(String userId, String productId){
        StringBuilder queryString = new StringBuilder("SELECT ci FROM CartItemEntity AS ci WHERE userId=:userId AND productId=:productId");
        Query query = getEntityManager().createQuery(queryString.toString());
        query.setParameter("userId", userId);
        query.setParameter("productId", productId);
        return getSingle(query);
    }

    @Transactional
    public List<CartItemEntity> getByUerId(String userId){
        StringBuilder queryString = new StringBuilder("SELECT ci FROM CartItemEntity AS ci WHERE userId=:userId");
        Query query = getEntityManager().createQuery(queryString.toString());
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Transactional
    public int deleteByUserId(String userId){
        StringBuilder queryString = new StringBuilder("DELETE FROM CartItemEntity AS ci WHERE userId=:userId");
        Query query = getEntityManager().createQuery(queryString.toString());
        query.setParameter("userId", userId);
        var rs = query.executeUpdate();
        return rs;
    }
}
