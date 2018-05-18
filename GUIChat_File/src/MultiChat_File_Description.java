
public class MultiChat_File_Description {
	/*
	 * 멀티 서버 동작
	 *   1. 루프를 돌면서 계속 리스닝한다.
	 *   2. 새로생성한 소켓들을 Vector에 저장한다.
	 *    - 후에 해당 소켓을 제외한 나머지 소켓에 데이터를 보내주기 위해서
	 *   3. 한명의 클라이언트로부터 입력이 들어오면 해당 클라이언트를 
	 *      제외한 나머지 모든 클라이언트에게 데이터를 보내준다.
	 *      - Vector<Socket>.size()만큼 루프를 돌면서 socket[i] != this.socket
	 *        이 아닌경우에 들어온 데이터를 전달하도록 한다.
	 *   4. 클라이언트가 접속을 끊으면 ((n=in.read(byte b[])==-1)이면 
	 *      Vector에서 해당 소켓을 제거해준다. 
	*/
}
