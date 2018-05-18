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
		//Ű����κ��� �о���� ���� ��Ʈ����ü ����
//		BufferedReader br=
//		new BufferedReader(new InputStreamReader(System.in));
		BufferedOutputStream bout=null;
		FileInputStream fin =null;
		int n=0;
		
		try{
			//������ ���ڿ� �����ϱ� ���� ��Ʈ����ü ����
			bout=new BufferedOutputStream(socketFile.getOutputStream());
			
			byte b[] = new byte[255];
						
			//Ű����� ���ڿ� �Է¹ޱ�
			//String str=br.readLine();				
			//if(fileLocation.equals("exit")) break;
			//�Է¹��� ���ڿ� ������ ������						
			fin = new FileInputStream(fileLocation);
			while(true){
				n=fin.read(b);
				if(n==-1){
					//�ٳ����� �������� �Ϸ�ѷ��ֱ�
					cf.txtA.append("---------�������ۿϷ�--------\n");
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
