package service;

import model.AudioBook;

import java.util.List;

public interface AudioBookService {
    List<AudioBook> findAll();

    AudioBook findById(Long id);

    boolean save(AudioBook book);

    int getAgeOfBook(Long id);
}
