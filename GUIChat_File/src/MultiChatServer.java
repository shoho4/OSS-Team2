import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

//Ŭ���̾�Ʈ�� ���� ���۵� ���ڿ��� �޾Ƽ� �ٸ� Ŭ���̾�Ʈ���� ���ڿ���
//�����ִ� ������
class EchoThread extends Thread{
	Socket socket;
	Vector<Socket> vec;
	public EchoThread(Socket socket, Vector<Socket> vec){
		this.socket = socket;
		this.vec = vec;
	}
	public void run(){
		BufferedInputStream bin=null;
		
		try{
			bin = new BufferedInputStream(socket.getInputStream());
			String str =null;
			
			String sendString= "";
			int n=0;
			while(true){
				//Ŭ���̾�Ʈ�� ���� ���ڿ� �ޱ�								
				byte b[] = new byte[255];
				n=bin.read(b);
			
//				if(n==-1){
//					System.out.println("n=====-1�� ���");
//				}
//				//if(n != -1){
//				str = new String(b, 0, n);
//				System.out.println(str);
//				//}		
				
				
				//���ڿ��� ũ�Ⱑ 255byte�� �Ѿ ��츦 ����ؼ� 
				//���� read()�� ���ڿ��� �����Ѵ�.
			//	sendString += str;					
			
				//��밡 ������ ������ break;
				if(n==-1){
					//���Ϳ��� ���ֱ�
					vec.remove(socket);
					break;
				}
				System.out.println("ä�ü����� sendMsgȣ��");
				//����� ���ϵ��� ���ؼ� �ٸ� Ŭ���̾�Ʈ���� ���ڿ� �����ֱ�
				sendMsg(b);								
			}
			
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}finally{
			try{
				if(bin != null) bin.close();
				if(socket != null) {
					System.out.println("socketclose ȣ��");
					socket.close();
					}
			}catch(IOException ie){
				System.out.println(ie.getMessage());
			}
		}
	}
	
	//���۹��� ���ڿ� �ٸ� Ŭ���̾�Ʈ�鿡�� �����ִ� �޼���
	public void sendMsg(byte b[]){
//		String str1 = new String(b);
//		System.out.println(str1);
		try{
			for(Socket socket:vec){
				//for�� ���� ������ socket�� �����͸� ���� Ŭ���̾�Ʈ�� ��츦 �����ϰ� 
				//������ socket�鿡�Ը� �����͸� ������.
				if(socket != this.socket){
					BufferedOutputStream bout  = new BufferedOutputStream(
							socket.getOutputStream());
					bout.write(b);
					bout.flush();
//					PrintWriter pw = 
//						new PrintWriter(socket.getOutputStream(), true);
//					pw.println(str);
//					pw.flush();
					//��,���⼭ ���� ���ϵ��� ���ǰ͵��̱� ������ ���⼭ ������ �ȵȴ�.
				}
			}
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}
	}
}


public class MultiChatServer {
	public static void main(String[] args) {
		ServerSocket server = null;
		Socket socket =null;
		//Ŭ���̾�Ʈ�� ����� ���ϵ��� �迭ó�� ������ ���Ͱ�ü ����
		Vector<Socket> vec = new Vector<Socket>();
		try{
			server= new ServerSocket(3000);
			while(true){
				System.out.println("ä�� ���Ӵ����..");
				socket = server.accept();
				//Ŭ���̾�Ʈ�� ����� ������ ���Ϳ� ���
				vec.add(socket);
				//������ ����
				new EchoThread(socket, vec).start();
			}
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}
	}
}
