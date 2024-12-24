package com.project.arbeit.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.project.arbeit.user.User;
import com.project.arbeit.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FirebaseTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final FirebaseAuth firebaseAuth;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 1. Authorization 헤더에서 토큰 추출
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String idToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰

            // 2. Firebase 토큰 검증
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
            String uid = decodedToken.getUid();

            // 3. 사용자 정보 로드 (필요에 따라 UserDetailsService 사용)
            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(uid);
            } catch (Exception e) {
                String email = decodedToken.getEmail();
                String name = decodedToken.getName();
                // DB에 없으면 저장
                userService.save(email, uid, name);
                userDetails = userDetailsService.loadUserByUsername(uid);
            }

            // 4. Spring Security 인증 객체 생성 및 설정
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // 토큰 검증 실패 시 SecurityContext를 비우고 요청 필터링을 계속 진행
            SecurityContextHolder.clearContext();
        }

        // 5. 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}