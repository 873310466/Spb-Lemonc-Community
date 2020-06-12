package com.lemonfish.entity;

import com.lemonfish.enumcode.CodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * JSON返回结果
 *
 * @author mazc@dibo.ltd
 * @version v2.0
 * @date 2019/01/01
 */
@Data
public class MyJsonResult implements Serializable {
    private static final long serialVersionUID = 873310466L;

    /***
     * 状态码
     */
    private int code;
    /***
     * 消息内容
     */
    private String label;
    /***
     * 返回结果数据
     */
    private Object data;

    /**
     * 返回额外信息
     */
    private Object additionalMsg;


    /**
     * 默认成功，无返回数据
     */
    public MyJsonResult() {
    }


    public static MyJsonResult success(Object data) {
        return MyJsonResult.success(data, null);
    }

    public static MyJsonResult success(Object data, Object additionalMsg) {
        MyJsonResult res = new MyJsonResult();
        res.setCode(CodeEnum.OK.getCode());
        res.setLabel(CodeEnum.OK.getLabel());
        res.setData(data);
        res.setAdditionalMsg(additionalMsg);
        return res;
    }

    public static MyJsonResult fail(CodeEnum codeEnum) {
        return MyJsonResult.fail(codeEnum, null);

    }
    public static MyJsonResult fail(CodeEnum codeEnum, Object additionalMsg) {
        MyJsonResult res = new MyJsonResult();
        res.setCode(codeEnum.getCode());
        res.setLabel(codeEnum.getLabel());
        res.setAdditionalMsg(additionalMsg);
        return res;
    }


    /**
     * 默认成功，有返回数据
     */
    public MyJsonResult(Object data) {
        this.code = CodeEnum.OK.getCode();
        this.label = CodeEnum.OK.getLabel();
        this.data = data;
    }


    /***
     * 非成功，指定状态
     * @param status
     */
    public MyJsonResult(CodeEnum status) {
        this.code = status.getCode();
        this.label = status.getLabel();
        this.data = null;
    }

    /**
     * 非成功，指定状态、返回数据
     *
     * @param status
     * @param data
     */
    public MyJsonResult(CodeEnum status, Object data) {
        this.code = status.getCode();
        this.label = status.getLabel();
        this.data = data;
    }


    /***
     * 自定义JsonResult
     * @param code
     * @param label
     * @param data
     */
    public MyJsonResult(int code, String label, Object data) {
        this.code = code;
        this.label = label;
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "code=" + code +
                ", label='" + label + '\'' +
                ", data=" + data +
                '}';
    }
}
