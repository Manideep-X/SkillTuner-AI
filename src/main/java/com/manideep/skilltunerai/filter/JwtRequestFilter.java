package com.manideep.skilltunerai.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.manideep.skilltunerai.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    
    public JwtRequestFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        String username = null;
        String jwtToken = null;
        
        final Cookie[] cookies = request.getCookies();

        // If the header is not empty and the list of cookies have JWT cookie, then get the token and the username.
        if (cookies != null) {
            // The word Bearer automatically gets added in front of the token in the request header. Which means, the one who have the token can only access the contents.
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
            if (jwtToken != null) {
                username = jwtUtil.getUsername(jwtToken);
            }
        }

        // need to check if the user is already authenticated to avoid auth override during authentication.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Fetches user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if (jwtUtil.isTokenValid(jwtToken, userDetails)) {
                
                // After checking the validity of the token, a auth token is created with all necessary details(username, password, authorities). 
                // Passing null in the credentical parameter, as its not needed after authentication for security reasons.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Adds request specific metadata
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Sets the authentication info into the security context to make user appeared logged-in
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        
        // This will pass and add the request and response as a filter to the filter chain
        filterChain.doFilter(request, response);

    }



}
