package com.school.vod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * 功能描述：
 *
 * @Package: com.school.vod
 * @author: Marvin-zl
 * @date: 2022/5/29 18:04
 */

public class TestVod {
    public final static String ACCESS_KEY_ID = "LTAI5tLcD8PnmddQiZFtTwzN";
    public final static String ACCESS_KEY_SECRET = "67yGEqTafg3KNkRmyLue2bEiRhMEUn";


    public static void main(String[] args) throws Exception {
        getPlayAuth();

//        //1.音视频上传-本地文件上传
//        //视频标题(必选)
//        String title = "wuhu111";
//        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
//        //文件名必须包含扩展名
//        String fileName = "C:/Users/msi-pc/Videos/test/6 - What If I Want to Move Faster.mp4";
//        //本地文件上传
//        UploadVideoRequest request = new UploadVideoRequest(ACCESS_KEY_ID, ACCESS_KEY_SECRET, title, fileName);
//        /* 可指定分片上传时每个分片的大小，默认为1M字节 */
//        request.setPartSize(2 * 1024 * 1024L);
//        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
//        request.setTaskNum(1);
//         /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上
//                传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
//                20 注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速
//                度，请您根据实际情况选择是否开启*/
//        request.setEnableCheckpoint(false);
//
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadVideoResponse response = uploader.uploadVideo(request);
//
//        if (response.isSuccess()) {
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//        } else {
//            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//            System.out.print("ErrorCode=" + response.getCode() + "\n");
//            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
//        }


    }

    public static void getPlayAuth() throws Exception {
        DefaultAcsClient client = InitVod.initVodClient("LTAI5tLcD8PnmddQiZFtTwzN", "67yGEqTafg3KNkRmyLue2bEiRhMEUn");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("dac114bb5ee94cde8e486af2885352d4");

        response = client.getAcsResponse(request);
        System.out.println("playAuth:" + response.getPlayAuth());
    }


    public static void getPlayUrl() throws Exception {
        DefaultAcsClient client = InitVod.initVodClient("LTAI5tLcD8PnmddQiZFtTwzN", "67yGEqTafg3KNkRmyLue2bEiRhMEUn");
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        request.setVideoId("aff28927568342d1aae5182829a18718");
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
