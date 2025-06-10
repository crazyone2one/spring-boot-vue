package cn.master.phoenix.exception;

/**
 * @author Created by 11's papa on 2025/6/10
 */
public interface IResultCode {
    /**
     * 返回状态码
     */
    int getCode();

    /**
     * 返回状态码信息
     */
    String getMessage();
}
