package org.openjfx.controller;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.util.List;
import java.util.ArrayList;
import org.openjfx.dao.BookDAO;
import org.openjfx.table.Book;
import org.openjfx.dao.MediaItemDAO;

public class SearchController {

    @FXML
    private TextField searchBox;

    @FXML
    private ListView<String> listView;

    @FXML
    private void updateSearch() {
        System.out.println("Du klickade på knappen!");
        BookDAO bookDAO = new BookDAO();
        List<Book> books = new ArrayList<>();
        Book searchedBook = null;
        String text = searchBox.getText();
        try {
            System.out.println("Trying to get all books");
            books = bookDAO.getAll();
            try {
                int id = MediaItemDAO.getIdByTitle(text.toLowerCase());
                System.out.println("Söker efter bok med id: " + id);
                searchedBook = bookDAO.get(id);
                System.out.println("Har satt searchedBook till: " + searchedBook);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    
        
        searchBox.clear();
        System.out.println(text);
        System.out.println("Funkade sökt bok: " + searchedBook);
    }
}

