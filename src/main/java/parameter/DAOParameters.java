package parameter;

/**
 * データベースアクセス情報管理クラス
 */
public class DAOParameters {
	
	/** コネクションプールJNDI名（tomcatのserver.xmlに詳細設定を記述） */
	public static final String JNDI_NAME = "java:comp/env/jdbc/postgres";

//	/** DBエンドポイント(スキーマ名付き) */
//	public static final String DB_ENDPOINT = "jdbc:postgresql://localhost:5432/postgres";
//	/** DBユーザ */
//	public static final String DB_USERID = "postgres";
//	/** DBパスワード */
//	public static final String DB_PASSWORD = "postgres";

}
