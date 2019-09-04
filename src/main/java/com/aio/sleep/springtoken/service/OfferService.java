package com.aio.sleep.springtoken.service;

import com.aio.sleep.springtoken.domain.Client;


public interface OfferService {
    /**
     * 根据ip解析国家
     * @param ip ip地址 string形式
     * @return 国家代码
     */
    String parseIP(String ip);
    /**
     * 根据Aid和Client对象生成token并入库
     *
    **/
    String generateToken(String aid, Client client);
}