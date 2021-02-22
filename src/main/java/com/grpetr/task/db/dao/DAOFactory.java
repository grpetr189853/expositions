package com.grpetr.task.db.dao;

public abstract class DAOFactory {

    private static String daoFQN = null;

    public static void setDaoFQN(String daoFQN) {
        DAOFactory.daoFQN = daoFQN;
    }

    private static DAOFactory instance;


    public static synchronized DAOFactory getInstance() {
        if (daoFQN == null) {
            throw new IllegalStateException("Unknown DAO implementaion!!");
        }
        if (instance == null) {
            try {
                Class<?> c = Class.forName(daoFQN);
                instance = (DAOFactory) c.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException ex) {
                ex.printStackTrace();
            }

        }
        return instance;
    }

    protected DAOFactory() {
    }

    public abstract UserDAO getUserDAO();

    public abstract ExpositionDAO getExpositionDAO();

    public abstract HallDAO getHallDAO();

    public abstract OrderDAO getOrderDAO();
}
