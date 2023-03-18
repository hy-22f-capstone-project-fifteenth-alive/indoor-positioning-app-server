package net.jaram.indoornavigation.controller;

import lombok.RequiredArgsConstructor;
import net.jaram.indoornavigation.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @GetMapping()
    ResponseEntity<UserResponse> getUserProfile() {
        DefaultOAuth2User user = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserResponse userResponse = UserResponse.builder()
                .name(user.getAttribute("name"))
                .email(user.getAttribute("email"))
                .givenName(user.getAttribute("given_name"))
                .familyName(user.getAttribute("family_name"))
                .picture(user.getAttribute("picture")).build();

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/test")
    String getTest() {
        return  "HELLO!!!";
    }
}
