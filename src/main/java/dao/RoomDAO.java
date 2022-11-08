package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
				maxIdNum = rs.getString("MAX_USERID");
			}
			return maxIdNum;

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}

	//新しいルーム作成
	public boolean createRoom(String roomId,String roomName,String createUserId,String directed,String privated) throws SwackException {
		// SQL
		String sql = "INSERT INTO ROOMS(ROOMID,ROOMNAME,CREATEDUSERID,DIRECTED,PRIVATED) VALUES (?,?,?,?,?)";
		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);
			pStmt.setString(2, roomName);
			pStmt.setString(3, createUserId);
			pStmt.setString(4, directed);
			pStmt.setString(5, privated);
			
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
		String sql = "INSERT INTO JOINROOM(ROOMID,USERID) VALUES (?,?)";
		
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
}