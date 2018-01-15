package cz.chalupa.examples.texttransformer.filters;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

    @NonNull private final Set<String> ignoredPaths;

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return !ignoredPaths.contains(request.getRequestURI());
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        logger.info(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        logger.info(message);
    }
}
