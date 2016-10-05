package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by steven on 05/10/16.
 */
public class JWTToken {

    public JWTToken(String token){
        set_token(token);
    }

    private String token;

    @JsonProperty("token")
    public String get_token() {
        return token;
    }

    public void set_token(String token) {
        this.token = token;
    }
}
