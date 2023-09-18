package jp.co.axa.apidemo.controllers.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    String message;

    public Response(String message) {
        this.message = message;
    }
}
