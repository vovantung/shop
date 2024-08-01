package txu.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

//import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "category")
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdOrGenerated")
    @GenericGenerator(strategy = "org.hibernate.id.UUIDHexGenerator", name = "IdOrGenerated")
    @Column(name = "id")
    private String id;

    private String name;

    private String description;

    private Date createDatetime;

    private Date updateDatetime;

}
