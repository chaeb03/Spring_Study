package com.example.springjwt.jwt;

import com.example.springjwt.dto.CustomUserDetails;
import com.example.springjwt.entity.UserEntity;
import com.example.springjwt.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public JWTFilter(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Authorization 헤더 검증
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 추출
        String token = authorization.split(" ")[1];

        // 토큰 만료 여부 확인
        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 userId 추출
        String userId = jwtUtil.getUserId(token);  // 메서드명 변경: getUsername → getUserId
        String role = jwtUtil.getRole(token);

        // DB에서 사용자 조회
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        // 인증 객체 생성
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                customUserDetails,
                null,
                customUserDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}