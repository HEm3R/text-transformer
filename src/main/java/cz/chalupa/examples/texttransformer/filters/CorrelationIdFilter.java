package cz.chalupa.examples.texttransformer.filters;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID_HEADER = "X-CorrelationId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String correlationId = createCorrelationId();
            CallContext.getCurrentContext().setCorrelationId(correlationId);
            response.setHeader(CORRELATION_ID_HEADER, correlationId);
            chain.doFilter(request, response);
        } finally {
            CallContext.clear();
        }
    }

    private String createCorrelationId() {
        return UUID.randomUUID().toString();
    }
}
