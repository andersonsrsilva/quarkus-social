package br.com.quarkus.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users_followers")
public class UserFollower {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

}
