package pictureManager.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pictureManager.dao.DaoRepository;
import pictureManager.dao.MySqlDaoRepository;
import pictureManager.domain.Event;
import pictureManager.domain.Picture;

public class ViewEventServlet extends AbstractDaoAccessServlet {

	protected static DaoRepository daoRepository = new MySqlDaoRepository();
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
        List<Picture> pictureList = daoRepository.getPictureDao().loadAll(Long.parseLong(req.getAttribute("eventId").toString()));
        req.setAttribute("pictureList", pictureList);
        req.getRequestDispatcher("viewEvent.jsp").forward(req, resp);
    }
}
