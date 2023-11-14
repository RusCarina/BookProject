package repository.ebook;

import repository.ebook.EBookRepository;

public abstract class EBookRepositoryDecorator implements EBookRepository{
    protected EBookRepository decoratedRepository;

    public EBookRepositoryDecorator(EBookRepository ebookRepository){
        this.decoratedRepository = ebookRepository;
    }

}
