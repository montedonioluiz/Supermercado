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
 * Servlet implementation class ServletDropProduct
 */
@WebServlet("/DropProduct")
public class ServletDropProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDropProduct() {
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
			String sql = "DELETE FROM products WHERE id = " + request.getParameter("id");
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ps.executeUpdate();
			out.println("<html>"
							+ "<head>"
							+ "<title>Lista de Produtos</title>"
							+ "</head>"
							+ "<body>"
								+"<script>"
									+ "function deleteProduct() {"
									   +" alert(\"Produto Excluído com sucesso.\");"
									+ "}"
									+ "function addProduct() {"
									   +" alert(\"Produto Adicionado com sucesso.\");"
									+ "}"
								+ "</script>"
								+ "<h1>Produtos:</h1><br>"
								+ "<table border = \"1\">"
								+ "<tr>"
									+ "<th>ID</th>"
									+ "<th>Descrição</th>"
									+ "<th>Detalhes</th>"
									+ "<th>Excluir</th>"
								+ "</tr>");
			sql = "SELECT id, description FROM products";
			ps = connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				out.println("<tr>"
								+ "<td>" + rs.getString("id") + "</td>"
								+ "<td>" + rs.getString("description") + "</td>"
								+ "<td><a href=\"ProductDetails?id=" + rs.getString("id") + "\"> Detalhes </a></td>"
								+ "<td><a href=\"DropProduct?id=" + rs.getString("id") + "\" onclick=\"deleteProduct()\"> Excluir </a></td>"
							+ "</tr>");
				/*
				 *id int unsigned not null auto_increment, PRIMARY KEY(id),
				    code varchar(15) not null ,
				    description varchar(20),
				    brand varchar(20) not null,
				    price
				 */
			}
			out.println("</table>");
			for(int a = 0;a < 8;a ++) {
				out.println("<br>");
			}
			
			out.println("<h1> Adicionar Novos Produtos</h1><br>"
					+ "<form action = \"AddProduct\" method = \"get\">"
						+ "<table border=\"1\">"
							+ "<tr>"
								+ "<th>Código de Barras</th>"
								+ "<th>Descrição</th>"
								+ "<th>Marca</th>"
								+ "<th>Preço</th>"
							+ "</tr>"
							+ "<tr>"
								+ "<td><input type = \"text\" name = \"inputCode\"></td>"
								+ "<td><input type = \"text\" name = \"inputDesc\"></td>"
								+ "<td><input type = \"text\" name = \"inputBrand\"></td>"
								+ "<td><input type = \"text\" name = \"inputPrice\"></td>"
						    + "</tr>"
						+ "</table>"
						+ "<input type = \"submit\" value = \"Adicionar\">"
						+ "<input type = \"submit\" value = \"Adicionar\" onclick=\"addProduct()\">"
					+ "</form>");
			
			out.println("</body></html>");
			rs.close();
			ps.close();
			connection.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
