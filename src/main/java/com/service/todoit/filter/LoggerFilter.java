package com.service.todoit.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LoggerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper((HttpServletResponse) response);


        chain.doFilter(req, res);

        // request log : uri, method, headers, body
        String uri = req.getRequestURI();
        String method = req.getMethod();
        StringBuilder headerValues = new StringBuilder();

        req.getHeaderNames().asIterator().forEachRemaining(headerKey ->{
            String headerValue = req.getHeader(headerKey);

            headerValues.append("[").append(headerKey).append(" : ").append(headerValue).append("] ");
        });

        String requestBody = new String(req.getContentAsByteArray());

        log.info(">>>> uri : {} , method : {} , header : {}, body : {}",uri, method, headerValues, requestBody);


        // response log : uri, method, headers, body

        StringBuilder responseHeaderValues = new StringBuilder();

        res.getHeaderNames().forEach(headerKey->{
            String headerValue = res.getHeader(headerKey);

            responseHeaderValues.append("[").append(headerKey).append(" : ").append(headerValue).append("] ");
        });

        String responseBody = new String(res.getContentAsByteArray());

        log.info("<<<<< uri : {} , method : {} , header : {} , body : {}",uri, method, responseHeaderValues, responseBody);
        res.copyBodyToResponse();
    }
}
