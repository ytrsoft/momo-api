package com.ytrsoft.config;

import com.ytrsoft.convert.Convert;
import com.ytrsoft.core.ApiAccess;
import com.ytrsoft.core.Props;
import com.ytrsoft.http.Response;
import com.ytrsoft.http.Request;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class ApiHandler implements MethodInterceptor {

    private static final String BASE_URL = "https://api.immomo.com";
    private final Props props;

    public ApiHandler(Props props) {
        this.props = props;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Method method = invocation.getMethod();
        Request request = method.getAnnotation(Request.class);

        if (request == null) {
            return invocation.proceed();
        }

        String url = BASE_URL + request.value();
        Object[] args = invocation.getArguments();

        boolean hasLogin = method.getName().equals("login");
        boolean rest = args.length > 0 && args[0] instanceof String && !hasLogin;

        if (rest) {
            url = url + "/" + args[0];
        }

        ApiAccess access = new ApiAccess(url, props);

        if (!rest) {
            access.params((JSONObject) args[0]);
            if (args.length > 1) {
                access.body((JSONObject) args[1]);
            }

        }

        JSONObject result;

        if (hasLogin) {
            result = access.doLogin();
        } else {
            result = access.doRequest();
        }

        Response convert = method.getAnnotation(Response.class);
        if (convert != null) {
            Class<?> target = convert.value();
            Convert<?> converter = (Convert<?>) target.getDeclaredConstructor().newInstance();
            return converter.convert(result);
        }

        return result;
    }
}
