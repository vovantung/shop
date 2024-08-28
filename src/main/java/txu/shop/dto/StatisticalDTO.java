package txu.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticalDTO {
    String id;
    String name;
    Object total_mount;
    Object total_quantity;
    public StatisticalDTO(String id,String name, Object total_quantity, Object total_mount){
        this.id = id;
        this.name = name;
        this.total_quantity = total_quantity;
        this.total_mount = total_mount;
    }
}
