package hw12.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Expose
    @Column(name = "name", nullable = false)
    private String name;

    @Expose
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Expose
    @Column(name = "password", nullable = false)
    private String password;

    @Expose
    @Column(name = "age")
    private int age;

    @Expose
    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Phone> phones = new ArrayList<Phone>(1);

    public User() {
    }

    public User(long id, String login, String password, String name, int age) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setAddress(Address address) {
        this.address = address;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public User addPhone(Phone phone) {
        phone.setUser(this);
        this.phones.add(phone);
        return this;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
