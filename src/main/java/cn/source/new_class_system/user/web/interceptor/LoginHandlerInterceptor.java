package cn.source.new_class_system.user.web.interceptor;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.user.entity.User;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

@Component
@Slf4j
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {



        response.setContentType("application/json;charset=utf-8");

        log.debug("测试拦截请求:{}",request.getRequestURI());

        String token  = request.getHeader("token");
        User user = null;
        try {
            user  = (User) redisTemplate.opsForValue().get(token);

        }catch (IllegalArgumentException e){
            throw new GlobalException(ErrorCode.LOGIN_NONE);

        }
        if(user==null){
            throw new GlobalException(ErrorCode.LOGIN_NONE);
        }

//        redisTemplate.boundValueOps(token).expire(30, TimeUnit.MINUTES);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
