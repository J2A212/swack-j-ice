package model;

import dao.RoomDAO;
import exception.SwackException;

public class RoomModel {
	public String newRoom(String roomName,String createdUserId,String directed,String privated) throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		String nextRoomId = roomDAO.selectNextRoomId();
		
		// 一度int型にして数字を１足した後、String型にして返す
		int maxIdNum = Integer.parseInt(nextRoomId.substring(1));
		maxIdNum++;
		//roomIdの先頭につく「R」
		nextRoomId = "R";
		//数字の桁数のよって0を付与する
		if (Integer.toString(maxIdNum).length() == 4) { // 桁数４
			nextRoomId += String.valueOf(maxIdNum);
		} else if (Integer.toString(maxIdNum).length() == 3) { // 桁数３
			nextRoomId = maxIdNum + "0" + String.valueOf(maxIdNum);
		} else if (Integer.toString(maxIdNum).length() == 2) { // 桁数２
			nextRoomId = maxIdNum + "00" + String.valueOf(maxIdNum);
		} else if (Integer.toString(maxIdNum).length() == 1) { // 桁数１
			nextRoomId = maxIdNum + "000" + String.valueOf(maxIdNum);
		}
		//room作成
		roomDAO.createRoom(nextRoomId, roomName, createdUserId, directed, privated);
		return nextRoomId;
	}
	public void joinRoom(String roomID,String userId) throws SwackException{
		RoomDAO  roomDAO=new RoomDAO();
		
		roomDAO.joinInsert(roomID, userId);
	}
}
