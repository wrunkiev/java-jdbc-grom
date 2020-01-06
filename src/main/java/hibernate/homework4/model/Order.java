package hibernate.homework4.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "O_SEQ", sequenceName = "ORDER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "O_SEQ")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ORDER_ROOM_ID", nullable = false)
    private Room room;

    @Column(name = "ORDER_DATE_FROM")
    @Temporal(value = TemporalType.DATE)
    private Date dateFrom;

    @Column(name = "ORDER_DATE_TO")
    @Temporal(value = TemporalType.DATE)
    private Date dateTo;

    @Column(name = "ORDER_MONEY_PAID")
    private double moneyPaid;

    public Order() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setMoneyPaid(double moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public double getMoneyPaid() {
        return moneyPaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!user.equals(order.user)) return false;
        return room.equals(order.room);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + room.hashCode();
        return result;
    }
}
