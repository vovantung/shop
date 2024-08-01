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
@Table(name = "user")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdOrGenerated")
    @GenericGenerator(strategy = "org.hibernate.id.UUIDHexGenerator", name = "IdOrGenerated")
    @Column(name = "id")
    private String id;

    private String username;

    private String password;

    private String lastName;

    private  String firstName;

    private String phoneNumber;

    private String email;

    private Date createDatetime;

    private Date updateDatetime;
}
