package io.github.pojo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

/**
 * FeedbackDO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "rvc_feedback" )
public class FeedbackDO {

    /**
     * 反馈ID
     */
    @TableId(value = "fb_id")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Long fbid;

    /**
     * 用户uid
     */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String uid;

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

    /**
     * 帖子状态
     */
    private Integer status;

    /**
     * 点赞数
     */
    private Long upNum;

    /**
     * 评论数
     */
    private Long commentNum;

    private Integer hasShow;

    /**
     * 最近创建时间
     */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = TimeConstant.YMD_HMS, timezone = "GMT+8")
    private LocalDateTime createAt;

    /**
     * 最近更新时间
     */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = TimeConstant.YMD_HMS, timezone = "GMT+8")
    private LocalDateTime updateAt;
}
