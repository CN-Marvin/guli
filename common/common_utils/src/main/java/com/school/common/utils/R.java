package com.school.common.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：统一返回结果的类
 *
 * @Package: com.school.common.utils
 * @author: Marvin-zl
 * @date: 2022/5/23 16:40
 */
@Data
public class R {
    @ApiModelProperty("是否成功")
    private boolean success;
    @ApiModelProperty("状态码")
    private Integer code;
    @ApiModelProperty("返回信息")
    private String message;
    @ApiModelProperty("返回数据")
    private Map<String, Object> data = new HashMap<>();

    private R() {
    }

    /**
     * @return 成功方法
     */
    public static R ok() {
        R ok = new R();
        ok.setSuccess(true);
        ok.setCode(ResultCode.SUCCESS);
        ok.setMessage("成功");
        return ok;
    }

    public static R error() {
        R error = new R();
        error.setSuccess(false);
        error.setCode(ResultCode.ERROR);
        error.setMessage("失败");
        return error;
    }

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}

