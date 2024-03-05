package com.example.demo.util;

public class Constant {
    // 任务广场一次展示页面中包含任务的数量
    public static final Integer TASK_PAGE_SIZE = 6;
    // 报告浏览界面一次展示页面中包含报告的数量
    public static final Integer REPORT_PAGE_SIZE = 6;
    // 请求操作成功返回码
    public static final Integer REQUEST_SUCCESS = 0;
    // 请求操作失败返回码
    public static final Integer REQUEST_FAIL = 1;
    // 任务状态完成
    public static final Integer TASK_FINISHED = 0;
    // 任务状态未完成
    public static final Integer TASK_UNFINISHED = 1;
    // 推荐任务列表大小
    public static final Integer TASK_RECOMMEND = 6;

    // 完成任务提交报告增加的活跃度
    public static final Integer REPORT_INCREASE = 3;
    // 提交补充说明报告增加的活跃度
    public static final Integer SUPPLEMENT_INCREASE = 2;
    // 报告评价增加的活跃度
    public static final Integer COMMENT_INCREASE = 1;

    public static final String[] PREFERENCES = new String[]{"功能测试","性能测试","漏洞扫描","用例执行","安全性测试"};

    public static final String[] EQUIPMENTS = new String[]{"安卓","ios","linux","window","鸿蒙"};
}
