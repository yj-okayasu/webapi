package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.FavoriteDBAccess;

/**
 * お気に入り機能実装サーブレット
 */
@WebServlet("/Favorite")
public class Favorite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Favorite() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションを取得
		HttpSession session = request.getSession();
		
		// 対象の店舗のID
		String shopId = request.getParameter("shop_id");
		// 対象の店舗のお気に入りフラグ
		boolean fav_flg = Boolean.parseBoolean(request.getParameter("fav_flg"));

		@SuppressWarnings("unchecked")
		ArrayList<String> favoriteShopList = (ArrayList<String>)session.getAttribute("favoriteList");
		try {
			//　アカウントIDを取得
			String accountId = (String)session.getAttribute("account");
			
			if(fav_flg) {
				// お気に入り登録されている場合は、削除処理
				FavoriteDBAccess.Delete(accountId, shopId);
				favoriteShopList.remove(shopId);
				fav_flg = false;
			}
			else {
				// お気に入り登録されていない場合は、登録処理
				FavoriteDBAccess.Insert(accountId, shopId);
				favoriteShopList.add(shopId);
				fav_flg = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// リクエストにお気に入り状態表示に必要なデータを設定
		request.setAttribute("id", shopId);
		request.setAttribute("favorite", fav_flg);
		if(fav_flg) request.setAttribute("css", "fav_icon");
		else request.setAttribute("css", "nofav_icon");
		log(String.valueOf(fav_flg));

		// セッションに店舗リストとお気に入りリストを設定
		session.setAttribute("favoriteList", favoriteShopList);
		
		ServletContext context = getServletContext();
		RequestDispatcher dis = context.getRequestDispatcher("/favorite.jsp");
		dis.forward(request, response);
	}
}
