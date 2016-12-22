package org.robferguson.resteasy.examples.fatjar.swagger;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

@Singleton
final class ApiOriginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}

// https://github.com/swagger-api/swagger-core/issues/997
// https://gist.github.com/ben-manes/af6ab5e857f29f55d990
// https://github.com/gwizard/gwizard/blob/master/gwizard-swagger/src/main/java/org/gwizard/swagger/ApiOriginFilter.java