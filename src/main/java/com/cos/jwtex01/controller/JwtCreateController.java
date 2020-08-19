package com.cos.jwtex01.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwtex01.config.jwt.JwtProperties;
import com.cos.jwtex01.config.oauth.provider.GoogleUser;
import com.cos.jwtex01.config.oauth.provider.OAuthUserInfo;
import com.cos.jwtex01.model.User;
import com.cos.jwtex01.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JwtCreateController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/oauth/jwt/google")
	public String jwtCreate(@RequestBody Map<String,Object> data) {
		System.out.println("jwtCreate 실행됨");
		System.out.println(data.get("profileObj")); //Map으로 함
		OAuthUserInfo googleUser = 
				new GoogleUser((Map<String,Object>)data.get("profileObj"));
		System.out.println(googleUser.getName());
		
		User userEntity 
		= userRepository.findByUsername(googleUser.getProvider()+"_"+googleUser.getProviderId());
		
		if(userEntity == null) {
			//토큰 받았으면 그 정보로 회원가입 진행함
			User userRequest = User.builder()
					.username(googleUser.getProvider()+"_"+googleUser.getProviderId())
					.password(bCryptPasswordEncoder.encode("겟인데어"))
					.email(googleUser.getEmail())
					.provider(googleUser.getProvider())
					.providerId(googleUser.getProviderId())
					.roles("ROLE_USER")
					.build();
			
			//MyBatis면 저장하고 다시 아이디를 찾아야내야한다. JPA는 세이브하면 바로 돌려줌                                            
			userEntity = userRepository.save(userRequest);
		}else {
			
		}
		
		String jwtToken = JWT.create()
				.withSubject(userEntity.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("id", userEntity.getId())
				.withClaim("username", userEntity.getUsername())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		
		return jwtToken;
	}
}
