package io.github.service;

import io.github.mapper.FeedbackTypeMapper;
import io.github.pojo.FeedbackTypeDO;
import org.springframework.stereotype.Service;

@Service
public class FeedbackTypeServiceImpl extends AssistantServiceImpl<FeedbackTypeMapper, FeedbackTypeDO> {
}
