package br.com.quarkus.rest.dto;

import br.com.quarkus.domain.model.UserPost;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPostResponse {

    private String text;
    private LocalDateTime dateTime;

    public static UserPostResponse fromEntity(UserPost userPost) {
        var response = new UserPostResponse();
        response.setText(userPost.getText());
        response.setDateTime(userPost.getDateTime());
        return response;
    }
}
