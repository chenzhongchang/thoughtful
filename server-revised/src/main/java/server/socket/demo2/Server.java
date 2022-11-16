package server.socket.demo2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {

        int port = genPort(args);
        ServerSocket server = null;
        ExecutorService service = Executors.newFixedThreadPool(50);
        try{
            server = new ServerSocket(port);
            System.out.println("server started!");
            while(true){
                Socket socket = server.accept();
                service.execute(new Handler(socket));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(server != null){
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            server = null;
        }
    }
    
    static class Handler implements Runnable{
        Socket socket = null;
        public Handler(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            BufferedReader reader = null;
            PrintWriter writer = null;
            try{
                InputStream in=socket.getInputStream();
                OutputStream out=socket.getOutputStream();
                byte[] b=new byte[1024];
                int len=0;
//                reader = new BufferedReader(
//                        new InputStreamReader(socket.getInputStream(), "UTF-8"));
                writer = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
                String readMessage = null;
                while(true){
                    System.out.println("server reading... ");
                    if((len=in.read(b))!=-1){
//                        out.write(len);
                        System.out.println(new String(b, 0, len));
                    }
//                    if((readMessage = reader.readLine()) == null){
//                        break;
//                    }
//                    System.out.println(readMessage);
//                    writer.println("server recive : " + readMessage);
//                    writer.flush();
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
    
    private static int genPort(String[] args){
        if(args.length > 0){
            try{
                return Integer.parseInt(args[0]);
            }catch(NumberFormatException e){
                return 9999;
            }
        }else{
            return 9999;
        }
    }
    
}