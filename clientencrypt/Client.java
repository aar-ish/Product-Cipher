import ProductCipher.cipher;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    private static DataInputStream datainputstream = null;
    private static DataOutputStream dataoutputstream = null;
    public static void main(String[] args)throws FileNotFoundException, IOException, Exception, IndexOutOfBoundsException {
        

        try (Socket socket = new Socket("localhost",12345)){
            datainputstream = new DataInputStream(socket.getInputStream());
            dataoutputstream = new DataOutputStream(socket.getOutputStream()); 
            
            int key;
            System.out.println("Enter Input to be encrypted: ");
            String Input = new Scanner(System.in).nextLine();
            System.out.print("Enter key: ");
            key = new Scanner(System.in).nextInt();
            cipher code = new cipher();
            String sub = code.encrypt(Input, key);
            //save sub in file
            PrintWriter write = new PrintWriter("message.txt");
            write.print(sub);
            write.close();

            int bytes =0;
            String dir = System.getProperty("user.dir");
            File file = new File(dir+"\\message.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            
            dataoutputstream.writeLong(file.length()); //send file size
           
           
            byte []buffer =new byte[4*1024];
            while((bytes=fileInputStream.read(buffer))!=-1)
            {
                dataoutputstream.write(buffer,0,bytes);          
                dataoutputstream.flush();
            }
            fileInputStream.close();
            datainputstream.close();
            dataoutputstream.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}