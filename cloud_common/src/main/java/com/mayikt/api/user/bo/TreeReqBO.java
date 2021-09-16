package com.mayikt.api.user.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("树")
public class TreeReqBO implements Serializable {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "父ID")
    @NotNull(message="父ID不能为空")
    private Long parentId;
    @ApiModelProperty(value = "节点名称")
    @NotEmpty(message="节点名称不能为空")
    private String label;
    @ApiModelProperty(value = "结构类型,1-文件夹,2-文件")
//    @FlagValidator(value = {"1","2"}, message = "结构类型不正确")
    private Integer flag;
    @ApiModelProperty(value = "层级")
    @NotNull(message="层级不能为空")
    private Integer level;
    @ApiModelProperty(value = "文件ID")
    private Long fileId;
    @ApiModelProperty(value = "文件名称")
    private String fileName;
}
