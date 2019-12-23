package hibernate.homework3;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "HOTELS")
public class Hotel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "HOTEL_NAME")
    private String name;

    @Column(name = "HOTEL_COUNTRY")
    private String country;

    @Column(name = "HOTEL_CITY")
    private String city;

    @Column(name = "HOTEL_STREET")
    private String street;

    //@OneToOne(mappedBy = "HOTELS")
    //private Room room;

    public Hotel() {
    }

    public void setId(long id) {
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

    public long getId() {
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
