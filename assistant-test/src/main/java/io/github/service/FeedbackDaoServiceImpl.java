package io.github.service;

import io.github.mapper.FeedbackMapper;
import io.github.pojo.FeedbackDO;
import org.springframework.stereotype.Service;

@Service
public class FeedbackDaoServiceImpl extends AssistantMJPServiceImpl<FeedbackMapper, FeedbackDO> {
}
