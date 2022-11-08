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

import bean.User;
import exception.SwackException;

/**
 * ユーザ機能に関するDBアクセスを行う.
 */
public class UsersDAO {

	public User select(String mailAddress, String password) throws SwackException {
		// SQL
		String sql = "SELECT USERID, USERNAME FROM USERS WHERE MAILADDRESS = ? AND PASSWORD = ?";

		User user = null;

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, mailAddress);
			pStmt.setString(2, password);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				String userId = rs.getString("USERID");
				String userName = rs.getString("USERNAME");

				user = new User(userId, userName, mailAddress, "********");
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返却される）
		return user;
	}

	public boolean exists(String mailAddress) throws SwackException {
		// TODO SQL
		String sql = "********";

		// TODO Access DB

		return false;

	}

	public String selectMaxUserId() throws SwackException {
		// SQL
		String sql = "SELECT MAX(USERID) AS MAX_USERID FROM USERS";
		int maxUserId = 0;
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

	public boolean insert(User user) throws SwackException { // ユーザ情報の新規追加
		// SQL作成
		String sql = "INSERT INTO USERS(USERID,USERNAME,MAILADDRESS,PASSWORD) VALUES(?,?,?,?) ";
		// DB接続
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, user.getUserId());
			pStmt.setString(2, user.getUserName());
			pStmt.setString(3, user.getMailAddress());
			pStmt.setString(4, user.getPassword());
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
	public List<String> selectAllName() throws SwackException {
		// SQL
		String sql = "SELECT USERNAME FROM USERS ";

		List<String> namelist = new ArrayList<String>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				
				String userName = rs.getString("USERNAME");
				
				namelist.add(userName);
				
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返却される）
		return namelist;
	}

}
