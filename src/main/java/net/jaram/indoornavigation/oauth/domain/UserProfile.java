package net.jaram.indoornavigation.oauth.domain;

public record UserProfile(String oauthId, String name, String email, String imageUrl) {
    public Member toMember() {
        return new Member(oauthId, name, email, imageUrl, Role.USER);
    }
}