package repository.audiobook;

import model.AudioBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AudioBookRepositoryMock implements AudioBookRepository{
    private List<AudioBook> books;

    public AudioBookRepositoryMock(){
        books = new ArrayList<>();
    }

    @Override
    public List<AudioBook> findAll() {
        return books;
    }

    @Override
    public Optional<AudioBook> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(AudioBook book) {
        return books.add(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }
}
