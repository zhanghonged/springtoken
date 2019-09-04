package com.aio.sleep.springtoken.dao;

import com.aio.sleep.springtoken.domain.Client;
import com.aio.sleep.springtoken.domain.Token;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDao {
    // 根据aid查询Client对象
    Client selectClientByAid(String aid);

    // 根据client_id查询Token对象
    Token selectTokenById(int id);

    // 插入client对象
    int insertclient(Client client);

    // 更新client对象
    int updateClient(Client client);

    // 插入token对象
    int insertToken(Token token);

    // 更新token对象
    int updateToken(Token token);
}