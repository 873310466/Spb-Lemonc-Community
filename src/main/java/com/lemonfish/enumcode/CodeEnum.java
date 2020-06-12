package com.lemonfish.enumcode;

/**
 * @author Masics 张超
 * @date 2020/2/26 0:54
 */
public enum CodeEnum {
    /***
     * 请求处理成功
     */
    OK(200, "操作成功"),


    /***
     * 部分成功（一般用于批量处理场景，只处理筛选后的合法数据）
     */
    WARN_PARTIAL_SUCCESS(1001, "部分成功"),

    /***
     * 有潜在的性能问题
     */
    WARN_PERFORMANCE_ISSUE(1002, "潜在的性能问题"),

    /***
     * 没有权限执行该操作
     */
    FAIL_NEED_LOGIN(4000, "请先进行登录"),

    /***
     * 帐号或密码错误
     */
    FAIL_LOGIN_ERROR(4001, "帐号或密码错误"),

    /***
     * Token无效或已过期
     */
    FAIL_INVALID_TOKEN(4002, "Token无效或已过期"),

    /***
     * 没有权限执行该操作
     */
    FAIL_NO_PERMISSION(4003, "无权执行该操作"),

    /***
     * 请求资源不存在
     */
    FAIL_NOT_FOUND(4004, "请求资源不存在"),

    /***
     * 数据校验不通过
     */
    FAIL_VALIDATION(4005, "数据校验不通过"),

    /***
     * 操作执行失败
     */
    FAIL_OPERATION(4006, "操作执行失败"),

    /***
     * 传入参数不对
     */
    FAIL_INVALID_PARAM(4007, "请求参数不匹配"),

    /**
     * 30分钟内登录失败次数过错，该帐号暂被冻结
     */
    ACCOUNT_LOCKED(4008, "30分钟内登录失败次数过多，该帐号暂被冻结"),

    /**
     * 读取消息通知失败
     */
    READ_NOTIFICATION_FAIL(4009, "不可眼读取其他人的小爱惜喔"),
    /**
     * 帐号不存在
     */
    ACCOUNT_NOT_EXISTED(4010, "帐号不存在"),
    /**
     * 昵称需要填写
     */
    NAME_NEEDED(4011, "昵称需要填写"),
    /**
     * 昵称需要填写
     */
    USERINFO_DUMPLICATION(4012, "用户信息重复(大概率邮箱重复)，请修改"),
    /***
     * Token无效或已过期
     */
    FAIL_NEED_TOKEN(4013, "请求头需要携带token"),

    /***
     * 分页参数错误
     */
    FAIL_PAGE_PARAMS(4014, "分页参数错误"),

    /**
     * 邮箱重复
     */
    DUMPLICATED_EMAIL(4015,"该邮箱重复了欸/(ㄒoㄒ)/~~"),


    /***
     * 分页参数错误
     */
    DELETE_OK(200, "删除成功"),

    /***
     * 系统异常
     */
    FAIL_EXCEPTION(5000, "系统异常"),


    /***
     * 严重警告
     */
    DANGEROUS_WARNING(9999, "严重警告！！！禁止动歪脑子，再次尝试将进行封号处理");



    CodeEnum() {
    }

    CodeEnum(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    private int code;
    private String label;
}
