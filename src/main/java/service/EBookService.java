package service;

import model.EBook;

import java.util.List;

public interface EBookService {
    List<EBook> findAll();

    EBook findById(Long id);

    boolean save(EBook ebook);

    int getAgeOfBook(Long id);
}
