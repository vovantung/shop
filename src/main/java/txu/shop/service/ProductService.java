package txu.shop.service;

import com.amazonaws.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import txu.common.exception.BadParameterException;
import txu.common.exception.NotFoundException;
import txu.shop.dao.CategoryDao;
import txu.shop.dao.ProductCategoryDao;
import txu.shop.dao.ProductDao;
import txu.shop.entity.*;
import txu.shop.util.TXUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final ProductCategoryDao productCategoryDao;
    private final CategoryDao categoryDao;

    @Transactional
    public ProductEntity createOrUpdate(ProductEntity productEntity, List<String> categories) {

        if (StringUtils.isNullOrEmpty(productEntity.getName())) {
            throw new BadParameterException("Product name field is required");
        }

        if (StringUtils.isNullOrEmpty(productEntity.getId())) {
            RateAvg rateAvg = new RateAvg();
            rateAvg.setProductEntity(productEntity);

            productEntity.setUpdateDatetime(DateTime.now().toDate());
            productEntity.setCreateDatetime(DateTime.now().toDate());
            productEntity.setRateAvg(rateAvg);
            productEntity.setProductCategoryEntities(process(productEntity, categories));
            productDao.save(productEntity);
            return  productEntity;
        }

        ProductEntity product = productDao.findById(productEntity.getId());
        if (product == null) {
           throw new NotFoundException("Not found product with id " + productEntity.getId());
        }else {
            removeOldCategoriesOfProduct(product,categories);

            product.setName(productEntity.getName());
            product.setDescription(productEntity.getDescription());
            product.setUpdateDatetime(DateTime.now().toDate());
            product.setProductCategoryEntities(process(product, categories));
            productDao.save(product);
            return  product;
        }
    }

    private void removeOldCategoriesOfProduct(ProductEntity product, List<String> categories){
        List<String> categoriesOld = productCategoryDao.getCategories(product.getId());
        productCategoryDao.removeByIds(TXUtil.leftList(categoriesOld, categories));
    }

    public List<ProductCategoryEntity> process(ProductEntity product, List<String> categories){
        List<ProductCategoryEntity> productCategoryEntities = new ArrayList<>();
        for (String categoryId: categories){
            CategoryEntity category = categoryDao.findById(categoryId);
            if (category != null) {
                ProductCategoryEntity productCategory = new ProductCategoryEntity();
                ProductCategoryProfiles productCategoryProfiles = new ProductCategoryProfiles();
                productCategoryProfiles.setCategoryEntity(category);
                productCategoryProfiles.setProductEntity(product);
                productCategory.setId(productCategoryProfiles);
                productCategoryEntities.add(productCategory);
            }
        }
        return productCategoryEntities;
    }

    public void delete(String productId){
        ProductEntity product = productDao.findById(productId);
        if (product !=null){
            productDao.delete(product);
        }
    }

    public List<ProductEntity> searchOrfilter(List<String> categories, String keySearch) {
        return productDao.searchOrfilter(categories, keySearch);
    }
}
