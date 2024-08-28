package txu.shop.api;

import com.amazonaws.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import txu.shop.base.BaseApi;
import txu.shop.dto.CreateUpdateProductRequest;
import txu.shop.dto.ProductRequest;
import txu.shop.dto.SearchProductRequest;
import txu.shop.entity.ProductEntity;
import txu.shop.service.ProductService;

import java.util.List;

//@CrossOrigin
//@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductApi extends BaseApi {

    private final ProductService productService;

    @PostMapping(consumes = "application/json")
    public ProductEntity createOrUpdate(@RequestBody CreateUpdateProductRequest createUpdateProductRequest){
        return productService.createOrUpdate(createUpdateProductRequest.getProduct(), createUpdateProductRequest.getCategories());
    }

    @DeleteMapping()
    public void delete(@RequestBody ProductRequest productRequest){
        productService.delete(productRequest.getProductId());
    }

    @PostMapping(value = "/search",  consumes = "application/json")
    public List<ProductEntity> search(@RequestBody SearchProductRequest searchProductRequest) {
        if (StringUtils.isNullOrEmpty(searchProductRequest.getKeySearch())){return null;}
        return filter(searchProductRequest);
    }

    @PostMapping(value = "/filter",  consumes = "application/json")
    public List<ProductEntity> filter(@RequestBody SearchProductRequest searchProductRequest) {
        return productService.searchOrfilter(searchProductRequest.getCategories() , searchProductRequest.getKeySearch());
    }
}
