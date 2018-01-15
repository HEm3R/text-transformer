package cz.chalupa.examples.texttransformer.filters;

import org.slf4j.MDC;

public class CallContext {

    private static final String CORRELATION_ID_KEY = "correlationId";
    private static final String USER_KEY = "user";

    private static final InheritableThreadLocal<CallContext> currentContext = new InheritableThreadLocal<CallContext>() {

        @Override
        protected CallContext initialValue() {
            return new CallContext();
        }
    };

    public static void clear() {
        currentContext.remove();
        MDC.remove(CORRELATION_ID_KEY);
        MDC.remove(USER_KEY);
    }

    public static CallContext getCurrentContext() {
        return currentContext.get();
    }

    public void setCorrelationId(String correlationId) {
        MDC.put(CORRELATION_ID_KEY, correlationId);
    }

    public void setAuthenticatedUser(String authUser) {
        MDC.put(USER_KEY, authUser);
    }
}
