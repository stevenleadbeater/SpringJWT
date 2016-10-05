package configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by steven on 31/08/16.
 */
public class SecurityHeaderFilters implements Filter {

    @Override
    public void destroy() {
        // Do nothing
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        String jwtToken = request.getHeader("Authorization");

        if (!jwtToken.contains("Bearer ")) {
            throw new SecurityException("incorrect token passed in authorization header");
        } else {
            jwtToken = jwtToken.replace("Bearer ", "");
        }
        Authentication authentication = null;
        authentication = new UserObjectAuthenticationToken(jwtToken, authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // Do nothing
    }

}