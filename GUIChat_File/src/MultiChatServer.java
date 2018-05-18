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

//클라이언트로 부터 전송된 문자열을 받아서 다른 클라이언트에게 문자열을
//보내주는 스레드
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
				//클라이언트로 부터 문자열 받기								
				byte b[] = new byte[255];
				n=bin.read(b);
			
//				if(n==-1){
//					System.out.println("n=====-1이 됬다");
//				}
//				//if(n != -1){
//				str = new String(b, 0, n);
//				System.out.println(str);
//				//}		
				
				
				//문자열의 크기가 255byte를 넘어갈 경우를 대비해서 
				//문자 read()한 문자열을 누적한다.
			//	sendString += str;					
			
				//상대가 접속을 끊으면 break;
				if(n==-1){
					//벡터에서 없애기
					vec.remove(socket);
					break;
				}
				System.out.println("채팅서버의 sendMsg호출");
				//연결된 소켓들을 통해서 다른 클라이언트에게 문자열 보내주기
				sendMsg(b);								
			}
			
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}finally{
			try{
				if(bin != null) bin.close();
				if(socket != null) {
					System.out.println("socketclose 호출");
					socket.close();
					}
			}catch(IOException ie){
				System.out.println(ie.getMessage());
			}
		}
	}
	
	//전송받은 문자열 다른 클라이언트들에게 보내주는 메서드
	public void sendMsg(byte b[]){
//		String str1 = new String(b);
//		System.out.println(str1);
		try{
			for(Socket socket:vec){
				//for를 돌되 현재의 socket이 데이터를 보낸 클라이언트인 경우를 제외하고 
				//나머지 socket들에게만 데이터를 보낸다.
				if(socket != this.socket){
					BufferedOutputStream bout  = new BufferedOutputStream(
							socket.getOutputStream());
					bout.write(b);
					bout.flush();
//					PrintWriter pw = 
//						new PrintWriter(socket.getOutputStream(), true);
//					pw.println(str);
//					pw.flush();
					//단,여기서 얻어온 소켓들은 남의것들이기 때문에 여기서 닫으면 안된다.
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
		//클라이언트와 연결된 소켓들을 배열처럼 저장할 벡터객체 생성
		Vector<Socket> vec = new Vector<Socket>();
		try{
			server= new ServerSocket(3000);
			while(true){
				System.out.println("채팅 접속대기중..");
				socket = server.accept();
				//클라이언트와 연결된 소켓을 벡터에 담기
				vec.add(socket);
				//스레드 구동
				new EchoThread(socket, vec).start();
			}
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}
	}
}
