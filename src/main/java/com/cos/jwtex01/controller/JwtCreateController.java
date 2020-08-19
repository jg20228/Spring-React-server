package com.cos.jwtex01.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwtex01.config.jwt.JwtProperties;
import com.cos.jwtex01.config.oauth.provider.GoogleUser;
import com.cos.jwtex01.config.oauth.provider.OAuthUserInfo;

@RestController
public class JwtCreateController {
	
	@PostMapping("/oauth/jwt/google")
	public String jwtCreate(@RequestBody Map<String,Object> data) {
		System.out.println("jwtCreate 실행됨");
		System.out.println(data.get("profileObj")); //Map으로 함
		OAuthUserInfo googleUser = 
				new GoogleUser((Map<String,Object>)data.get("profileObj"));
		System.out.println(googleUser.getName());
		
		String jwtToken = JWT.create()
				.withSubject(googleUser.getProvider()+"_"+googleUser.getProviderId())
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("id", principalDetailis.getUser().getId())
				.withClaim("username", principalDetailis.getUser().getUsername())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		
		return "jwtToken created";
	}
}
