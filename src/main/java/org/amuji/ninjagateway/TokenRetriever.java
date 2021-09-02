package org.amuji.ninjagateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "tokenRetriever", url = "https://jsonplaceholder.typicode.com/")
public interface TokenRetriever {
    @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
    Token getToken(@PathVariable("postId") Long postId);
}
