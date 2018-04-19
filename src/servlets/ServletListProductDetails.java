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
 * Servlet implementation class ServletListProductDetails
 */
@WebServlet("/ProductDetails")
public class ServletListProductDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletListProductDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			String sql = "SELECT * FROM products WHERE id = " + request.getParameter("id");
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			if(!rs.first()) {
				out.println("<html>"
								+ "<body>"
									+"<script>"
										+ "alert(\"Este Produto não existe mais. Tente Novamente.\");"
									+ "</script>"
									+ "<h1>ERRO 404</h1><br>"
									+ "<a href=\"ListProducts\"> Voltar à Lista de Produtos.</a>"
								+"</body>"
						+ "</html>");
			}
			else {
				out.println("<html>"
						+ "<head>"
						+ "</head>"
						+ "<body>"
							+ "<h1>Produtos:</h1><br>"
							+ "<table border = \"1\">"
								+ "<tr>"
									+ "<th>ID</th>"
									+ "<th>Código de Barras</th>"
									+ "<th>Descrição</th>"
									+ "<th>Fabricante</th>"
									+ "<th>Preço(R$)</th>"
								+ "</tr>"
								+ "<tr>"
									+ "<td>" + rs.getString("id") + "</td>"
									+ "<td>" + rs.getString("code") + "</td>"
									+ "<td>" + rs.getString("description") + "</td>"
									+ "<td>" + rs.getString("brand") + "</td>"
									+ "<td>" + rs.getString("price") + "</td>"
								+ "</tr>"
							+ "</table>"
						+ "</body>"
						+"</html>");
			}
			rs.close();
			ps.close();
			connection.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
