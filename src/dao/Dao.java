package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Dao {

	private Connection con;
	private String sql;
	
	/**
	 * コンストラクタ
	 * @throws SQLException
	 */
	public Dao() throws SQLException{
		// DBを指定
		String url= "jdbc:mysql://localhost:3306/webapi?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
		// ログイン情報
		String user = "root";
		String pass = "root";
		// DBへ接続
		con = DriverManager.getConnection(url, user, pass);
		System.out.println("Connection success!");
	}
	
	/**
	 * DBとの接続を閉じる
	 */
	public void close() {
		try {
			// 接続を閉じる
			if(con != null) con.close(); 
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * お気に入り店舗のIDを取得
	 * @param accountId:アカウントID
	 * @return お気に入り店舗のIDリスト
	 * @throws SQLException
	 */
	public ArrayList<String> getFavoriteShopIds(String accountId) throws SQLException{
		// SQL
		sql = "select * from favorite where account_id = ?";
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		// 店舗IDリスト
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			// プレースホルダを設定
			ps = con.prepareStatement(sql);
			ps.setString(1, accountId);

			// SQL実行
			rs = ps.executeQuery();
			while(rs.next()) {
				// 店舗IDを取得してリストへ格納
				list.add(rs.getString("shop_id"));
			}
			rs.close();
		}finally {
			ps.close();
		}
		
		return list;
	}
	
	/**
	 * お気に入り店舗を追加
	 * @param accountId:アカウントID
	 * @param shopId:店舗ID
	 * @return 実行結果
	 * @throws SQLException
	 */
	public int insertFavorite(String accountId, String shopId) throws SQLException{
		// SQL
		String sql = "INSERT INTO favorite(account_id, shop_id) VALUES (?, ?) ";
		PreparedStatement ps = null;
		int n = 0;
		
		try {
			// プレースホルダを設定
			ps = con.prepareStatement(sql);
			ps.setString(1, accountId);
			ps.setString(2, shopId);
			
			// SQL実行
			n = ps.executeUpdate();
		}finally {
			ps.close();
		}
		
		return n;
	}
	
	/**
	 * お気に入り店舗を削除
	 * @param accountId:アカウントID
	 * @param shopId:店舗ID
	 * @return 実行結果
	 * @throws SQLException
	 */
	public int deleteFavorite(String accountId, String shopId) throws SQLException{
		// SQL
		String sql = "delete from favorite where account_id = ? and shop_id = ?";
		PreparedStatement ps = null;
		int n = 0;
		
		try {
			// プレースホルダを設定
			ps = con.prepareStatement(sql);
			ps.setString(1, accountId);
			ps.setString(2, shopId);

			// SQL実行
			n = ps.executeUpdate();
		}finally {
			ps.close();
		}
		
		return n;
	}
	
	/**
	 * アカウントを検索して取得
	 * @param accountId:アカウントID
	 * @param passWord:パスワード
	 * @return　検索結果
	 * @throws SQLException
	 */
	public int getAccount(String accountId, String passWord) throws SQLException{
		// SQL
		sql = "select * from account where account_id = ? and password = ?";
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		int result = 0;
		
		try {
			// プレースホルダを設定
			ps = con.prepareStatement(sql);
			ps.setString(1, accountId);
			ps.setString(2, passWord);

			// SQL実行
			rs = ps.executeQuery();
			// 結果のカーソルを最後へ移動
			rs.next();
			// 結果取得
			result = rs.getRow();
			// ResultSetを閉じる
			rs.close();
		}finally {
			ps.close();
		}
		
		return result;
	}
}
