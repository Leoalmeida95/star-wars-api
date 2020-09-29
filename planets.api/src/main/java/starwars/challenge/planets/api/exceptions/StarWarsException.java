package starwars.challenge.planets.api.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@EqualsAndHashCode
public class StarWarsException extends RuntimeException{
    private final Integer code;
    private final String message;
    private final HttpStatus statusCode;

    public StarWarsException(Integer code, String message, HttpStatus statusCode) {
        super(message);
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "Error{statuscode=" + this.getStatusCode()  + ", message=" + this.getMessage() + "}";
    }

    public Map<String, String> toFormat(){
        Map<String, String> map = new HashMap<>();
        map.put("status", "Error");
        map.put("statuscode", this.getStatusCode().toString());
        map.put("code", this.getCode().toString());
        map.put("message", this.getMessage());

        return map;
    }
}
