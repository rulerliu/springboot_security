package com.mayikt.api.user.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor()
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgressTreeRspBO implements Serializable {
    @ApiModelProperty(value = "进度ID")
    private Long progressId;

    @ApiModelProperty(value = "父进度ID")
    private Long parentId;

    @ApiModelProperty(value = "进度类型，1-阶段，2-任务")
    private Integer progressType;

    @ApiModelProperty(value = "进度名称")
    private String progressName;

    @ApiModelProperty(value = "开始日期,yyyy-MM-dd")
    private String startDate;

    @ApiModelProperty(value = "结束日期,yyyy-MM-dd")
    private String endDate;

    @ApiModelProperty(value = "天数")
    private Integer days;

    @ApiModelProperty(value = "状态，1-未开始，2-进行中，3-已完成")
    private Integer status;

    private List<ProgressTreeRspBO> children;

}

