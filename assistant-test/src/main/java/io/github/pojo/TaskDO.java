package io.github.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDO {
    private Integer taskId;

    private String taskName;

    private String taskDescription;
}
