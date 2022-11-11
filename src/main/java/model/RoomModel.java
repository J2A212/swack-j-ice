package model;

import dao.RoomDAO;
import exception.SwackException;

public class RoomModel {
	public String newRoom(String roomName,String createdUserId,boolean directed,boolean privated) throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		String nextRoomId = roomDAO.selectNextRoomId();
		
		// 一度int型にして数字を１足した後、String型にして返す
		int maxIdNum = Integer.parseInt(nextRoomId.substring(1,5));
		maxIdNum++;
		
		//数字の桁数のよって0を付与する
		if (maxIdNum < 10) {
			nextRoomId = "R000" + maxIdNum;
		} else if (maxIdNum < 100) {
			nextRoomId = "R00" + maxIdNum;
		} else if (maxIdNum < 1000) {
			nextRoomId = "R0" + maxIdNum;
		} else {
			nextRoomId = "R" + maxIdNum;
		}
		System.out.println("あああ");
		//room作成
		roomDAO.createRoom(nextRoomId, roomName, createdUserId, directed, privated);
		return nextRoomId;
	}
	public void joinRoom(String roomID,String userId) throws SwackException{
		RoomDAO  roomDAO=new RoomDAO();
		
		roomDAO.joinInsert(roomID, userId);
	}
}
