package com.ytrsoft.config;

import com.ytrsoft.convert.IConvert;
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
        ApiAccess access = new ApiAccess(url, props);

        if (args.length > 0) {
            access.params((JSONObject) args[0]);
        }
        if (args.length > 1) {
            access.body((JSONObject) args[1]);
        }

        JSONObject result;

        if (method.getName().equals("login")) {
            result = access.doLogin();
        } else {
            result = access.doRequest();
        }

        Response convert = method.getAnnotation(Response.class);
        if (convert != null) {
            Class<? extends IConvert> target = convert.value();
            IConvert converter = target.getDeclaredConstructor().newInstance();
            result = converter.convert(result);
        }

        return result;
    }
}
