package model;

import java.util.List;

import bean.Room;
import dao.RoomDAO;
import dao.UsersDAO;
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
	public List<Room> getRoomNameList() throws SwackException{
		RoomDAO roomDAO = new RoomDAO();
		List<Room> roomList = roomDAO.selectAll();
		return roomList;
	}
	public List<Room> getPublicRoomNameList() throws SwackException{
		RoomDAO roomDAO = new RoomDAO();
		List<Room> roomList = roomDAO.selectPublic();
		return roomList;
	}
	public String getUserId(String userName)throws SwackException {
		String userId=null;
		
		UsersDAO userDAO=new UsersDAO();
		userId=userDAO.selectId(userName);
		return userId;
	}
	public String getRoomId(String roomName) throws SwackException {
		String roomId=null;
		
		RoomDAO roomDAO=new RoomDAO();
		roomId=roomDAO.getRoomId(roomName);
		return roomId;
	}
	public String getCreatedUserId(String roomId) throws SwackException {
		String createdUserId = "";
		
		RoomDAO roomDAO=new RoomDAO();
		createdUserId = roomDAO.getCreatedUserId(roomId);
		return createdUserId;
	}
}
