package com.aio.sleep.springtoken.service;

import com.aio.sleep.springtoken.domain.Client;

public interface ClientService {
    Client checkClientInfo(String aid);

    String insertClient(Client client);

    String updateClient(Client client);

}