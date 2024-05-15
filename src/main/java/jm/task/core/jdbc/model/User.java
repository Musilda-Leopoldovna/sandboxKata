package jm.task.core.jdbc.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table (name = "user")
//        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "lastname", "age"}))
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "ID", nullable = false)
    private Long id;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "lastname", nullable = false)
    private String lastName;

    @Column (name = "age", nullable = false)
    private Byte age;

    public User() {

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("User %d: " +
                "{name: '%s', lastName: '%s', age: '%d'}",
                id, name, lastName, age);
    }
}
