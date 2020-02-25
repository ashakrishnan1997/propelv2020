package net.faith.usermanagement.dao;

import java.util.List;

import net.faith.usermanagement.model.User;

public interface iUserDao {

	public abstract int insertUser(User user);

	public abstract User selectUser(Integer id) throws Exception;

	public abstract List<User> selectAllUser() throws Exception;

	public abstract boolean deleteUser(Integer id) throws Exception;

	public abstract boolean updateUser(User user) throws Exception;

	

}