package br.com.quarkus.rest.dto.response;

import br.com.quarkus.domain.model.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {

    private String text;
    private LocalDateTime dateTime;

    public static PostResponse fromEntity(Post userPost) {
        PostResponse response = new PostResponse();
        response.setText(userPost.getText());
        response.setDateTime(userPost.getDateTime());
        return response;
    }
}
