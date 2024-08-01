package txu.shop.dao;

import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import txu.shop.base.BaseDao;
import txu.shop.entity.ProductCategoryEntity;

import java.util.List;

@Repository
public class ProductCategoryDao extends BaseDao<ProductCategoryEntity> {
    @Override
    protected ProductCategoryEntity findById(Object Id) {
        return super.findById(Id);
    }

    @Transactional
    public void remove(ProductCategoryEntity productCategoryEntity) {
        productCategoryEntity = merge(productCategoryEntity);
        getEntityManager().remove(productCategoryEntity);
    }

    public List<String> getCategories(String productId){
        String queryString = "SELECT pc.id.categoryEntity.id FROM " + ProductCategoryEntity.class.getName() + " AS pc WHERE pc.id.productEntity.id=:productId";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("productId", productId);
        return query.getResultList();
    }

    @Transactional
    public void removeByIds(List<String> Ids){
        String queryString = "DELETE FROM " + ProductCategoryEntity.class.getName() + " AS pc";
        if (Ids !=null){
            queryString += " WHERE pc.id.categoryEntity.id IN :Ids";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("Ids", Ids);
            query.executeUpdate();
        }
    }
}
