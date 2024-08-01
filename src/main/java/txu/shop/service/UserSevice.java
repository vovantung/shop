package txu.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import txu.common.exception.NotFoundException;
import txu.shop.dao.UserDao;
import txu.shop.entity.UserEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSevice {

    private final UserDao userDao;

    @Transactional
    public UserEntity createUser(UserEntity userEntity){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setUpdateDatetime(DateTime.now().toDate());
        userEntity.setCreateDatetime(DateTime.now().toDate());
        return  userDao.save(userEntity);
    }

    @Transactional
    public UserEntity getByUsername(String username){
        UserEntity user = userDao.getByUsername(username);
        if (user == null){
            throw new NotFoundException("User is not found");
        }
        return user;
    }
}
