package hw11.core.model;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "number")
    private String number;

    public Phone() {
    }

    public Phone(int id, String number) {
        this.id = id;
        this.number = number;
    }

    public Phone setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
