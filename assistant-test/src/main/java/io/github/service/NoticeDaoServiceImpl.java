package io.github.service;

import io.github.mapper.NoticeMapper;
import io.github.pojo.NoticeDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NoticeDaoServiceImpl extends AssistantMJPServiceImpl<NoticeMapper, NoticeDO> {
}
