package com.aio.sleep.springtoken.service.impl;

import com.aio.sleep.springtoken.dao.ClientDao;
import com.aio.sleep.springtoken.domain.Client;
import com.aio.sleep.springtoken.domain.Token;
import com.aio.sleep.springtoken.service.OfferService;
import com.aio.sleep.springtoken.utils.ControllerUtils;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
public class OfferServiceImpl implements OfferService {

    @Resource
    ClientDao clientDao;

    private static DatabaseReader reader;

    {
        try {
            String path = this.getClass().getResource("/GeoLite2-Country.mmdb").getPath();
            reader = new DatabaseReader.Builder(new File(path)).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String parseIP(String ip) {
        String areacode = null;
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CountryResponse response = reader.country(ipAddress);

            Country country = response.getCountry();
            areacode = country.getIsoCode().toLowerCase();
            return areacode;
        } catch (AddressNotFoundException n) {
            System.out.println(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String generateToken(String aid, Client client) {
        String salt = ControllerUtils.createSalt(8);
        String tokenString = ControllerUtils.createMd5(aid,salt);
        Token token = null;
        int id = client.getId();
        token = clientDao.selectTokenById(id);
        // 如果token对象不存在，则创建
        // 如果token对象存在则进行更新
        if (null == token) {
            Token token_new = new Token(tokenString,salt,id);
            System.out.println("token对象不存在，创建");
            clientDao.insertToken(token_new);
        }else {
            token.setToken(tokenString);
            token.setSalt(salt);
            token.setClient_id(id);
            System.out.println("token对象存在，更新");
            clientDao.updateToken(token);
        }

        return tokenString;
    }
}
