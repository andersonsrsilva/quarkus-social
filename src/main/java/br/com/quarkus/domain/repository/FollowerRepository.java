package br.com.quarkus.domain.repository;

import br.com.quarkus.domain.model.User;
import br.com.quarkus.domain.model.Follower;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

    public boolean followers(User follower, User user) {
        List<Follower> result = list("follower = ?1 and user = ?2", follower, user);
        return result.isEmpty() ? false : true;
    }

    public List<Follower> findByUser(Long userId) {
        return list("user.id = ?1", userId);
    }

    public void deleteFollower(User followerId, User userId) {
        delete("follower = ?1 and user = ?2", followerId, userId);
    }

}
