package service;

import model.EBook;
import repository.ebook.EBookRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class EBookServiceImpl implements EBookService{

    final EBookRepository ebookRepository;

    public EBookServiceImpl(EBookRepository ebookRepository){
        this.ebookRepository = ebookRepository;
    }

    @Override
    public List<EBook> findAll() {
        return ebookRepository.findAll();
    }

    @Override
    public EBook findById(Long id) {
        return ebookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("EBook with id: %d not found".formatted(id)));
    }

    @Override
    public boolean save(EBook ebook) {
        return ebookRepository.save(ebook);
    }

    @Override
    public int getAgeOfBook(Long id) {
        EBook ebook = this.findById(id);

        LocalDate now = LocalDate.now();

        return (int) ChronoUnit.YEARS.between(ebook.getPublishedDate(), now);
    }
}

