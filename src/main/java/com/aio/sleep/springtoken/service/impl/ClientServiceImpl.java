package com.aio.sleep.springtoken.service.impl;

import com.aio.sleep.springtoken.dao.ClientDao;
import com.aio.sleep.springtoken.domain.Client;
import com.aio.sleep.springtoken.service.ClientService;
import com.aio.sleep.springtoken.service.OfferService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClientServiceImpl implements ClientService {

    @Resource
    ClientDao clientDao;

    @Resource
    OfferService offerService;

    @Override
    public Client checkClientInfo(String aid) {
        return clientDao.selectClientByAid(aid);
    }

    @Override
    public String insertClient(Client client) {
        clientDao.insertclient(client);
        return offerService.generateToken(client.getAid(),client);
    }

    @Override
    public String updateClient(Client client) {
        clientDao.updateClient(client);
        return offerService.generateToken(client.getAid(),client);
    }
}