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
        if (cartItemEntity.getId().getProductEntity().getId() == null && cartItemEntity.getId().getUserEntity().getId() ==null) {
            persist(cartItemEntity);
            return cartItemEntity;
        } else {
            return merge(cartItemEntity);
        }
    }

//    @Override
//    public CartItemEntity findById(Object Id) {
//        return super.findById(Id);
//    }

    @Transactional
    public void remove(CartItemEntity cartItemEntity) {
        cartItemEntity = merge(cartItemEntity);
        getEntityManager().remove(cartItemEntity);
    }

    @Transactional
    public CartItemEntity getByUerIdProductId(String userId, String productId){
        String queryString = "SELECT ci FROM CartItemEntity AS ci WHERE ci.id.userEntity.id =:userId AND ci.id.productEntity.id =:productId";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("userId", userId);
        query.setParameter("productId", productId);
        return getSingle(query);
    }

    @Transactional
    public List<CartItemEntity> getByUerId(String userId){
        String queryString = "SELECT ci FROM CartItemEntity AS ci WHERE ci.id.userEntity.id=:userId";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Transactional
    public int deleteByUserId(String userId){
        String queryString = "DELETE FROM CartItemEntity AS ci WHERE ci.id.userEntity.id =:userId";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("userId", userId);
        var rs = query.executeUpdate();
        return rs;
    }

    @Transactional
    public int deleteByUserIdAndProductId(String userId, String productId){
        String queryString = "DELETE FROM CartItemEntity AS ci WHERE ci.id.userEntity.id=:userId AND ci.id.productEntity.id=:productId";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("userId", userId);
        query.setParameter("productId", productId);
        return query.executeUpdate();
    }
}
