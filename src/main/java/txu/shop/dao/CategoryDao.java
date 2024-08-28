package txu.shop.dao;

import com.amazonaws.util.StringUtils;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import txu.shop.base.BaseDao;
import txu.shop.entity.CategoryEntity;

//import javax.persistence.Query;
import java.util.List;

@Repository
public class CategoryDao extends BaseDao<CategoryEntity> {

    @Transactional
    public CategoryEntity save(CategoryEntity categoryEntity) {
        if (StringUtils.isNullOrEmpty(categoryEntity.getId())) {
            persist(categoryEntity);
            return categoryEntity;
        } else {
            return merge(categoryEntity);
        }
    }

    @Override
    public CategoryEntity findById(Object Id) {
        return super.findById(Id);
    }

    @Transactional
    public void remove(CategoryEntity categoryEntity) {
        categoryEntity = merge(categoryEntity);
        getEntityManager().remove(categoryEntity);
    }

    @Transactional
    public List<CategoryEntity> getAll(){
        StringBuilder queryString = new StringBuilder("SELECT c FROM CategoryEntity AS c");
        Query query = getEntityManager().createQuery(queryString.toString());
        var rs =query.getResultList();
        return rs;

    }
}
