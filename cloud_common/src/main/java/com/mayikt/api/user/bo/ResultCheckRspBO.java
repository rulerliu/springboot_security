package com.mayikt.api.user.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("检测结果")
public class ResultCheckRspBO {
    @ApiModelProperty(value = "检测结果,true 成功 ,false 失败")
    private Boolean success;
    @ApiModelProperty(value = "缺失目录列表")
    private List<FileDesc> lackDirList;
    @ApiModelProperty(value = "缺失目录数")
    private Integer  lackDirCount;
    @ApiModelProperty(value = "缺失文件列表")
    private List<FileDesc> lackFileList;
    @ApiModelProperty(value = "缺失文件数")
    private Integer  lackFileCount;
    @ApiModelProperty(value = "检测结果描述")
    private String  msg;
    @ApiModelProperty(value = "检测开始时间")
    private Date startTime;
    @ApiModelProperty(value = "检测结束时间")
    private Date endTime;
    @ApiModelProperty(value = "检测耗时")
    private Long costTime;
    @ApiModelProperty(value = "总文件目录数")
    private Integer allFileCount;
    @ApiModelProperty(value = "检测模板ID")
    private Long modelId;
    @ApiModelProperty(value = "检测模板名称")
    private String  modelName;


    public ResultCheckRspBO startCheck(){
        this.setStartTime(new Date());
        this.setSuccess(false);
        this.setLackDirList(new ArrayList<FileDesc>());
        this.setLackDirCount(0);
        this.setLackFileList(new ArrayList<FileDesc>());
        this.setLackFileCount(0);
        this.setAllFileCount(0);
        return this;
    }
    public ResultCheckRspBO endCheck(){
        this.setEndTime(new Date());
        this.setCostTime((this.getEndTime().getTime()-this.getStartTime().getTime())/1000);
        if(!CollectionUtils.isEmpty(lackDirList) || !CollectionUtils.isEmpty(lackFileList)){
            this.setSuccess(false);
            this.setMsg("检测失败");
        }else{
            this.setSuccess(true);
            this.setMsg("检测成功");
        }
        return this;
    }

    public ResultCheckRspBO addErrorDir(String fullPath, String fileName){
        this.lackDirList.add(new FileDesc(fileName,fullPath));
        this.lackDirCount++;
        return this;
    }
    public ResultCheckRspBO addErrorFile(String fullPath, String fileName){
        this.lackFileList.add(new FileDesc(fileName,fullPath));
        this.lackFileCount++;
        return this;
    }

    @ApiModel("文件描述")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileDesc{
        @ApiModelProperty(value = "文件或目录名称")
        private String fileName;
        @ApiModelProperty(value = "文件完整路径")
        private String fullPath;
    }
}
