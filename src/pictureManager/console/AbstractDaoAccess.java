package pictureManager.console;

import pictureManager.dao.DaoRepository;
import pictureManager.dao.DataAccessException;

public abstract class AbstractDaoAccess {

    protected static DaoRepository daoRepository;

    protected final DaoRepository getDaoRepository() throws DataAccessException {
        synchronized (AbstractDaoAccess.class) {
            if (daoRepository == null) {
                try {
                    Class<?> daoRepositoryClass = Class.forName("MySqlDaoRepository");
                    daoRepository = (DaoRepository) daoRepositoryClass.newInstance();
                } catch (Exception e) {
                    throw new DataAccessException(
                            "Failed to initialize dao repository from init parameter [daoClassName]=[MySqlDaoRepository]", e);
                }
            }
            return daoRepository;
        }
    }

    protected boolean isEmpty(String s) {
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }
}
