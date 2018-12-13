package com.erminray.polls.payload;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String userType;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken, String userType) {
        this.accessToken = accessToken;
        this.userType = userType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}