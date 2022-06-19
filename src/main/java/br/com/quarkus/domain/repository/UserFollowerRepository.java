package br.com.quarkus.domain.repository;

import br.com.quarkus.domain.model.User;
import br.com.quarkus.domain.model.UserFollower;
import br.com.quarkus.domain.model.UserPost;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UserFollowerRepository implements PanacheRepository<UserFollower> {

    public boolean followers(User follower, User user) {
        List<UserFollower> result = list("follower = ?1 and user = ?2", follower, user);
        return result.isEmpty() ? false : true;
    }

    public List<UserFollower> findByUser(Long userId) {
        return list("user.id = ?1", userId);
    }

    public void deleteFollower(Long followerId, Long userId) {
        delete("follower.id = ?1 and user.id = ?2", followerId, userId);
    }

}
