package util.forJSInterface;


import lombok.Data;

@Data
class JsData {

    //接口类型
    private String type;
    //数据
    private String data;
    //js为app提供的回调方法
    private String callbackMethodForApp;

}
