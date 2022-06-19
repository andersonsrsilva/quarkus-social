package br.com.quarkus.domain.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

}
