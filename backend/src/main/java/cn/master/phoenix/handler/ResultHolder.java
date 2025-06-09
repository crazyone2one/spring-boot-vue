package cn.master.phoenix.handler;

import lombok.Data;

/**
 * @author Created by 11's papa on 2025/6/6
 */
@Data
public class ResultHolder {
    // 描述信息，一般是错误信息
    private String message;
    // 详细描述信息, 如有异常，这里是详细日志
    private Object messageDetail;
    // 返回数据
    private Object data = "";
    private int code = 200;
    public ResultHolder() {
    }

    public ResultHolder(Object data) {
        this.data = data;
    }

    public ResultHolder(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
    public ResultHolder(int code, String msg, Object data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public ResultHolder(int code, String msg, Object messageDetail, Object data) {
        this.code = code;
        this.message = msg;
        this.messageDetail = messageDetail;
        this.data = data;
    }

    public static ResultHolder success(Object obj) {
        return new ResultHolder(obj);
    }

    public static ResultHolder error(int code, String message) {
        return new ResultHolder(code, message, null, null);
    }

    public static ResultHolder error(String message, String messageDetail) {
        return new ResultHolder(-1, message, messageDetail, null);
    }

    public static ResultHolder error(int code, String message, Object messageDetail) {
        return new ResultHolder(code, message, messageDetail, null);
    }
    public static ResultHolder successCodeErrorInfo(int code, String message) {
        return new ResultHolder(code, message, null, null);
    }
}
