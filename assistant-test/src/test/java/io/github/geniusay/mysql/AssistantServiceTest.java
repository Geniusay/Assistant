package io.github.geniusay.mysql;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.pojo.NoticeDO;
import io.github.pojo.vo.NoticeVO;
import io.github.service.FeedbackTypeServiceImpl;
import io.github.service.NoticeDaoServiceImpl;
import io.github.util.PageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AssistantServiceTest {
    @Resource
    NoticeDaoServiceImpl noticeDaoService;

    @Resource
    FeedbackTypeServiceImpl feedbackTypeService;
    @Test
    public void testAssistantService(){
        IPage<NoticeVO> noticeVOIPage = noticeDaoService.BeanPageVOList(1, 1, List.of("notice_id,title,author"),Map.of("notice_id","06dab345-b611-410a-a9d8-980ff11591c4"), NoticeVO.class);
        IPage<NoticeDO> noticeDOIPage = noticeDaoService.BeanPageList(1, 1, List.of("notice_id,title,author"));
//        System.out.println(noticeDaoService.getBean(List.of("notice_id,title"), Map.of("notice_id", "5bcd73d6-8546-42a7-8afe-1819643aac6c")));
//        System.out.println(noticeDaoService.getBeanVO(List.of("notice_id,title"), Map.of("notice_id", "06dab345-b611-410a-a9d8-980ff11591c4"), NoticeVO.class));
//        System.out.println(noticeDaoService.getBeanList(List.of("notice_id,title"), Map.of("author", "RVC社区官方")));
//        System.out.println(noticeDaoService.getBeanVOList(List.of("notice_id,title"), Map.of("author", "RVC社区官方"), NoticeVO.class));
        System.out.println(feedbackTypeService.list());
        System.out.println(PageUtil.toPageVO(noticeVOIPage));
        System.out.println(PageUtil.toPageVO(noticeDOIPage));
    }
}
