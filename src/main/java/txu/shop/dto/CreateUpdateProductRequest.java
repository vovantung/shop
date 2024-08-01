package txu.shop.dto;

import lombok.Getter;
import lombok.Setter;
import txu.shop.entity.ProductEntity;

import java.util.List;

@Getter
@Setter
public class CreateUpdateProductRequest {
    List<String> categories;
    ProductEntity product;
}
