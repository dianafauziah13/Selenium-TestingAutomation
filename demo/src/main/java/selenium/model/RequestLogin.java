package selenium.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestLogin {
        @JsonProperty("email")
        public String email;

        @JsonProperty("password")
        public String password;

        public RequestLogin(String email, String password) {
            this.email = email;
            this.password = password;
        }
}
