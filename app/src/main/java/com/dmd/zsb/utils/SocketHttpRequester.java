package com.dmd.zsb.utils;

import android.util.Log;

import com.dmd.tutor.utils.OnUploadProcessListener;
import com.dmd.tutor.utils.TLog;
import com.android.volley.FormFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Map;

/**
 * 上传文件到服务器
 *
 * @author Administrator
 *
 */
public class SocketHttpRequester {
    private static SocketHttpRequester socketHttpRequester;

    //文件不存在
    public static final int NOFILE = 0;
    public static final int SUCCESS = 1; //上传成功
    //服务器出错
    public static final int SERVERERROR = 4;
    public static SocketHttpRequester getInstance(){
        if (socketHttpRequester==null){
            socketHttpRequester=new SocketHttpRequester();
        }
        return socketHttpRequester;
    }
    private OnUploadProcessListener onUploadProcessListener;


    public void setOnUploadProcessListener(OnUploadProcessListener onUploadProcessListener) {
        this.onUploadProcessListener = onUploadProcessListener;
    }
    public void uploadFile(String path, Map<String, String> params, FormFile formFile) {
        if (formFile == null) {
            onUploadProcessListener.onUploadDone(NOFILE, "文件不存在");
            return;
        }
        try {
            toUploadFile(path,params,formFile);
        } catch (Exception e) {
            onUploadProcessListener.onUploadDone(NOFILE, "文件不存在");
            e.printStackTrace();
            return;
        }
    }

    public void toUploadFile(final String path,final Map<String, String> params,final FormFile formFile) {

        if (formFile.getFile() == null || (!formFile.getFile().exists())) {
            onUploadProcessListener.onUploadDone(NOFILE, "文件不存在");
            return;
        }

        new Thread(new Runnable() {  //开启线程上传文件

            public void run() {
                //Looper.prepare();
                try {
                    post(path, params,formFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Looper.loop();
            }
        }).start();

    }
    /**
     * 直接通过HTTP协议提交数据到服务器,实现如下面表单提交功能:
     *   <FORM METHOD=POST ACTION="http://192.168.1.101:8083/upload/servlet/UploadServlet" enctype="multipart/form-data">
     <INPUT TYPE="text" NAME="name">
     <INPUT TYPE="text" NAME="id">
     <input type="file" name="imagefile"/>
     <input type="file" name="zip"/>
     </FORM>
     * @param path 上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://www.iteye.cn或http://192.168.1.101:8083这样的路径测试)
     * @param params 请求参数 key为参数名,value为参数值
     * @param files 上传文件
     */
    public  void post(String path, Map<String, String> params, FormFile[] files) throws Exception{
        Log.e("fileEntity",path);
        final String BOUNDARY = "---------------------------7da2137580612"; //数据分隔线
        final String endline = "--" + BOUNDARY + "--\r\n";//数据结束标志

        int fileDataLength = 0;
        for(FormFile uploadFile : files){//得到文件类型数据的总长度
            StringBuilder fileExplain = new StringBuilder();
            fileExplain.append("--");
            fileExplain.append(BOUNDARY);
            fileExplain.append("\r\n");
            fileExplain.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFileName() + "\"\r\n");
            fileExplain.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
            fileExplain.append("\r\n");
            TLog.writeToFile(fileExplain.toString());
            fileDataLength += fileExplain.length();
            if(uploadFile.getInStream()!=null){
                fileDataLength += uploadFile.getFile().length();
            }else{
                fileDataLength += uploadFile.getData().length;
            }
        }
        StringBuilder textEntity = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {//构造文本类型参数的实体数据
            textEntity.append("--");
            textEntity.append(BOUNDARY);
            textEntity.append("\r\n");
            textEntity.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");
            textEntity.append(entry.getValue());
            textEntity.append("\r\n");
        }
        TLog.writeToFile(textEntity.toString());
        //计算传输给服务器的实体数据总长度
        int dataLength = textEntity.toString().getBytes().length + fileDataLength +  endline.getBytes().length;

        URL url = new URL(path);
        int port = url.getPort()==-1 ? 80 : url.getPort();
        Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
        OutputStream outStream = socket.getOutputStream();
        //下面完成HTTP请求头的发送
        String requestmethod = "POST "+ url.getPath()+" HTTP/1.1\r\n";
        outStream.write(requestmethod.getBytes());
        String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
        outStream.write(accept.getBytes());
        String language = "Accept-Language: zh-CN\r\n";
        outStream.write(language.getBytes());
        String contenttype = "Content-Type: multipart/form-data; boundary="+ BOUNDARY+ "\r\n";
        outStream.write(contenttype.getBytes());
        String contentlength = "Content-Length: "+ dataLength + "\r\n";
        outStream.write(contentlength.getBytes());
        String alive = "Connection: Keep-Alive\r\n";
        outStream.write(alive.getBytes());
        String host = "Host: "+ url.getHost() +":"+ port +"\r\n";
        outStream.write(host.getBytes());
        //写完HTTP请求头后根据HTTP协议再写一个回车换行
        outStream.write("\r\n".getBytes());
        //把所有文本类型的实体数据发送出来
        outStream.write(textEntity.toString().getBytes());

        TLog.writeToFile(outStream.toString());
        //把所有文件类型的实体数据发送出来
        for(FormFile uploadFile : files){
            //onUploadProcessListener.initUpload((int)uploadFile.getFile().length());
            StringBuilder fileEntity = new StringBuilder();
            fileEntity.append("--");
            fileEntity.append(BOUNDARY);
            fileEntity.append("\r\n");
            fileEntity.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFileName() + "\"\r\n");
            fileEntity.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
            TLog.writeToFile(fileEntity.toString());
            outStream.write(fileEntity.toString().getBytes());

            onUploadProcessListener.initUpload((int) uploadFile.getFile().length());

            int curLen = 0;
            if(uploadFile.getInStream()!=null){
                int len = 0;
                byte[] buffer = new byte[1024];
                while((len = uploadFile.getInStream().read(buffer, 0, 1024))!=-1){
                    curLen += len;
                    outStream.write(buffer, 0, len);
                    onUploadProcessListener.onUploadProcess(curLen);
                }
                uploadFile.getInStream().close();
            }else{
                outStream.write(uploadFile.getData(), 0, uploadFile.getData().length);
            }
            outStream.write("\r\n".getBytes());
        }
        //下面发送数据结束标志，表示数据已经结束
        outStream.write(endline.getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        if(reader.readLine().indexOf("200")==-1){//读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
            onUploadProcessListener.onUploadDone(SERVERERROR, "文件不存在");
        }
        outStream.flush();
        outStream.close();
        reader.close();
        socket.close();
        onUploadProcessListener.onUploadDone(SUCCESS, "文件SUCCESS");
    }

    /**
     * 提交数据到服务器
     * @param path 上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://www.itcast.cn或http://192.168.1.10:8080这样的路径测试)
     * @param params 请求参数 key为参数名,value为参数值
     * @param file 上传文件
     */
    public  void post(String path, Map<String, String> params, FormFile file) throws Exception{
         post(path, params, new FormFile[]{file});
    }
}