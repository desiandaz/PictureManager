package pictureManager.dao;

public class MySqlDaoRepository implements DaoRepository {

    private PictureDao picDao = new PictureDaoImpl();
	private EventDao eveDao = new EventDaoImpl();
    private EventLocationDao evlDao = new EventLocationDaoImpl();
    
    public PictureDao getPictureDao() {
		return picDao;
	}

	public EventDao getEventDao() {
		return eveDao;
	}

	public EventLocationDao getEventLocationDao() {
		return evlDao;
	}

}
