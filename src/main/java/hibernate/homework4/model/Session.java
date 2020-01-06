package hibernate.homework4.model;

public class Session {
    private Long id;
    private String name;
    private User user;

    public Session(Long id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }
}
