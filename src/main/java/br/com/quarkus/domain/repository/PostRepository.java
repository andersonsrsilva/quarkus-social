package br.com.quarkus.domain.repository;

import br.com.quarkus.domain.model.User;
import br.com.quarkus.domain.model.Post;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {

    public List<Post> postsByUser(User user) {
        return list("user = ?1 ORDER BY dateTime DESC", user);
    }

}
