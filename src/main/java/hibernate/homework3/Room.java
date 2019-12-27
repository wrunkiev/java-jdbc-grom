package hibernate.homework3;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ROOMS")
public class Room {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "PR_SEQ", sequenceName = "ROOM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PR_SEQ")
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

    @ManyToOne
    @JoinColumn(name = "ROOM_HOTEL_ID", nullable = false)
    private Hotel hotel;

    public Room() {
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
