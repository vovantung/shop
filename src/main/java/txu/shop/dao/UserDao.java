package txu.shop.dao;

import com.amazonaws.util.StringUtils;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import txu.shop.base.BaseDao;
import txu.shop.entity.UserEntity;

@Repository
public class UserDao extends BaseDao<UserEntity> {
    @Transactional
    public UserEntity save(UserEntity userEntity) {
        if (StringUtils.isNullOrEmpty(userEntity.getId())) {
            persist(userEntity);
            return userEntity;
        } else {
            return merge(userEntity);
        }
    }

    @Override
    public UserEntity findById(Object Id) {
        return super.findById(Id);
    }

    @Transactional
    public void remove(UserEntity userEntity) {
        userEntity = merge(userEntity);
        getEntityManager().remove(userEntity);
    }

    public UserEntity getByUsername(String username){
        StringBuilder queryString = new StringBuilder("SELECT U FROM UserEntity AS U WHERE username=:username");
        Query query = getEntityManager().createQuery(queryString.toString());
        query.setParameter("username", username);
        return getSingle(query);
    }

}
