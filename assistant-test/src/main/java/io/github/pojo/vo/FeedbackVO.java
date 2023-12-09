package io.github.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.github.constant.TimeConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackVO {

    /**
     * 反馈ID
     */
    private Long fbid;

    /**
     * 用户uid
     */
    private String uid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 反馈帖子内容
     */
    private String content;

    /**
     * 反馈帖子标题
     */
    private String title;

    /**
     * 帖子类型
     */
    private Integer type;

    private String typeName;

    /**
     * 帖子状态
     */
    private Integer status;

    private String statusName;

    /**
     * 点赞数
     */
    private Long upNum;

    /**
     * 该用户是否对该反馈帖子点赞
     */
    private Boolean hasUp = false;
    /**
     * 评论数
     */
    private Long commentNum;

    private Integer hasShow;

    /**
     * 最近创建时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = TimeConstant.YMD_HMS, timezone = "GMT+8")
    private LocalDateTime createAt;

}
