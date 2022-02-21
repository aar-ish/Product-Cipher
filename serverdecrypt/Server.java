import ProductCipher.cipher;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server{
	private static DataInputStream dataInputStream =null;
	private static DataOutputStream dataOutputStream = null;
	public static void main(String[] args) throws FileNotFoundException, IOException, Exception{
		try(ServerSocket serversocket = new ServerSocket(12345))
		{
			System.out.println("Listening to client at port 12345...");
			Socket clientSocket = serversocket.accept();
			System.out.println("Connected to "+clientSocket+"\n/--------------/");
			
			dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
			
			
			String fileName = "message";
			PrintWriter write = new PrintWriter("message.txt");
			String dir = System.getProperty("user.dir");
			
			int bytes=0;
			FileOutputStream fileOutputStream = new FileOutputStream("message.txt");
			long size = dataInputStream.readLong(); //reads file size
			byte[] buffer = new byte[4*1024];
			while(size>0 &&(bytes=dataInputStream.read(buffer,0,(int)Math.min(buffer.length, size)))!=-1)
			{
				fileOutputStream.write(buffer,0,bytes);
				size-=bytes; //reads upto files size
			}
			fileOutputStream.close();
			dataInputStream.close();
			dataOutputStream.close();
			clientSocket.close();	
			
			System.out.println("To decrypt");
		
			//copy file content in a variable			
			
				fileName = fileName + ".txt";
				dir = dir +"\\"+fileName;

			cipher data = new cipher();
			String bus = data.readFileAsString(dir);
			//server side
			System.out.print("Enter key: ");
			int key = new Scanner(System.in).nextInt();
			
			String plaintxt = data.decrypt(bus.toString(), key);
			//save plaintxt in same file
			write.print(plaintxt);
			write.close();
		}
		catch(Exception e){
		System.out.println(e.toString());}
			
	}
}