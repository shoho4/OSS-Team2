import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class FileWrite {
	Socket socketFile;
	ClientFrame cf; 
	public FileWrite(ClientFrame cf, Socket socketFile) {		
		this.cf = cf;
		this.socketFile=socketFile;
	}
	public void sendMsg(String fileLocation) {
		//키보드로부터 읽어오기 위한 스트림객체 생성
//		BufferedReader br=
//		new BufferedReader(new InputStreamReader(System.in));
		BufferedOutputStream bout=null;
		FileInputStream fin =null;
		int n=0;
		
		try{
			//서버로 문자열 전송하기 위한 스트림객체 생성
			bout=new BufferedOutputStream(socketFile.getOutputStream());
			
			byte b[] = new byte[255];
						
			//키보드로 문자열 입력받기
			//String str=br.readLine();				
			//if(fileLocation.equals("exit")) break;
			//입력받은 문자열 서버로 보내기						
			fin = new FileInputStream(fileLocation);
			while(true){
				n=fin.read(b);
				if(n==-1){
					//다끝나면 파일전송 완료뿌려주기
					cf.txtA.append("---------파일전송완료--------\n");
					break;
				}
				bout.write(b, 0, n);
//					bout.write(str.getBytes());
				bout.flush();
			}			
			
		
		}catch(FileNotFoundException fe){
			System.out.println(fe.getMessage());
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}finally{
			try{
				//if(br!=null) br.close();
				if(bout!=null)bout.close();
				if(fin != null) fin.close();
				if(socketFile!=null) socketFile.close();
			}catch(IOException ie){
				System.out.println(ie.getMessage());
			}
		}
	}
}
