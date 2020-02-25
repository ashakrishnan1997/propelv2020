package net.faith.usermanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.faith.usermanagement.dbc.ConnectionFactory;
import net.faith.usermanagement.model.User;

public class UserDAO implements iUserDao {
	private static final String INSERT_USER = "INSERT INTO tblUser(name,email,country) VALUES(?,?,?)";
	private static final String SELECT_USER_BY_ID = "SELECT * FROM tblUser WHERE id=?";
	private static final String UPDATE_USER_SQL = "UPDATE tblUser SET name=?,email=?,country=? WHERE id=?";
	private static final String SELECT_USERS = "SELECT * FROM tblUser";
	private static final String DELETE_USER = "DELETE FROM tblUser WHERE id=?";

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.faith.usermanagement.dao.iUserDao#insertUser(net.faith.usermanagement
	 * .model.User)
	 */
	@Override
	public int insertUser(User user) {
		int result = -1;

		// print the result
		System.out.println(INSERT_USER);
		try {
			// get the connection from connection factory
			connection = ConnectionFactory.getConnection();

			// execute the query
			preparedStatement = connection.prepareStatement(INSERT_USER);

			// set the values
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());

			result = preparedStatement.executeUpdate();
			// close the object
			preparedStatement.close();
			connection.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.faith.usermanagement.dao.iUserDao#selectAllUSer()
	 */
	@Override
	public List<User> selectAllUser() throws Exception {
		List<User> users = new ArrayList<User>();
		try {
			connection = ConnectionFactory.getConnection();

			preparedStatement = connection.prepareStatement(SELECT_USERS);
			
			System.out.println(SELECT_USERS);
			// execute query
			resultSet = preparedStatement.executeQuery();
			// display the result
			while (resultSet.next()) {
				Integer userId = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				String country = resultSet.getString("country");
				users.add(new User(userId, name, email, country));

			}
			// close the objects
			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return users;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.faith.usermanagement.dao.iUserDao#deleteUser(java.lang.Integer)
	 */
	@Override
	public boolean deleteUser(Integer id) throws Exception {
		boolean rowDelete = false;
		try {
			connection = ConnectionFactory.getConnection();

			preparedStatement = connection.prepareStatement(DELETE_USER);
			preparedStatement.setInt(1, id);

			rowDelete = preparedStatement.executeUpdate() > 0;

			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return rowDelete;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.faith.usermanagement.dao.iUserDao#updateUser(net.faith.usermanagement
	 * .model.User)
	 */
	@Override
	public boolean updateUser(User user) throws Exception {
		boolean rowUpdated = false;
		try {
			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_USER_SQL);
			
			System.out.println(UPDATE_USER_SQL);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.setInt(4, user.getId());

			rowUpdated = preparedStatement.executeUpdate() > 0;
			
			preparedStatement.close();
			connection.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return rowUpdated;

	}

	// instance variables
	private static UserDAO instance = null;

	// default constructor
	private UserDAO() {

	}

	// check the coursedao is available in stack
	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;

	}

	@Override
	public User selectUser(Integer id) throws Exception {
		// user object
		User user = null;
		try {
			// get connection
			connection = ConnectionFactory.getConnection();
			// calling query
			preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
			preparedStatement.setInt(1, id);
			// execute query
			resultSet = preparedStatement.executeQuery();
			// display the result
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				String country = resultSet.getString("country");

				user = new User(id, name, email, country);

			}
			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return user;
	}

}
