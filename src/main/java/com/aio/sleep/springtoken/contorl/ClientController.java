package com.aio.sleep.springtoken.contorl;

import com.aio.sleep.springtoken.domain.Client;
import com.aio.sleep.springtoken.service.ClientService;
import com.aio.sleep.springtoken.service.OfferService;
import com.aio.sleep.springtoken.utils.ControllerUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 客户端接口
 * 统计客户端信息，下发token
 * **/

@Controller
@RequestMapping("/client")
public class ClientController {

    @Resource
    private OfferService offerService;

    @Resource
    private ClientService clientService;

    private Logger logger = Logger.getLogger(ClientController.class);


    /**
     * 初始化andorid客户端，返回token信息
     * */
    @RequestMapping("/initClient")
    public void initClient(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        logger.info(jsonObject);
        //获取传递过来的参数
        String aid = jsonObject.getString("aid");
        Client client = jsonObject.toJavaObject(Client.class);

        String ip = "";
        String country = "";
        String newToken = "";

        // aid字符串不为null且不为空
        if (aid != null && aid.length() != 0) {
            ip = ControllerUtils.getIpAddress(request);
            if (ip != null) {
                country = offerService.parseIP(ip);

                Client clientOld = clientService.checkClientInfo(aid);
                // Client 对象不存在则插入
                // Client 对象存在，则更新token
                if (null == clientOld) {
                    System.out.println("Clent: " + aid + "不存在");
                    client.setIp(ip);
                    client.setCountry(country);
                    newToken = clientService.insertClient(client);

                }else {
                    System.out.println("Client: " + aid + "存在");
                    clientOld.setIp(ip);
                    clientOld.setIp(country);
                    newToken = clientService.updateClient(clientOld);
                }
            }
        }


        JSONObject res = new JSONObject();
        Long now = Calendar.getInstance().getTimeInMillis();
        res.put("now",now);
        res.put("ip",ip);
        res.put("country",country);
        res.put("token",newToken);
        logger.info(res);
        ControllerUtils.writeObjectJsonResponse(response,res);
    }


    /**
     *解析IP地址的国家代码
     * http://localhost:8080/st/client/ipinfo?ip=8.8.8.8
     */

    @RequestMapping(value = "/ipinfo", method = RequestMethod.GET)
    public void ipInfo(HttpServletRequest request, HttpServletResponse response) {
        String ip = request.getParameter("ip");
        JSONObject res = new JSONObject();
        String country = offerService.parseIP(ip);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String time = format.format(Calendar.getInstance().getTime());
        res.put("ip",ip);
        res.put("country",country.toUpperCase());
        res.put("time",time);
        ControllerUtils.writeObjectJsonResponse(response,res);
    }
}
