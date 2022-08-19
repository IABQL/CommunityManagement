package com.openlab.notice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.notice.Entity.NoticeTpl;
import com.openlab.notice.mapper.NoticeTplMapper;
import com.openlab.notice.service.NoticeTplService;
import org.springframework.stereotype.Service;

@Service
public class NoticeTplServiceImpl extends ServiceImpl<NoticeTplMapper, NoticeTpl> implements NoticeTplService {
}
