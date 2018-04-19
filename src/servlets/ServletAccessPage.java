package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/AccessPage")
public class ServletAccessPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccessPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("user");
		String password = request.getParameter("pass");
		PrintWriter out = response.getWriter();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/supermercado";
			String user1 = "root";
			String pass1 = "root";
			
			Connection connection = DriverManager.getConnection(url, user1, pass1);
			
			/* Criando SQL Jeito bosta
			String sql = "select name, password FROM users WHERE name = '" + user + "' AND password = '" + password + "'";
			PreparedStatement ps = connection.prepareStatement(sql); */
			
			//Criando SQL Jeito raíz
			String sql = "select name, password FROM users WHERE name = ? AND password = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ps.setString(1, user);
			
			ps.setString(2,password);
			
			ResultSet rs = ps.executeQuery();
			
			out.println("<html><head><title>Login</title></head><body>");
			if(rs.first()) {
				
				out.println("<h1>Login Feito com Sucesso! Bem vindo(a) usuário(a) " + user + ". O que deseja fazer?</h1><br>"
						+ "<a href=\"ListProducts\">Listar Produtos no Stoque</a>");
			} else {
				out.println("<h1>Login feito sem sucesso. Volte e tente Novamente</h1>");
			}
			out.println("</body></html>");
			
			rs.close();
			ps.close();
			connection.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 out.println("<html>"
							+ "<head>"
								+ "<title>Sucesso!</title>"
							+ "</head>"
							+ "<body>"
							+ "</body>"
						+ "</html>");
		 */
	}

}