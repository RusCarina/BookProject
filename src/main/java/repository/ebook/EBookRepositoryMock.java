package repository.ebook;

import model.EBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EBookRepositoryMock implements EBookRepository{
    private List<EBook> ebooks;

    public EBookRepositoryMock(){
        ebooks = new ArrayList<>();
    }

    @Override
    public List<EBook> findAll() {
        return ebooks;
    }

    @Override
    public Optional<EBook> findById(Long id) {
        return ebooks.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(EBook ebook) {
        return ebooks.add(ebook);
    }

    @Override
    public void removeAll() {
        ebooks.clear();
    }
}
