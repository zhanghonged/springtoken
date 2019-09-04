package com.aio.sleep.springtoken.utils;

import com.aio.sleep.springtoken.dao.ClientDao;
import com.aio.sleep.springtoken.domain.Client;
import com.aio.sleep.springtoken.domain.Token;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;


/**
 * tokan验证拦截器，根据客户端请求request里面的token信息，查询后台数据库是否存在，并且和aid可以对应上
 **/
public class APIInterceptor extends HandlerInterceptorAdapter {
    @Resource
    ClientDao clientDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){
        String tokenString = request.getHeader("token");
        String aid = "";
        if (request.getMethod().equals("GET")){
            aid = request.getParameter("aid");
        }else if (request.getMethod().equals("POST")){
            try {
                InputStream is = null;
                is = request.getInputStream();
                String bodyInfo = IOUtils.toString(is,"utf-8");
                JSONObject json = JSONObject.parseObject(bodyInfo);
                aid = json.getString("aid");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (tokenString.length() != 0 && aid.length() != 0) {
           Client client = null;
           client = clientDao.selectClientByAid(aid);
           if (null != client) {
               int id = client.getId();
               Token token = null;
               token = clientDao.selectTokenById(id);
               if (null != token){
                   if (token.getToken().equals(tokenString)) {
                       return true;
                   }
               }
           }
        }

        // token验证不通过返回指定json信息
        // 重置response
        response.reset();
        // 设置编码 格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            JSONObject res = new JSONObject();
            res.put("code",401);
            res.put("message", "token验证失败");
            out = response.getWriter();
            out.append(res.toString());
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}