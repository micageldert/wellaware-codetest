package us.wellaware.library;

import java.util.*;

public class Shelf {
    private int maxSize;
    private int curSize;
    private String genre;
    private int shelfNum;
    private String shelfName;

    // tree set ensures that books are always in order within shelf
    private TreeSet<Book> books;

    public Shelf(int maxSize, String genre, int shelfNum){
        this.maxSize = maxSize;
        this.genre = genre;
        this.shelfNum = shelfNum;
        this.curSize = 0;
        this.shelfName = genre + " - " + Integer.toString(shelfNum);
        books = new TreeSet<Book>(new BookSortingComparator());
    }

    public String getGenre(){
        return genre;
    }
    
    public String getName(){
        return shelfName;
    }
    
    public TreeSet<Book> getBooks(){
        return books;
    }

    public void addBook(Book book){
        books.add(book);
        curSize++;
    }

    public boolean isFull(){
        if (maxSize == curSize)
            return true;
        else
            return false;
    }

    public void setShelfNum(int shelfNum){
        this.shelfNum = shelfNum;
        this.shelfName = genre + " - " + Integer.toString(shelfNum);
    }

    static class BookSortingComparator implements Comparator<Book> { 
        @Override
        public int compare(Book b1, Book b2) {
            int author_comp = b1.getAuthor().compareTo(b2.getAuthor());
            int title_comp = b1.getTitle().compareTo(b2.getTitle());
            if (author_comp == 0){
                    return ((title_comp == 0) ? author_comp : title_comp);
                }
                else{
                    return author_comp;
                }
        }
    }
}
