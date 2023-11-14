package repository.audiobook;

public abstract class AudioBookRepositoryDecorator implements AudioBookRepository{
    protected AudioBookRepository decoratedRepository;

    public AudioBookRepositoryDecorator(AudioBookRepository audiobookRepository){
        this.decoratedRepository = audiobookRepository;
    }
}
