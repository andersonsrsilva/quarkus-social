package br.com.quarkus.domain.repository;

import br.com.quarkus.domain.model.User;
import br.com.quarkus.domain.model.UserPost;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UserPostRepository implements PanacheRepository<UserPost> {

    public List<UserPost> postsByUser(User user) {
        return list("user = ?1 order by dateTime DESC", user);
    }

}
