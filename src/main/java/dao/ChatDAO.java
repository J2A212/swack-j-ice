package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.ChatLog;
import bean.Room;
import exception.SwackException;

/**
 * チャット機能に関するDBアクセスを行う.
 */
public class ChatDAO {

	public Room getRoom(String roomId, String userId) throws SwackException {
		String sqlGetRoom = "SELECT R.ROOMID, R.ROOMNAME, COUNT(*) AS MEMBER_COUNT, R.DIRECTED FROM ROOMS R JOIN JOINROOM J ON R.ROOMID = J.ROOMID WHERE R.ROOMID = ? GROUP BY R.ROOMID, R.ROOMNAME, R.DIRECTED";
		String sqlGetDirectRoom = "SELECT U.USERNAME AS ROOMNAME FROM JOINROOM R JOIN USERS U ON R.USERID = U.USERID WHERE R.USERID <> ? AND R.ROOMID = ?;";

		boolean directed = false;
		String roomName = "";
		int memberCount = 0;

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sqlGetRoom);
			pStmt.setString(1, roomId);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				directed = rs.getBoolean("DIRECTED");
				roomName = rs.getString("ROOMNAME");
				memberCount = rs.getInt("MEMBER_COUNT");
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// for Direct
		if (directed) {
			try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
				PreparedStatement pStmt = conn.prepareStatement(sqlGetDirectRoom);
				pStmt.setString(1, userId);
				pStmt.setString(2, roomId);

				ResultSet rs = pStmt.executeQuery();
				rs.next();
				roomName = rs.getString("ROOMNAME");
				memberCount = 2;

			} catch (SQLException e) {
				throw new SwackException(ERR_DB_PROCESS, e);
			}
		}

		Room room = new Room(roomId, roomName, memberCount, directed);
		return room;
	}

	public ArrayList<Room> getRoomList(String userId) throws SwackException {
		// SQL
		String sql = "SELECT R.ROOMID, R.ROOMNAME FROM JOINROOM J JOIN ROOMS R ON J.ROOMID = R.ROOMID WHERE J.USERID = ? AND R.DIRECTED = FALSE";

		ArrayList<Room> roomlist = new ArrayList<Room>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String roomId = rs.getString("ROOMID");
				String roomName = rs.getString("ROOMNAME");

				Room room = new Room(roomId, roomName);
				roomlist.add(room);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return roomlist;

	}

	public ArrayList<Room> getDirectList(String userId) throws SwackException {
		String sql = "SELECT R.ROOMID, U.USERNAME AS ROOMNAME FROM JOINROOM R JOIN USERS U ON R.USERID = U.USERID WHERE R.USERID <> ? AND ROOMID IN (SELECT R.ROOMID FROM JOINROOM J JOIN ROOMS R ON J.ROOMID = R.ROOMID WHERE J.USERID = ? AND R.DIRECTED = TRUE) ORDER BY R.USERID";

		ArrayList<Room> roomlist = new ArrayList<Room>();

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			pStmt.setString(2, userId);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				String roomId = rs.getString("ROOMID");
				String roomName = rs.getString("ROOMNAME");

				Room room = new Room(roomId, roomName);
				roomlist.add(room);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return roomlist;

	}

	public List<ChatLog> getChatlogList(String roomId) throws SwackException {
		String sql = "SELECT CHATLOGID, U.USERID AS USERID, U.USERNAME AS USERNAME, MESSAGE, CREATED_AT "
				+ "FROM CHATLOG C JOIN USERS U ON C.USERID = U.USERID WHERE ROOMID = ? " + "ORDER BY CREATED_AT ASC";

		List<ChatLog> chatLogList = new ArrayList<ChatLog>();

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);

			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				int chatLogId = rs.getInt("CHATLOGID");
				String userId = rs.getString("USERID");
				String userName = rs.getString("USERNAME");
				String message = rs.getString("MESSAGE");
				Timestamp createdAt = rs.getTimestamp("CREATED_AT");

				ChatLog chatLog = new ChatLog(chatLogId, roomId, userId, userName, message, createdAt);
				chatLogList.add(chatLog);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return chatLogList;

	}

	public void saveChatlog(String roomId, String userId, String message) throws SwackException {
		// SQL準備
		String sql = "INSERT INTO CHATLOG (CHATLOGID, ROOMID, USERID, MESSAGE, CREATED_AT) VALUES (nextval('CHATLOGID_SEQ'), ?, ?, ?,  CURRENT_TIMESTAMP)";

		try {
			Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD);
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);
			pStmt.setString(2, userId);
			pStmt.setString(3, message);

			// SQL実行
			int result = pStmt.executeUpdate();

			if (result == 1) {
				// success
			} else {
				// insert error
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteChatlog(String chatLogId) throws SwackException {
		String sql = "DELETE FROM CHATLOG WHERE CHATLOGID = ?";
		try {
			Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD);
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, chatLogId);

			// SQL実行
			int result = pStmt.executeUpdate();
			if (result == 1) {
				// success
			} else {
				// insert error
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateChatlog(String chatLogId, String message) throws SwackException {
		String sql = "UPDATE CHATLOG SET MESSAGE = ? WHERE CHATLOGID = ?";
		try {
			Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD);
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, message);
			pStmt.setString(2, chatLogId);
			

			// SQL実行
			int result = pStmt.executeUpdate();
			if (result == 1) {
				// success
			} else {
				// insert error
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//チャットIDをもとに、ユーザIDを取得する
	public String getChatUserId(String chatLogId) {
		String sql = "SELECT USERID FROM CHATLOG WHERE CHATLOGID = ?";
		
		String userId = "";
		try {
			Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD);
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, chatLogId);
			
			//SQL実行
			ResultSet rs = pStmt.executeQuery();
			userId = rs.getString("USERID");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
}
