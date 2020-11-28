package electonic.document.management.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.User;
import electonic.document.management.model.Views;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static electonic.document.management.config.filter.FilterConstant.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);

        if (cookie == null) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
            throws JsonProcessingException {
        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
        String token = cookie != null ? cookie.getValue() : null;
        if (token != null) {
            String userJson = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
            User user = objectMapper
                    .readerWithView(Views.IdNameRoles.class)
                    .forType(User.class)
                    .readValue(userJson);
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, user.getRoleSet());
            }
            return null;
        }
        return null;
    }
}
