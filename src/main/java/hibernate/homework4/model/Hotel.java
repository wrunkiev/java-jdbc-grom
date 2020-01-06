package hibernate.homework4.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "HOTELS")
public class Hotel {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "H_SEQ", sequenceName = "HOTEL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "H_SEQ")
    private Long id;

    @Column(name = "HOTEL_NAME")
    private String name;

    @Column(name = "HOTEL_COUNTRY")
    private String country;

    @Column(name = "HOTEL_CITY")
    private String city;

    @Column(name = "HOTEL_STREET")
    private String street;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Room> rooms;

    public Hotel() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hotel hotel = (Hotel) o;

        return name.equals(hotel.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
