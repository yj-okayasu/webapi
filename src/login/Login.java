package login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 指示1: 以下のコードを削除し、ログイン画面を表示する処理を追加する。
		
		// アカウントId
		String accountId = "test";
		
		HttpSession session = request.getSession(true);
		
		// NoTe: セッション変数名"account"にアカウントIDを設定し、Mainサーブレットへリダイレクトすることで、ログインが完了する。
		
		// セッションにアカウントIDを設定
		session.setAttribute("account", accountId);
		
		// Mainサーブレットへリダイレクト
		response.sendRedirect("Main");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 指示1: ここでPOSTされたIDとパスワードでログイン処理
	}
}
