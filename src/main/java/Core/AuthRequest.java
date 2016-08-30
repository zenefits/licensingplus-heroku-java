package Core;

import java.util.*;

import Core.Utils.CalenderUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by vthiruvengadam on 8/7/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequest {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
