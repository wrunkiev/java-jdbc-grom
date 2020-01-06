package hibernate.homework4.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ROOMS")
public class Room {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "R_SEQ", sequenceName = "ROOM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "R_SEQ")
    private Long id;

    @Column(name = "ROOM_NUMBER_OF_GUESTS")
    private Integer numberOfGuests;

    @Column(name = "ROOM_PRICE")
    private double price;

    @Column(name = "ROOM_BREAKFAST_INCLUDED")
    private char breakfastIncluded;

    @Column(name = "ROOM_PETS_ALLOWED")
    private char petsAllowed;

    @Column(name = "ROOM_DATE_AVAILABLE_FROM")
    @Temporal(value = TemporalType.DATE)
    private Date dateAvailableFrom;

    @ManyToOne
    @JoinColumn(name = "ROOM_HOTEL_ID", nullable = false)
    private Hotel hotel;

    public Room() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBreakfastIncluded(char breakfastIncluded) {
        this.breakfastIncluded = breakfastIncluded;
    }

    public void setPetsAllowed(char petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public void setDateAvailableFrom(Date dateAvailableFrom) {
        this.dateAvailableFrom = dateAvailableFrom;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Long getId() {
        return id;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public double getPrice() {
        return price;
    }

    public char getBreakfastIncluded() {
        return breakfastIncluded;
    }

    public char getPetsAllowed() {
        return petsAllowed;
    }

    public Date getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    public Hotel getHotel() {
        return hotel;
    }
}
