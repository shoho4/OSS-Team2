import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;


public class FileReadThread extends Thread{
	Socket socket;
	public FileReadThread(Socket socket) {
		this.socket=socket;
	}
	public void run() {
		BufferedInputStream br=null;
		FileOutputStream fout = null;
		int n=0;
		String str = null;
		try{
			//서버로부터 전송된 문자열 읽어오기 위한 스트림객체 생성
			br=new BufferedInputStream(socket.getInputStream());
			byte b[] = new byte[255];
			
			fout = new FileOutputStream("c:\\out.txt");
			//소켓으로부터 문자열 읽어옴
			try{
				while(true){					
					n=br.read(b);
					if(n==-1){
						break;
					}
					fout.write(b, 0, n);
					fout.flush();
				}
				System.out.println("파일저장완료!!");
			}catch(FileNotFoundException nfe){
				System.out.println(nfe.getMessage());
			}finally{
				try{						
					if(fout != null) fout.close();
				}catch(IOException ie){}
			}
			
			//str = new String(b, 0, n);

			
			//				if(str==null){
//					System.out.println("접속이 끊겼음");
//					break;
//				}
			
			//전송받은 문자열 화면에 출력
//				System.out.println("[server] " + str);
			
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}finally{
			try{
				if(br!=null) br.close();
				if(fout != null) fout.close();
				if(socket!=null) socket.close();
				
			}catch(IOException ie){}
		}
	}
}
