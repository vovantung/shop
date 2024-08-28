package txu.shop.dao;

import com.amazonaws.util.StringUtils;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import txu.shop.base.BaseDao;
import txu.shop.entity.ProductEntity;

import java.lang.Object;
import java.util.List;

@Repository
public class ProductDao extends BaseDao<ProductEntity> {

    @Transactional
    public ProductEntity save(ProductEntity productEntity) {
        if (StringUtils.isNullOrEmpty(productEntity.getId())) {
            persist(productEntity);
            return productEntity;
        } else {
            return merge(productEntity);
        }
    }

    @Override
    public ProductEntity findById(Object Id) {
        return super.findById(Id);
    }

    @Transactional
    public void delete(ProductEntity productEntity) {
        remove(productEntity);
    }


    public List<ProductEntity> searchOrfilter(List<String> categories, String keySearch) {

        String queryString = "SELECT p FROM ProductEntity AS p JOIN ProductCategoryEntity AS pc ON p.id = pc.id.productEntity.id";

        Query query;

        if (categories == null || categories.isEmpty()) {
            if (!StringUtils.isNullOrEmpty(keySearch)) {

                queryString += " WHERE p.name LIKE '%" + keySearch + "%'";
                queryString += " UNION SELECT p FROM ProductEntity AS p JOIN ProductCategoryEntity AS pc ON p.id = pc.id.productEntity.id";
                queryString += " WHERE p.description LIKE '%" + keySearch + "%'";
            }
            query = getEntityManager().createQuery(queryString);
        } else {
            queryString += " WHERE pc.id.categoryEntity.id in :categories";
            if (!StringUtils.isNullOrEmpty(keySearch)) {
                queryString += " AND p.name LIKE '%" + keySearch + "%'";
                queryString += " UNION SELECT p FROM ProductEntity AS p JOIN ProductCategoryEntity AS pc ON p.id = pc.id.productEntity.id";
                queryString += " WHERE pc.id.categoryEntity.id in :categories";
                queryString += " AND p.description LIKE '%" + keySearch + "%'";
            }
            query = getEntityManager().createQuery(queryString.toString());
            query.setParameter("categories", categories);
        }

        return getRessultList(query);
    }


}
