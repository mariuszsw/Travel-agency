package main.service;

import main.model.User;

public interface UserService {

	public void createNewAccount(User user);
	
	public boolean loginExistes(String login);
	
}
