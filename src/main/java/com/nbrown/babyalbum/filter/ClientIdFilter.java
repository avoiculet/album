package com.nbrown.babyalbum.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.nbrown.babyalbum.util.Constants.CLIENT_ID;

/**
 * Created by avoiculet on 06/03/2019.
 */
@Component
public class ClientIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(((HttpServletRequest)(servletRequest)).getHeader(CLIENT_ID) == null) {
            throw new ServletException("Client id is missing!");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
