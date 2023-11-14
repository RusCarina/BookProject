package model;

import java.time.LocalDate;

public class AudioBook {
    private Long id;

    private String author;

    private String title;

    private LocalDate publishedDate;
    private int runTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getRunTime(){ return runTime; }

    public void setRunTime(int runTime){ this.runTime = runTime; }

    @Override
    public String toString(){
        return String.format("Id: %d | Title: %s | Author: %s | Date: %s | Run Time: %s", this.id, this.title, this.author, this.publishedDate, this.runTime);
    }
}
