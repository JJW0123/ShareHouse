package com.example.demo.jwt;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.DTO.CustomUserDetails;
import com.example.demo.entity.UserEntity;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // request에서 access token 가져오기
        String token = request.getHeader("access");

        // token 헤더 유무 검증
        if (token == null) {

            // TODO: 토큰 없음 예외처리하기
            // 다음 필터로 전달(권한이 필요없는 요청인 경우)
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 만료 시간 검증
        try {
            jwtUtil.isExpired(token);
        } catch (ExpiredJwtException e) {

            // response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            // reponse status 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // access 토큰인지 검증
        String category = jwtUtil.getCategory(token);

        if (!category.equals("access")) {

            // response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            // response status code 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰에서 id, name, role 가져오기
        String userId = jwtUtil.getId(token);
        String username = jwtUtil.getName(token);
        String role = jwtUtil.getRole(token);

        // userEntity를 생성하여 값 세팅
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setName(username);
        userEntity.setRole(role);

        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());

        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
