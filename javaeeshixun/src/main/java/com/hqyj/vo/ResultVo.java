package com.hqyj.vo;

import lombok.Data;

@Data
public class ResultVo {
    private Integer code;
    private String msg;
    private Object data;
    public ResultVo(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    //成功
    public static ResultVo success( String msg,Object data){
        ResultVo resultVo = new ResultVo(200,msg,data);
        return resultVo;
    }
    //失败
    public static ResultVo fail( String msg,Object data){
        ResultVo resultVo = new ResultVo(500,msg,data);
        return resultVo;
    }
}
