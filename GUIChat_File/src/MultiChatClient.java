import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
// 키보드로 전송문자열 입력받아 서버로 전송하는 스레드
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
		//키보드로부터 읽어오기 위한 스트림객체 생성
		BufferedReader br=
		new BufferedReader(new InputStreamReader(System.in));
		//PrintWriter pw=null;
		BufferedOutputStream bout=null;
		try{
			//서버로 문자열 전송하기 위한 스트림객체 생성
			bout=new BufferedOutputStream(socket.getOutputStream());
			//첫번째 데이터는 id 이다. 상대방에게 id와 함께 내 IP를 전송한다.
			if(cf.isFirst==true){
				InetAddress iaddr=socket.getLocalAddress();				
				String ip = iaddr.getHostAddress();				
				getId();
				System.out.println("ip:"+ip+"id:"+id);
				str = "["+id+"] 님 로그인 ("+ip+")"; 
			}else{
				str= "["+id+"] "+cf.txtF.getText();
			}
			//입력받은 문자열 서버로 보내기
			bout.write(str.getBytes());
			//버퍼가 차지 않아도 버퍼의 내용을 내 보내도록 한다.
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
//서버가 보내온 문자열을 전송받는 스레드
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
			
			//서버로부터 전송된 문자열 읽어오기 위한 스트림객체 생성
			bin=new BufferedInputStream(socket.getInputStream());
			byte b[] = new byte[255];
			int n=0;
			String str;
			while(true){
				//소켓으로부터 문자열 읽어옴
				n = bin.read(b);
//				if(str==null){
//					System.out.println("접속이 끊겼음");
//					break;
//				}
				//전송받은 문자열 화면에 출력
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
			System.out.println("채팅서버 연결성공!");
			
			cf = new ClientFrame(socket);
			new ReadThread(socket, cf).start();			
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}
	}
}












