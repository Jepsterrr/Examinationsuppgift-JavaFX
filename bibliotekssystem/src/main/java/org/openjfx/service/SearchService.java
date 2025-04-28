package org.openjfx.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.dao.ArticleDAO;
import org.openjfx.dao.BookDAO;
import org.openjfx.dao.DvdDAO;
import org.openjfx.table.MediaItem;

public class SearchService {

    private final BookDAO bookDao = new BookDAO();
    private final DvdDAO dvdDao   = new DvdDAO();
    private final ArticleDAO artDao = new ArticleDAO();

    public List<MediaItem> searchAll(String term) throws SQLException {
        List<MediaItem> allMedia = new ArrayList<>();
        allMedia.addAll(bookDao.searchByTitle(term));
        allMedia.addAll(dvdDao.searchByTitle(term));
        allMedia.addAll(artDao.searchByTitle(term));
        return allMedia;
    }
}