import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
// Ű����� ���۹��ڿ� �Է¹޾� ������ �����ϴ� ������
class WriteThread{
	Socket socket;
	ClientFrame cf;
	String str;
	String id;
	public WriteThread(ClientFrame cf) {
		this.cf  = cf;
		this.socket= cf.socket;
	}
	public void sendMsg() {
		//Ű����κ��� �о���� ���� ��Ʈ����ü ����
		BufferedReader br=
		new BufferedReader(new InputStreamReader(System.in));
		//PrintWriter pw=null;
		BufferedOutputStream bout=null;
		try{
			//������ ���ڿ� �����ϱ� ���� ��Ʈ����ü ����
			bout=new BufferedOutputStream(socket.getOutputStream());
			//ù��° �����ʹ� id �̴�. ���濡�� id�� �Բ� �� IP�� �����Ѵ�.
			if(cf.isFirst==true){
				InetAddress iaddr=socket.getLocalAddress();				
				String ip = iaddr.getHostAddress();				
				getId();
				System.out.println("ip:"+ip+"id:"+id);
				str = "["+id+"] �� �α��� ("+ip+")"; 
			}else{
				str= "["+id+"] "+cf.txtF.getText();
			}
			//�Է¹��� ���ڿ� ������ ������
			bout.write(str.getBytes());
			//���۰� ���� �ʾƵ� ������ ������ �� �������� �Ѵ�.
			bout.flush();
		
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}finally{
			try{
				if(br!=null) br.close();
				//if(pw!=null) pw.close();
				//if(socket!=null) socket.close();
			}catch(IOException ie){
				System.out.println(ie.getMessage());
			}
		}
	}	
	public void getId(){		
		id = Id.getId(); 
	}
}
//������ ������ ���ڿ��� ���۹޴� ������
class ReadThread extends Thread{
	Socket socket;
	ClientFrame cf;
	public ReadThread(Socket socket, ClientFrame cf) {
		this.cf = cf;
		this.socket=socket;
	}
	public void run() {
		//BufferedReader br=null;
		BufferedInputStream bin = null;
		try{
			
			//�����κ��� ���۵� ���ڿ� �о���� ���� ��Ʈ����ü ����
			bin=new BufferedInputStream(socket.getInputStream());
			byte b[] = new byte[255];
			int n=0;
			String str;
			while(true){
				//�������κ��� ���ڿ� �о��
				n = bin.read(b);
//				if(str==null){
//					System.out.println("������ ������");
//					break;
//				}
				//���۹��� ���ڿ� ȭ�鿡 ���
				//System.out.println("[server] " + str);
				str = new String(b, 0, n);
				cf.txtA.append(str+"\n");
			}
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}finally{
			try{
				if(bin!=null) bin.close();
				if(socket!=null) socket.close();
			}catch(IOException ie){}
		}
	}
}
public class MultiChatClient {
	public static void main(String[] args) {
		Socket socket=null;		 
		ClientFrame cf;
		try{
			socket=new Socket("127.0.0.1",3000);
			System.out.println("ä�ü��� ���Ἲ��!");
			
			cf = new ClientFrame(socket);
			new ReadThread(socket, cf).start();			
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}
	}
}












