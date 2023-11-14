package repository.audiobook;

import model.AudioBook;
import repository.Cache;

import java.util.List;
import java.util.Optional;

public class AudioBookRepositoryCacheDecorator extends AudioBookRepositoryDecorator{
    private Cache<AudioBook> cache;

    public AudioBookRepositoryCacheDecorator(AudioBookRepository audiobookRepository, Cache<AudioBook> cache){
        super(audiobookRepository);
        this.cache = cache;
    }

    @Override
    public List<AudioBook> findAll() {
        if (cache.hasResult()){
            return cache.load();
        }

        List<AudioBook> books = decoratedRepository.findAll();
        cache.save(books);

        return books;
    }

    @Override
    public Optional<AudioBook> findById(Long id) {

        if (cache.hasResult()){
            return cache.load()
                    .stream()
                    .filter(it -> it.getId().equals(id))
                    .findFirst();
        }

        return decoratedRepository.findById(id);
    }

    @Override
    public boolean save(AudioBook book) {
        cache.invalidateCache();
        return decoratedRepository.save(book);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }
}
