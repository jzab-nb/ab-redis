package xyz.jzab.abredis.client;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
public class AbRedisClient {
    private final String host;
    private final Integer port;
    private final String pwd;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    // 建立连接
    public boolean connect(){
        // 1.连接redis
        try {
            socket = new Socket(host,6379);
            // 2.获取输入输出流
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace( );
            return false;
        }
        return true;
    }

    // 关闭连接释放资源
    public boolean close(){
        try {
            if(socket!=null){
                socket.close();
                socket = null;
            }
            if(writer!=null){
                writer.close();
                writer = null;
            }
            if(reader!=null){
                reader.close();
                reader = null;
            }
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public AbRedisClient(String host, Integer port,String pwd){
        this.host = host;
        this.port = port;
        this.pwd = pwd;
    }

    public void info(){
        System.out.println(host+":"+port+":"+pwd);
    }

    private Object handle(String... args){
        connect();
        send(args);
        Object r = recv( );
        close();
        return r;
    }

    private void send(String... args){
        // 3.发出请求
        writer.println( "*"+args.length );
        for (String arg : args) {
            writer.println("$"+arg.getBytes(StandardCharsets.UTF_8).length);
            writer.println(arg);
        }
        writer.flush();
    }

    private Object recv(){
        // 4.解析响应
        try {
            int prefix = reader.read( );
            // 根据前缀判断
            switch (prefix){
                case '+':return reader.readLine();
                case '-':throw new RuntimeException(reader.readLine());
                case ':':return Long.parseLong(reader.readLine());
                case '$':
                    int len = Integer.parseInt(reader.readLine());
                    if(len==0) return "";
                    if(len==-1) return null;
                    // 读取一行
                    return reader.readLine();
                case '*':
                    // 读取数组
                    int arrayLen = Integer.parseInt(reader.readLine());
                    if(arrayLen<=0) return null;
                    // 根据长度初始化列表
                    List<Object> list = new ArrayList<>(arrayLen);
                    for (int count = 0; count < arrayLen; count++) {
                        list.add(recv());
                    }
                    return list;
                default:
                    throw new RuntimeException("错误的响应类型");
            }
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public String ping(){
        return (String) handle("PING");
    }

    public boolean set(String key, String v){
        String r = (String)handle("set", key, v);
        return "OK".equals(r);
    }

    public String get(String key){
        return (String) handle("get",key);
    }


}
