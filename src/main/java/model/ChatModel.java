package model;

import java.util.ArrayList;
import java.util.List;

import bean.ChatLog;
import bean.Room;
import dao.ChatDAO;
import exception.SwackException;

/**
 * チャット機能を実行するクラス
 */
public class ChatModel {

	public Room getRoom(String roomId, String userId) throws SwackException {
		System.out.println("[getRoom] " + roomId + " " + userId);
//		Room room = new Room("R0000", "d_everyone", 4, false);

		ChatDAO chatDAO = new ChatDAO();

		Room room = chatDAO.getRoom(roomId, userId);

		return room;

	}

	public ArrayList<Room> getRoomList(String userId) throws SwackException {
		System.out.println("[getRoomList] " + userId);
//		ArrayList<Room> list = new ArrayList<Room>();
//		list.add(new Room("R0000", "d_everyone", 4, false));
//		list.add(new Room("R0001", "d_ramdom", 3, false));
//		list.add(new Room("R0002", "d_連絡板", 2, false));

		ChatDAO chatDAO = new ChatDAO();

		ArrayList<Room> list = chatDAO.getRoomList(userId);

		return list;

	}

	public ArrayList<Room> getDirectList(String userId) throws SwackException {
		System.out.println("[getDirectList] " + userId);
//		ArrayList<Room> list = new ArrayList<Room>();
//		list.add(new Room("R0003", "d_情報花子", 2, false));
//		list.add(new Room("R0005", "d_情報次郎", 2, false));

		ChatDAO chatDAO = new ChatDAO();

		ArrayList<Room> list = chatDAO.getDirectList(userId);

		return list;

	}

	public List<ChatLog> getChatlogList(String roomId) throws SwackException {
		System.out.println("[getChatlogList] " + roomId);
//		ArrayList<ChatLog> list = new ArrayList<ChatLog>();
//		list.add(new ChatLog(1, "R0000", "U0001", "ダミー太郎", "どうもダミーです", new Timestamp(System.currentTimeMillis())));
//		list.add(new ChatLog(2, "R0000", "U0002", "ダミー花子", "私もダミーです", new Timestamp(System.currentTimeMillis())));
//		list.add(new ChatLog(3, "R0000", "U0003", "ダミー次郎", "奇遇ですね、私もダミーです", new Timestamp(System.currentTimeMillis())));
//		list.add(new ChatLog(4, "R0000", "U0001", "ダミー太郎", "ダミーさんこんにちは", new Timestamp(System.currentTimeMillis())));
//		list.add(new ChatLog(5, "R0000", "U0002", "ダミー次郎", "私は元気です", new Timestamp(System.currentTimeMillis())));

		ChatDAO chatDAO = new ChatDAO();

		List<ChatLog> list = chatDAO.getChatlogList(roomId);

		return list;

	}

	public void saveChatLog(String roomId, String userId, String message) throws SwackException {
		ChatDAO chatDAO = new ChatDAO();
		
		chatDAO.saveChatlog(roomId, userId, message);
	}

}
