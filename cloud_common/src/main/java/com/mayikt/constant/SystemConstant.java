package com.mayikt.constant;

public class SystemConstant {
    public static final Integer ROLE_TYPE_1 = 1;//一级角色 超级管理员
    public static final Integer ROLE_TYPE_2 = 2;//二级角色 区域管理员
    public static final Integer ROLE_TYPE_3 = 3;//三级角色 单位管理员
    public static final Integer ROLE_TYPE_4 = 4;//四级角色 普通员工

    // 终端类型
    public static final Integer TERMINAL_TYPE_1 = 1;// 云端
    public static final Integer TERMINAL_TYPE_2 = 2;// 桌面端
    public static final Integer TERMINAL_TYPE_3 = 3;// 移动端

    /**
     * 菜单类型
     */
    public static final Integer MENU_TYPE_1=1;//目录
    public static final Integer MENU_TYPE_2=2;//菜单
    public static final Integer MENU_TYPE_3=3;//按钮
    public static final Integer LOG_TYPE_SYSTEM = 1;//系统模块
    public static final Integer LOG_TYPE_PROJECT = 2;//项目模块
    public static final Integer LOG_TYPE_STANDER = 3;//标准模块

    public static final Integer NOT_ENABLED = 0;//未启用
    public static final Integer ENABLED = 1;//已启用

    public static final Integer NOT_PUBLISH = 0;//未发布
    public static final Integer PUBLISH = 1;//已发布

    public static final Integer FILE_TYPE_1 = 1;//文档
    public static final Integer FILE_TYPE_2 = 2;//图片
    public static final String METHOD = "METHOD";
    public static final String PATH = "PATH";
    public static final String PARAMS = "PARAMS";
    public static final String BODY = "BODY";
    public static final String MODULE = "MODULE";
    public static final String MEDIATYPE = "MEDIATYPE";
    public static final String TOKEN = "TOKEN";
    public static final String PROJECT = "project";
    public static final String SYSTEM = "system";
    public static final String STANDER = "stander";
    public static final String PAGESIZE = "pageSize";
    public static final String PAGENUM = "pageNum";
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String FILENAME = "fileName";
    public static final String FILEDATA = "fileData";

    public static final String AREA_CODE = "AREA_CODE";
    //是否本人负责
    public static final Integer NOT_SELF = 0;//否
    public static final Integer SELF = 1;//是

    //1文件夹 2文件
    public static final Integer FILE_TYPE_DIR=1;
    public static final Integer FILE_TYPE_FILE=2;

    /**
     * 阶段类型
     */
    public static final Integer STAGE_TYPE_1=1;//可研
    public static final Integer STAGE_TYPE_2=2;//施设
    public static final Integer STAGE_TYPE_3=3;//变更
    public static final Integer STAGE_TYPE_4=4;//竣设

    /**
     * 项目类型
     */
    public static final Integer PROJECT_TYPE_1=1;//可研项目
    public static final Integer PROJECT_TYPE_2=2;//施设项目

    /**
     * 项目状态
     */
    public static final Integer STAGE_STATUS_1=1;//任务待分配
    public static final Integer STAGE_STATUS_2=2;//项目开展中
    public static final Integer STAGE_STATUS_3=3;//成果待提交
    public static final Integer STAGE_STATUS_4=4;//项目评审中
    public static final Integer STAGE_STATUS_5=5;//完结
    public static final Integer STAGE_STATUS_9=9;//删除

    /**
     * 用户类型
     */
    public static final String USER_TYPE_1="1";//设总
    public static final String USER_TYPE_2="2";//成员
    public static final String USER_TYPE_3="3";//设总、成员
    public static final String USER_TYPE_4="4";//都不是

    //项目来源
    public static final Integer PROJECT_SOURCE_TYPE_1 = 1;//1-同步，
    public static final Integer PROJECT_SOURCE_TYPE_2 = 2;//2-本地创建

    // 勘测状态
    public static final Integer STAGE_SURVER_STATUS_1 = 1;//1-未开始，
    public static final Integer STAGE_SURVER_STATUS_2 = 2;//2-勘测中，
    public static final Integer STAGE_SURVER_STATUS_3 = 3;//3-已完成',

    // 项目
    public static final Integer PROJECT_NOT_ENABLED = 1;//废弃
    public static final Integer PROJECT_ENABLED = 0;//有效

    // 任务状态
    public static final Integer TASK_STATUS1 = 1;// 进行中
    public static final Integer TASK_STATUS2 = 2;// 已完成

    //点位状态
    public static final Integer POSITION_STATUS_1 = 0;// 正常
    public static final Integer POSITION_STATUS_2 = 1;// 删除
    public static final Integer POSITION_STATUS_3 = 2;// 撤销

    /**
     * 文件上传状态
     */
    public static final Integer UPLOAD_STATUS_0 = 0;// 待上传
    public static final Integer UPLOAD_STATUS_1 = 1;// 已上传
    public static final Integer UPLOAD_STATUS_2 = 2;// 已完成

    /**
     * 传输类型
     */
    public static final Integer TRANS_TYPE_1 = 1;// 外网到内网OSS
    public static final Integer TRANS_TYPE_2 = 2;// 内网到外网OSS

    /**
     * 设计成果文件状态
     */
    public static final Integer RESULT_FILE_STATUS_0 = 0;// 准备中
    public static final Integer RESULT_FILE_STATUS_1 = 1;// 已生成
    public static final Integer RESULT_FILE_STATUS_2 = 2;// 已下载
    public static final Integer RESULT_FILE_STATUS_3 = 3;// 已过期
    public static final Integer RESULT_FILE_STATUS_4 = 4;// 已通知

    /**
     * 设计成果下载文件类型
     */
    public static final Integer RESULT_FILE_TYPE_1 = 1;// 下载单个文件
    public static final Integer RESULT_FILE_TYPE_2 = 2;// 下载所有设计成果
    public static final Integer RESULT_FILE_TYPE_3 = 3;// 下载部分设计成果

    /**
     * sys_file_transfer所在模块
     */
    public static final long MODEL_1 = 1;// 标准化
    public static final long MODEL_2 = 2;// 项目

    public static final Integer SURVEY_STATUS_1 = 1;// 勘测中
    public static final Integer SURVEY_STATUS_2 = 2;// 已提交
    public static final Integer SURVEY_STATUS_3 = 3;// 已完成
    public static final Integer SURVEY_STATUS_4 = 4;// 已回退

    public static final Integer SURVEY_FLAG_1 = 0;// 是
    public static final Integer SURVEY_FLAG_2 = 1;// 否


}
