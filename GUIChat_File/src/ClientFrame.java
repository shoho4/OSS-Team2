import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class Id extends JFrame implements ActionListener{
	static JTextField tf=new JTextField(8);
	JButton btn = new JButton("입력");	
	
	WriteThread wt;	
	ClientFrame cf;
	public Id(){}
	public Id(WriteThread wt, ClientFrame cf) {
		super("아이디");		
		this.wt = wt;
		this.cf = cf;
		
		
		setLayout(new FlowLayout());
		add(new JLabel("아이디"));
		add(tf);
		add(btn);
		
		btn.addActionListener(this);
		
		setBounds(300, 300, 250, 100);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btn){
			wt.sendMsg();	
			cf.isFirst = false;
			cf.setVisible(true);
			this.dispose();
		}
	}
	static public String getId(){
		return tf.getText();
	}
}

// 파일을 전송을 위한 GUI 프레임
class FileTransfer extends JFrame implements ActionListener{
	JButton btn = new JButton("전송");
	JTextField tf = new JTextField(10);
	JPanel p = new JPanel();
	Socket socketFile;	
	FileWrite fw ;
	ClientFrame cf;
	public FileTransfer(ClientFrame cf) {
		this.cf = cf;			
		
					
		try{
			socketFile=new Socket("127.0.0.1", 3003);
			System.out.println("파일서버 연결성공!");
			
			new FileReadThread(socketFile).start();
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}		
		
		
		
		fw = new FileWrite(cf, socketFile );		
		
		p.add(new JLabel("파일위치"));
		p.add(tf);
		p.add(btn);
		add(p);
		
		btn.addActionListener(this);		
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
		
		setBounds(200, 200, 200, 200);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(tf.getText().equals("")){
			return;
		}
		if(e.getSource()==btn){
			
			fw.sendMsg(tf.getText());
			this.dispose();		
		}
	}
}



public class ClientFrame extends JFrame implements ActionListener{
	JTextArea txtA = new JTextArea();
	JTextField txtF = new JTextField(15);
	JButton btnTransfer = new JButton("전송");
	JButton btnFileSend = new JButton("파일전송");
	
	JButton btnExit = new JButton("닫기");
	boolean isFirst=true;
	JPanel p1 = new JPanel();
	Socket socket;
	WriteThread wt;
			
	public ClientFrame(Socket socket) {
		super("채팅이나 해볼까");
		this.socket = socket;
		wt = new WriteThread(this);		
		
		new Id(wt, this);
		
		add("Center", txtA);
		
		p1.add(txtF);
		p1.add(btnTransfer);
		p1.add(btnFileSend);
		p1.add(btnExit);
		add("South", p1);
				
		btnFileSend.addActionListener(this);
		btnTransfer.addActionListener(this);
		btnExit.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(300, 300, 450, 300);
		setVisible(false);	
	}
	
	public void actionPerformed(ActionEvent e){
		String id = Id.getId();
		if(e.getSource()==btnTransfer){//전송버튼 눌렀을 경우
			//메세지 입력없이 전송버튼만 눌렀을 경우
			if(txtF.getText().equals("")){
				return;
			}			
			txtA.append("["+id+"] "+ txtF.getText()+"\n");
			wt.sendMsg();
			txtF.setText("");
		}else if(e.getSource()==btnExit){//닫기버튼 눌렸을 경우			
			try{
				if(socket!=null) socket.close();				
			}catch(IOException ie){
				System.out.println(ie.getMessage());
			}
				
			this.dispose();
		}else if(e.getSource() == btnFileSend){
			new FileTransfer(this);
		}
	}
}
