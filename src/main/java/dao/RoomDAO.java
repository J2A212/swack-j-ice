package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Room;
import exception.SwackException;

public class RoomDAO {
	/**
	 * ルーム機能に関するDBアクセスを行う.
	 * 
	 * @throws SwackException
	 */
	//入力したユーザIDが所属しているルームを取得
	public Room select(String userId) throws SwackException {
		// SQL
		String sql = "SELECT R.ROOMID, R.ROOMNAME FROM JOINROOM J JOIN ROOMS R ON J.ROOMID = R.ROOMID WHERE J.USERID = ? AND R.DIRECTED = FALSE";
		Room room = null;

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				String roomId = rs.getString("ROOMID");
				String roomName = rs.getString("ROOMNAME");
				room = new Room(roomId, roomName);
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return room;
	}

	//roomIDの次の候補を取得
	public String selectNextRoomId() throws SwackException {
		// SQL
		String sql = "SELECT MAX(ROOMID) AS MAX_ROOMID FROM ROOMS";
		String maxIdNum = null;

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				maxIdNum = rs.getString("MAX_ROOMID");
				
			}
			return maxIdNum;

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}

	//新しいルーム作成
	public boolean createRoom(String roomId,String roomName,String createdUserId,boolean directed,boolean privated) throws SwackException {
		// SQL
		String sql = "INSERT INTO ROOMS VALUES (?,?,?,?,?)";
		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);
			pStmt.setString(2, roomName);
			pStmt.setString(3, createdUserId);
			pStmt.setBoolean(4, directed);
			pStmt.setBoolean(5, privated);
			System.out.println(roomId+roomName+createdUserId+directed+privated);
			
			// SQL実行
			int num = pStmt.executeUpdate();

			// 結果の返却(INSERTが完了した場合、戻り値TRUE)
			if (num == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
				throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
	
	//joinRoomのINSERT(ルームに新しく参加)
	public boolean joinInsert(String roomid,String userId) throws SwackException {
		//SQL文作成
		String sql = "INSERT INTO JOINROOM (ROOMID,USERID) VALUES (?,?)";
		
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,roomid);
			pStmt.setString(2, userId);
			// SQL実行
			int num = pStmt.executeUpdate();

			// 結果の返却(INSERTが完了した場合、戻り値TRUE)
			if (num == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			// エラー発生時
			throw new SwackException(ERR_DB_PROCESS, e);
		} 
	}
	public List<Room> selectAll() throws SwackException {
		// SQL
		String sql = "SELECT ROOMID,ROOMNAME FROM ROOMS";

		List<Room> roomList = new ArrayList<Room>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String roomId = rs.getString("ROOMID");
				String roomName = rs.getString("ROOMNAME");
				Room room = new Room(roomId,roomName);
				roomList.add(room);
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返却される）
		return roomList;
	}
	public List<Room> selectPublic() throws SwackException {
		// SQL
		String sql = "SELECT ROOMID,ROOMNAME FROM ROOMS";

		List<Room> roomList = new ArrayList<Room>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String roomId = rs.getString("ROOMID");
				String roomName = rs.getString("ROOMNAME");
				Room room = new Room(roomId,roomName);
				roomList.add(room);
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返却される）
		return roomList;
	}
}
