package cn.source.new_class_system.user.web.interceptor;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.user.entity.PowerName;
import cn.source.new_class_system.user.service.IPowerNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IPowerNameService iPowerNameService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求uri
        String uri = request.getRequestURI();
        //1.通过查询数据库权限列表判断该路径是否需要权限
        List<PowerName> powerList = iPowerNameService.list(null);
        String powerString = isNeedVerify(uri, powerList);

        if(powerString!=null){
            //2.如果需要需要权限操作，通过redis查询用户拥有的权限
            List<PowerName> powerNameList = (List<PowerName>)redisTemplate.opsForValue().get("power:" + request.getHeader("token"));
            //判断如果redis中查询不到用户的权限信息则抛出异常无权限
            if(powerNameList==null){
                throw new GlobalException(ErrorCode.NONE_POWER);
            }
            //3.查询请求路径需要的权限,于用户的权限集合进行比对是否有符合要求的权限如果有则通过
            Optional<PowerName> first = powerNameList.stream()
                    .filter(e -> e.getPowerName().equals(powerString))
                    .findFirst();

            if(!first.isPresent()){
                throw new GlobalException(ErrorCode.NONE_POWER);
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /** @Description 查询该uri是否需要验证，需要则返回需要验证的验证字符串 **/
    public String isNeedVerify(String uri,List<PowerName> list){
        Optional<PowerName> first = list.stream().filter(e -> {
            int i = uri.indexOf(e.getUrl());
            return i != -1;
        }).findFirst();
        if(first.isPresent()){
            PowerName powerName = first.get();
            return powerName.getPowerName();
        }
        return null;


    }
}
