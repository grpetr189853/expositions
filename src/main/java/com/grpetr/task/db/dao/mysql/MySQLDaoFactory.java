package com.grpetr.task.db.dao.mysql;


import com.grpetr.task.db.dao.*;

public class MySQLDaoFactory extends DAOFactory {

	@Override
	public UserDAO getUserDAO() {
		return new MySQLUserDAO();
	}

	@Override
	public ExpositionDAO getExpositionDAO(){return new MySQLExpositionDAO();}

    @Override
    public HallDAO getHallDAO(){return new MySQLHallDAO();}

    @Override
    public OrderDAO getOrderDAO(){return new MySQLOrderDAO();}
}

