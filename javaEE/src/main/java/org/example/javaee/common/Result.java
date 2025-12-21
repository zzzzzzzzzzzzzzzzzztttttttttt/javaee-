package org.example.javaee.common;
//包装类同一后端返回数据类型
public class Result {
    private String code;
    private String message;
    private Object data;

    public static Result success(){
        Result result=new Result();
        result.setCode("200");
        result.setMessage("成功");
        return result;
    }
    public static Result success(Object data){
        Result result=success();
        result.setData(data);
        return result;
    }

    public static Result error(){
        Result result=new Result();
        result.setCode("500");
        result.setMessage("系统错误");
        return result;
    }

    public static Result error(String code, String message){
        Result result=new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public Result(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Result(){

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
