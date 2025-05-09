import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FoodServlet")
public class FoodServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM food_items");
            
            out.println("<html><head>");
            out.println("<style>");
            out.println(".card { display: inline-block; width: 250px; margin: 15px; padding: 10px; box-shadow: 2px 2px 12px #aaa; border-radius: 10px; text-align: center; }");
            out.println(".card img { width: 200px; height: 150px; }");
            out.println("</style>");
            out.println("</head><body>");
out.println("<nav>\r\n"
		+ "    <a href=\"\">Home</a>\r\n"
		+ "    <a href=\"http://\">Registration</a>\r\n"
		+ "    <a href=\"http://\">Login</a>\r\n"
		+ "    <a href=\"http://\">Cart</a>\r\n"
		+ "    <a href=\"/FoodDB/FoodServlet\">Menu</a>\r\n"
		+ "    <a href=\"http://\">Profile</a>\r\n"
		+ "</nav>");
            
            out.println("<h1>Our Menu</h1>");
            out.println("<form action='CartServlet' method='post'>");

            while(rs.next()) {
                String itemName = rs.getString("itemname");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                String imgPath = rs.getString("img_path");

                out.println("<div class='card'>");
                out.println("<img src='" + imgPath + "'><br>");
                out.println("<h3>" + itemName + "</h3>");
                out.println("<p>" + description + "</p>");
                out.println("<p><b>Price: Rs. " + price + "</b></p>");
                out.println("Qty: <input type='number' name='qty_" + itemName + "' value='1' min='1' style='width:50px;'><br><br>");
                out.println("<button type='submit' name='item' value='" + itemName + "," + price + "'>Add to Cart</button>");
                out.println("</div>");
            }

            out.println("</form>");
            out.println("<br><a href='ViewCartServlet'>View Cart & Checkout</a>");
            out.println("</body></html>");
            
            con.close();
        } catch(Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}