package txu.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Entity
@Setter
//@Getter
@Table(name = "order_t")
public class OrderEntity implements Serializable {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdOrGenerated")
    @GenericGenerator(strategy = "org.hibernate.id.UUIDHexGenerator", name = "IdOrGenerated")
    @Column(name = "id")
    private String id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;


    private Date createDatetime;


    private Date updateDatetime;

    @Getter
    private Boolean delivered;

    @Getter
    private Boolean paided;
    public String getCreateDatetime(){
        return  createDatetime.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("[dd/MM/yyyy] HH:mm:ss"));
    }

    public String getUpdateDatetime(){
//        return  updateDatetime.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS z"));
        return  updateDatetime.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("[dd/MM/yyyy] HH:mm:ss"));
    }

    @Getter
    private String note;

    @JsonIgnore
    @OneToMany(mappedBy = "id.orderEntity", cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItemEntities;
}
