package xyz.jzab.abredis.client;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
public class AbRedisClient {
    private final String host;
    private final String port;
    private final String pwd;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public AbRedisClient(String host, String port,String pwd){
        this.host = host;
        this.port = port;
        this.pwd = pwd;
        try {
            socket = new Socket(host,6379);
            socket.getInputStream();
            Arrays.stream(new int[1]).max().getAsInt();
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace( );
        }finally {
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace( );
                }
            }
        }
        // 1.连接redis
        // 2.获取输入输出流
        // 3.发出请求
        // 4.解析响应
        // 5.释放连接
    }

    public void info(){
        System.out.println(host+":"+port+":"+pwd);
    }
}
