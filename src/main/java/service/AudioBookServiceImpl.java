package service;

import model.AudioBook;
import repository.audiobook.AudioBookRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AudioBookServiceImpl implements AudioBookService{
    final AudioBookRepository bookRepository;

    public AudioBookServiceImpl(AudioBookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public List<AudioBook> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public AudioBook findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Audio Book with id: %d not found".formatted(id)));
    }

    @Override
    public boolean save(AudioBook book) {
        return bookRepository.save(book);
    }

    @Override
    public int getAgeOfBook(Long id) {
        AudioBook book = this.findById(id);

        LocalDate now = LocalDate.now();

        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }
}
