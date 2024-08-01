package txu.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import txu.shop.dao.CategoryDao;
import txu.shop.entity.CategoryEntity;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;

    @Transactional
    public List<CategoryEntity> getAll(){
        return categoryDao.getAll();
    }
}
