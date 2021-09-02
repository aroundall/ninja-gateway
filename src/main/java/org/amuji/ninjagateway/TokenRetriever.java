package org.amuji.ninjagateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "tokenService", url = "${token-service.url}")
public interface TokenRetriever {
    @RequestMapping(method = RequestMethod.POST, value = "/tokens")
    String getToken(@RequestBody Credential credential);
}
class Credential {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public Credential setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Credential setPassword(String password) {
        this.password = password;
        return this;
    }
}
