package txu.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchProductRequest {
    List<String> categories;
    String keySearch;
}
