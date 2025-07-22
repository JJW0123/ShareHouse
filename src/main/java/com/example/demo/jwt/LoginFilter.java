package com.example.demo.jwt;

import java.io.IOException;
import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.DTO.CustomUserDetails;
import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.RefreshEntity;
import com.example.demo.repository.RefreshRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// 상속받은 filter는 formLogin에서 작동하기에 formLogin이 disable인 지금 상황에서는 커스텀해줘야 사용할 수 있음
// 해당 filter가 "/login" 경로로 오는 POST 요청을 가로채기에 컨트롤러에 따로 매핑할 필요는 없음
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDTO userDTO = objectMapper.readValue(request.getInputStream(), UserDTO.class);

            // request에서 username, password 받아오기
            String username = userDTO.getUserId();
            String password = userDTO.getUserPassword();

            // 받아온 정보를 authenticationManager로 전달
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
                    null);

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO: 없는 아이디일 경우 null 리턴되는거 예외처리하기
        return null;
    }

    // 로그인 성공 메소드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) throws IOException, ServletException {

        // attemptAuthentication에서 전달한 토큰 정보 받아오기
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // username
        String username = customUserDetails.getUsername();
        // userId
        String userId = customUserDetails.getUserId();

        // collection을 Iterator로 순회해서 role 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        // access, refresh 토큰 생성
        String access = jwtUtil.createJwt("access", userId, username, role, 10 * 60 * 1000L);
        String refresh = jwtUtil.createJwt("refresh", userId, username, role, 24 * 60 * 60 * 1000L);

        addRefreshEntity(username, refresh, 24 * 60 * 60 * 1000L);

        // 응답 설정
        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    // DB에 refresh token 저장
    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setHttpOnly(true);
        return cookie;
    }

    // 로그인시 로컬 스토리지에서 jwt 받은다음 로그인 메뉴(등록 하우스 조회, 예약하우스 조회, 예약 등등) 사용 가능 하도록 할 것

    // 로그인 실패 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(401);
    }
}
