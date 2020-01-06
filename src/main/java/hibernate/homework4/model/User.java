package hibernate.homework4.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "U_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "U_SEQ")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "USER_COUNTRY")
    private String country;

    @Column(name = "USER_TYPE_ID")
    private UserType userType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Order> orders;

    public User() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getCountry() {
        return country;
    }

    public UserType getUserType() {
        return userType;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userName.equals(user.userName);
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }
}
