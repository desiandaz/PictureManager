package pictureManager.web;

import javax.servlet.http.HttpServlet;

import pictureManager.dao.DaoRepository;
import pictureManager.dao.DataAccessException;

public abstract class AbstractDaoAccessServlet extends HttpServlet {

    protected static DaoRepository daoRepository;

    protected final DaoRepository getDaoRepository() throws DataAccessException {
        synchronized (AbstractDaoAccessServlet.class) {
            if (daoRepository == null) {
                String daoRepositoryClassName = super.getServletContext().getInitParameter(
                        "daoRepositoryClassName");
                try {
                    Class<?> daoRepositoryClass = Class.forName(daoRepositoryClassName);
                    daoRepository = (DaoRepository) daoRepositoryClass.newInstance();
                } catch (Exception e) {
                    throw new DataAccessException(
                            "Failed to initialize dao repository from init parameter [daoClassName]=["
                                    + daoRepositoryClassName + "]", e);
                }
            }
            return daoRepository;
        }
    }

    protected boolean isEmpty(String s) {
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }
}
