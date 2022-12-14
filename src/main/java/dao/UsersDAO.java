package dao;

import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.Date;
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
public class UsersDAO extends BaseDAO{
	
	public UsersDAO() throws SwackException {
		super();
	}

	/**
	 * メールアドレスとパスワードの引数をもとにユーザ名とユーザIDを取得
	 * @param mailAddress
	 * @param password
	 * @return sUser
	 * @throws SwackException
	 */
	public User select(String mailAddress, String password) throws SwackException {
		// SQL
		String sql = "SELECT USERID, USERNAME FROM USERS WHERE MAILADDRESS = ? AND PASSWORD = ?";

		User user = null;

		// Access DB
		try (Connection conn = dataSource.getConnection()) {

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
		String maxIdNum = null;
		
		// Access DB
		try (Connection conn = dataSource.getConnection()) {

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
		try (Connection conn = dataSource.getConnection()) {
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
	public List<User> selectAll() throws SwackException {
		// SQL
		String sql = "SELECT USERID,USERNAME,MAILADDRESS,PASSWORD FROM USERS ";

		List<User> userList = new ArrayList<User>();

		// Access DB
		try (Connection conn = dataSource.getConnection()) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String userId = rs.getString("USERID");
				String userName = rs.getString("USERNAME");
				String mailAddress =rs.getString("MAILADDRESS");
				String passWord =rs.getString("PASSWORD");
				
				
				User user = new User(userId, userName,mailAddress, passWord);
				userList.add(user);
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返却される）
		return userList;
	}
	public String selectId(String userName) throws SwackException {
		// SQL
		String sql = "SELECT USERID FROM USERS WHERE USERNAME = ?";

		String userId=null;

		// Access DB
		try (Connection conn = dataSource.getConnection()) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userName);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();
			
			// 結果を詰め替え
			if (rs.next()) {
				userId = rs.getString("USERID");
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返却される）
		return userId;
	}
	//メールアドレスをもとにパスワードを取得
	public String getPassword(String mailAddress) throws SwackException {
		// TODO SQL
		String sql = "SELECT PASSWORD FROM USERS WHERE MAILADDRESS = ?";
		String password  = "";
		
		// Access DB
		try (Connection conn = dataSource.getConnection()) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, mailAddress);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				password = rs.getString("PASSWORD");

			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}


		return password;
	}
	//パスワードの失敗回数を1増やす
	public boolean passwordFailCountup(String mailAddress) throws SwackException {
		String sql = "UPDATE USERS SET FAILCOUNT = FAILCOUNT + 1 WHERE MAILADDRESS = ?";
		// Access DB
		try (Connection conn = dataSource.getConnection()) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, mailAddress);

			// SQL実行
			int num = pStmt.executeUpdate();

			// 結果の返却(UPDATEが完了した場合、戻り値TRUE)
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
	public int getFailCount(String mailAddress) throws SwackException {
		int failCount = 0;
		String sql = "SELECT FAILCOUNT FROM USERS WHERE MAILADDRESS = ?";
		// Access DB
		try (Connection conn = dataSource.getConnection()) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, mailAddress);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				failCount = rs.getInt("FAILCOUNT");

			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return failCount;
	}
	//最後にログインした日付を更新
	public boolean loginUpdate(Date loginDate,String mailAddress) throws SwackException {
		String sql = "UPDATE USERS SET LASTROGIN = ? WHERE MAILADDRESS = ?";
		// Access DB
		try (Connection conn = dataSource.getConnection()) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setDate(1, loginDate);
			pStmt.setString(2, mailAddress);

			// SQL実行
			int num = pStmt.executeUpdate();

			// 結果の返却(UPDATEが完了した場合、戻り値TRUE)
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
	//最後にログインした日付を取得
	public Date getLastLogin(String mailAddress) throws SwackException {
		Date lastlogin = null;
		String sql = "SELECT LASTROGIN FROM USERS WHERE MAILADDRESS = ?";
		// Access DB
		try (Connection conn = dataSource.getConnection()) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, mailAddress);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				lastlogin = rs.getDate("LASTROGIN");
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return lastlogin;
	}
	//最後にログインした日付を更新
		public boolean setLastLogin(Date loginDate) throws SwackException {
			String sql = "UPDATE USERS(LASTROGIN) SET LASTROGIN = 0  WHERE MAILADDRESS = ?";
			// Access DB
			try (Connection conn = dataSource.getConnection()) {

				// SQL作成
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setDate(1, loginDate);

				// SQL実行
				int num = pStmt.executeUpdate();

				// 結果の返却(UPDATEが完了した場合、戻り値TRUE)
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
}
