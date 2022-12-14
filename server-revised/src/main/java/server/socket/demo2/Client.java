package server.socket.demo2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = null;
        int port = 0;
        if(args.length > 2){
            host = args[0];
            port = Integer.parseInt(args[1]);
        }else{
            host = "127.0.0.1";
            port = 9999;
        }
        
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        OutputStream out=null;
        Scanner s = new Scanner(System.in);
        try{
            socket = new Socket(host, port);
            out=socket.getOutputStream();
            String message = null;
            
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), "UTF-8"));
//            writer = new PrintWriter(
//                    socket.getOutputStream(), true);
            while(true){
                message = s.nextLine();
                if(message.equals("exit")){
                    break;
                }
                out.write(message.getBytes("utf-8"));
//                writer.println(message);
//                writer.flush();
                System.out.println("print:"+message);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            socket = null;
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            reader = null;
            if(writer != null){
                writer.close();
            }
            writer = null;
        }
    }
}