package com.austin.walletapp.utils;

import com.austin.walletapp.config.MemcachedConfig;
import com.austin.walletapp.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class LocalMemStorage {

    private final MemcachedClient memcachedClient;
    Logger logger = LoggerFactory.getLogger(LocalMemStorage.class);

    public void save(String key,String value, int expiryInSeconds) {

        try {

            memcachedClient.set(key, expiryInSeconds, value);

        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            logger.info("Memcached encountered an error : {}",e.getMessage());
        }

    }

    public void setBlacklist(String token, int expiryInSeconds) {
        try {
            if (!keyExist("Blacklist")) {
                memcachedClient.set("Blacklist", expiryInSeconds, token);

            } else {
                String tokens = this.getValueByKey("Blacklist") + " ," + token;
                memcachedClient.set("Blacklist", expiryInSeconds, tokens);

            }
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            logger.info("Memcached encountered an error : {}", e.getMessage());
        }
    }

    public String getValueByKey(String key) {
        try {
            return memcachedClient.get(key);
        } catch (Exception e) {
            return null;
        }
    }
    public Boolean keyExist(String key) {
        try {
            return memcachedClient.get(key)!=null;
        } catch (Exception e) {
            return null;
        }
    }


    public void clear(String key) {
        try {
            memcachedClient.delete(key);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            logger.info("Memcached encountered an error : {}",e.getMessage());
        }

    }
}
