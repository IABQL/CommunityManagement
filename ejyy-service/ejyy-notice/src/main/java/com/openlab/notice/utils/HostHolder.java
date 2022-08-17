package com.openlab.notice.utils;

import org.springframework.stereotype.Component;

/**
 * 持有用户信息
 */
@Component
public class HostHolder {
    private ThreadLocal<Long> users = new ThreadLocal<>();

    public void setUserId(Long userId){
        users.set(userId);
    }

    public Long getUserId(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}