package util.forJSInterface.Camera;

import util.forJSInterface.JsData;

public interface CameraService {
    //获取摄像头数据
    String getCamera(JsData d);

    //获取相册数据
    String getAlbum(JsData d);
}
