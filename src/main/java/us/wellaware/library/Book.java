package us.wellaware.library;

import java.util.*;

public class Book {
    private long isbn;
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private int publicationYear;
    private int pageCount;
    private String shelfName;

    public Book(long isbn, String title, String author, String genre, String publisher,
        int publicationYear, int pageCount){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.pageCount = pageCount;
    }

    public long getIsbn(){
        return isbn;
    }

    public String getTitle(){
        return title;
    }

    public String getGenre(){
        return genre;
    }
    
    public String getAuthor(){
        return author;
    }

    public String getPublisher(){
        return publisher;
    }

    public int getYear(){
        return publicationYear;
    }

    public int getPageCount(){
        return pageCount;
    }
    
    public void setShelfName(String shelfName){
        this.shelfName = shelfName;
    }

    public String getShelfName(){
        return shelfName;
    }
}

