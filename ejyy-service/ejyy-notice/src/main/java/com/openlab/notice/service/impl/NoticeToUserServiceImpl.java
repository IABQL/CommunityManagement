package com.openlab.notice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.notice.Entity.NoticeToUser;
import com.openlab.notice.mapper.NoticeToUserMapper;
import com.openlab.notice.service.NoticeToUserService;
import org.springframework.stereotype.Service;

@Service
public class NoticeToUserServiceImpl extends ServiceImpl<NoticeToUserMapper, NoticeToUser> implements NoticeToUserService {
}
