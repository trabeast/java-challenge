package jp.co.axa.apidemo.controllers.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWithBody<T> extends Response {

    T body;

    public ResponseWithBody(String message, T body) {
        super(message);
        this.body = body;
    }
}
