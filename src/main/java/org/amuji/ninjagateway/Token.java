package org.amuji.ninjagateway;

public class Token {
    private String id;
    private String userId;
    private String title;
    private String body;

    public String getId() {
        return id;
    }

    public Token setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Token setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Token setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Token setBody(String body) {
        this.body = body;
        return this;
    }
}
