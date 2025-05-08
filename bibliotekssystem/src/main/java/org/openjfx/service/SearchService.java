package org.openjfx.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.dao.ArticleDAO;
import org.openjfx.dao.BookDAO;
import org.openjfx.dao.DvdDAO;
import org.openjfx.table.MediaItem;

public class SearchService {

    private final BookDAO bookDAO = new BookDAO();
    private final DvdDAO dvdDAO = new DvdDAO();
    private final ArticleDAO artDAO = new ArticleDAO();

    public List<MediaItem> searchAll(String term) throws SQLException {
        List<MediaItem> allMedia = new ArrayList<>();
        allMedia.addAll(bookDAO.searchByTerm(term));
        allMedia.addAll(dvdDAO.searchByTerm(term));
        allMedia.addAll(artDAO.searchByTerm(term));
        return allMedia;
    }

    public List<MediaItem> getAll() throws SQLException {
        List<MediaItem> allMedia = new ArrayList<>();
        allMedia.addAll(bookDAO.getAll());
        allMedia.addAll(dvdDAO.getAll());
        allMedia.addAll(artDAO.getAll());
        return allMedia;
    }
}