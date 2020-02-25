package net.faith.usermanagement.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

import net.faith.usermanagement.dao.UserDAO;
import net.faith.usermanagement.dao.iUserDao;
import net.faith.usermanagement.model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDAO userDao;

	public void init() {
		userDao = UserDAO.getInstance();
	}

	public UserServlet() {
		super();

	}
//do post method is used to post the information to the server 
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}
//calling doget from dopost
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
 //getting the action
		String action = request.getServletPath();
		//use try catch for exce ption handling 
		//use switch case 
		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertUser(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			default:
				listUser(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		} catch (Exception e) {
	
			e.printStackTrace();
		}

	}

	private void showNewForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}

	private void listUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<User> listUser = userDao.selectAllUser();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispacter = request
				.getRequestDispatcher("user-list.jsp");
		dispacter.forward(request, response);

	}

	private void showEditForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		Integer id = Integer.parseInt(request.getParameter("id"));
		User existingUser = userDao.selectUser(id);
		
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("user-form.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);

	}



	private void insertUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		User newUser = new User(name, email, country);
		Integer result;
		result = userDao.insertUser(newUser);
		response.sendRedirect("list");
	}

	private void updateUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");

		User updateUsers = new User(id, name, email, country);
		
		System.out.println(updateUsers.toString());
		userDao.updateUser(updateUsers);
		response.sendRedirect("list");
	}

	private void deleteUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		userDao.deleteUser(id);
		response.sendRedirect("list");
	}

}
