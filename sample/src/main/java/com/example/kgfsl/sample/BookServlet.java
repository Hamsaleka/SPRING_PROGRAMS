package com.example.kgfsl.sample;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BookServlet
 */
@WebServlet({ "/list", "/delete", "/insert", "/update1","/update2" })
public class BookServlet extends HttpServlet {
    private BookDAO bookDAO;

    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        bookDAO = new BookDAO(jdbcURL, jdbcUsername, jdbcPassword);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String action = req.getServletPath();
        System.out.println(action);
        try {
            switch (action) {
            case "/insert":
                System.out.println("insert action called");
                insertBook(req, resp);
                break;
            case "/delete":
                System.out.println("delete action called");
                //deleteBook(req, resp);
                deleteBook(req, resp);

                break;
            case "/update1":
                
                System.out.println("update action called");
                updateForm(req, resp);
                break;

                case "/update2":
                
                System.out.println("Update 2 Called");
                updateBook(req, resp);
                break;
            default:
                System.out.println("list action called");
                listBook(req, resp);
                break;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void listBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Book> listBook = bookDAO.listAllBooks();
        request.setAttribute("listBook", listBook);
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(listBook, new TypeToken<List<Book>>() {
        }.getType());
        JsonArray jsonArray = element.getAsJsonArray();
        response.setContentType("application/json");
        response.getWriter().print(jsonArray);
        // RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        // dispatcher.forward(request, response);
    }

    protected void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("code"));
        //  System.out.println(id);
        Book book = new Book(id);
        bookDAO.deleteBook(book);
        // listBook(request, response);
        response.sendRedirect("/");
        // System.out.println("gg");
        // request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void insertBook(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, SQLException, IOException {
        PrintWriter out = resp.getWriter();
        String title = req.getParameter("title");
        String author = req.getParameter("author");
        String price = req.getParameter("price");
        try {

            //loading drivers for mysql
            Class.forName("com.mysql.jdbc.Driver");

            //creating connection with the database 
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");

            PreparedStatement ps = con
                    .prepareStatement("INSERT INTO `book`(`title`, `author`, `price`) VALUES (?,?,?)");

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, price);
            int i = ps.executeUpdate();
        } catch (Exception se) {
            se.printStackTrace();
        }
        //    showNewForm(req, resp);
        // List<Book> listBook = bookDAO.listAllBooks();
        // req.setAttribute("listBook", listBook);

        // Gson gson = new Gson();
        // JsonElement element = gson.toJsonTree(listBook, new TypeToken<List<Book>>() {}.getType());
        // JsonArray jsonArray = element.getAsJsonArray();
        resp.setContentType("application/json");
        // resp.getWriter().print(jsonArray);
        resp.sendRedirect("/");
    }

    private void updateForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
                System.out.println("hiii");
                int id = Integer.parseInt(request.getParameter("code"));
                Book book=bookDAO.getBook(id);
                ArrayList<Book> booklist=new ArrayList<Book>();
                booklist.add(book);
                Gson gson = new Gson();
                JsonElement element = gson.toJsonTree(booklist,new TypeToken<List<Book>>(){
                }.getType());
                JsonArray jsonArray = element.getAsJsonArray();
                
                response.setContentType("application/json");
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                request.setAttribute("bookid", id);
                request.setAttribute("book", book);
                dispatcher.forward(request, response);
                //response.getWriter().print(jsonArray);
                //response.sendRedirect("/");

        // List<Book> listBook = bookDAO.listAllBooks();
        // request.setAttribute("listBook", listBook);
        // Gson gson = new Gson();
        // JsonElement element = gson.toJsonTree(listBook, new TypeToken<List<Book>>() {
        // }.getType());
        // JsonArray jsonArray = element.getAsJsonArray();
        // response.setContentType("application/json");
        // response.getWriter().print(jsonArray);
        // RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        // dispatcher.forward(request, response);
    }
    private void updateBook(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException, ServletException {
        System.out.println("before update");
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String price = request.getParameter("price");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
            PreparedStatement statement;
            statement = con.prepareStatement("UPDATE `book` SET `title`=?,`author`=?,`price`=? WHERE book_id=?");

            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, price);
            statement.setString(4, id);
            statement.executeUpdate(); 
            System.out.println("updated");
            //response.setContentType("application/json");
            // resp.getWriter().print(jsonArray);
            response.sendRedirect("/");

        } catch (Exception se) {
            se.printStackTrace();
            
        }
        // Book book=bookDAO.getBook(id);
        // ArrayList<Book> booklist=new ArrayList<Book>();
        // booklist.add(book);
        // Gson gson = new Gson();
        // JsonElement element = gson.toJsonTree(booklist,new TypeToken<List<Book>>(){
        // }.getType());
        // JsonArray jsonArray = element.getAsJsonArray();
        
        // response.setContentType("application/json");
        // RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        // request.setAttribute("bookid", id);
        // request.setAttribute("book", book);
        // dispatcher.forward(request, response);
        //response.getWriter().print(jsonArray);
        //response.sendRedirect("/");

// List<Book> listBook = bookDAO.listAllBooks();
// request.setAttribute("listBook", listBook);
// Gson gson = new Gson();
// JsonElement element = gson.toJsonTree(listBook, new TypeToken<List<Book>>() {
// }.getType());
// JsonArray jsonArray = element.getAsJsonArray();
// response.setContentType("application/json");
// response.getWriter().print(jsonArray);
// RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
// dispatcher.forward(request, response);
}
}