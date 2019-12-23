package hibernate.homework3;

import javax.persistence.*;
import java.util.Date;

public class Room {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "ROOM_NUMBER_OF_GUESTS")
    private int numberOfGuests;

    @Column(name = "ROOM_PRICE")
    private double price;

    @Column(name = "ROOM_BREAKFAST_INCLUDED")
    private int breakfastIncluded;

    @Column(name = "ROOM_PETS_ALLOWED")
    private int petsAllowed;

    @Column(name = "ROOM_DATE_AVAILABLE_FROM")
    @Temporal(value=TemporalType.DATE)
    private Date dateAvailableFrom;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Hotel hotel;

    public Room() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBreakfastIncluded(int breakfastIncluded) {
        this.breakfastIncluded = breakfastIncluded;
    }

    public void setPetsAllowed(int petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public void setDateAvailableFrom(Date dateAvailableFrom) {
        this.dateAvailableFrom = dateAvailableFrom;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public long getId() {
        return id;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public double getPrice() {
        return price;
    }

    public int getBreakfastIncluded() {
        return breakfastIncluded;
    }

    public int getPetsAllowed() {
        return petsAllowed;
    }

    public Date getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    public Hotel getHotel() {
        return hotel;
    }
}
