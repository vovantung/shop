package txu.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "rate_avg")
public class RateAvg {

    @Id
    @Column(name = "prodcutId")
    @JsonIgnore
    private String productId;

    @OneToOne()
    @MapsId
    @JoinColumn(name = "productId")
    @JsonIgnore
    private ProductEntity productEntity;

    private float avgRate = 0.0f;
}
