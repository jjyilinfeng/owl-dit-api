package com.ylf.util;


/**
 * @author Administrator
 */
public class ReturnData {



    /**
     * 状态码
     */
    private int code;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 返回信息
     */
    private String message;

    public ReturnData() {
    }

    public ReturnData(Object data) {
        this.data = data;
    }

    public ReturnData(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public ReturnData(int code,  String message) {
        this.code = code;
        this.message = message;
    }

    public ReturnData(int code, Object data, String message) {

        this.code = code;
        this.data = data;
        this.message = message;
    }

//    public ReturnData(StatusCode userResponseEnum,Object data,String message){
//        this.data = data;
//        this.message = message;
//        this.code = userResponseEnum.getCode();
//    }
//
//    public ReturnData success(){
//        this.code = HttpStatus.OK.value();
//        this.data = true;
//        this.message = "请求成功";
//        return this;
//    }
//
//    public ReturnData success(Object data){
//        this.code = HttpStatus.OK.value();
//        this.data = data;
//        this.message = "请求成功";
//        return this;
//    }
//
//    public ReturnData error(){
//        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
//        this.data = false;
//        this.message = "请求失败";
//        return this;
//    }

    public ReturnData error(Object data,String message){
        this.code = 500;
        this.data = data;
        this.message = message;
        return this;
    }

    /*set get方法*/

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    //----------
}
