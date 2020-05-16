package us.wellaware.library;
import java.util.*;

public class LibrarySimulation implements Library {
    private final int maxShelfSize;
    private HashMap<Long, Book> isbnSet;                // <key: isbn, val: book>
    private HashMap<String, ArrayList<Shelf>> library;  // <key: genre, val: list of shelfs>

    public LibrarySimulation(int shelfSize) {
        maxShelfSize = shelfSize;
        isbnSet = new HashMap<Long, Book>();
        library = new HashMap<String, ArrayList<Shelf>>();
    }

    public boolean addBookToShelf(long isbn, String title, String author, 
        String genre, String publisher, int publicationYear, int pageCount) {

        // check if isbn is already in library
        if (isbnSet.containsKey(isbn))
            return false;

        Book newBook = new Book(isbn, title, author, genre, publisher, publicationYear, pageCount); 
        isbnSet.put(isbn, newBook);

        // check if shelf for genre already exists
        ArrayList<Shelf> curShelfs;
        if ((curShelfs = library.get(genre)) != null){
            int size  = curShelfs.size();
            Shelf lastShelf = curShelfs.get(size - 1);
            // shelfs are all full
            if (lastShelf.isFull()){
                addBookToNewShelf(newBook, size + 1, curShelfs);
            }
            // add to existing shelf
            else {
                newBook.setShelfName(lastShelf.getName());
                lastShelf.addBook(newBook);
            }
            // restore order in shelves after book is added
            library.put(genre, sortShelfs(library.get(genre)));
        } 
        // no shelfs exisit for genre
        else {
            addBookToNewShelf(newBook, 1, curShelfs);
        }
        return true;
    }

    // restore order in shelves after book is added
    private ArrayList<Shelf> sortShelfs(ArrayList<Shelf> shelfs){
        ArrayList<Shelf> newShelfs = new ArrayList<Shelf>();
        TreeSet<Book> allBooks = new TreeSet<Book>(new Shelf.BookSortingComparator());
        Iterator<Book> shelfBooks;
        for (int i = 0; i < shelfs.size(); i++){
            shelfBooks = shelfs.get(i).getBooks().iterator();
            while (shelfBooks.hasNext()){
                allBooks.add(shelfBooks.next());
            }
        }
        Iterator<Book> alliter = allBooks.iterator();
        for (int i = 0; i < shelfs.size(); i++){
            Shelf newShelf = new Shelf(maxShelfSize, shelfs.get(i).getGenre(), i + 1);
            newShelfs.add(newShelf);
            int k = 0;
            while(alliter.hasNext() && k < maxShelfSize){
                Book book = alliter.next();
                book.setShelfName(shelfs.get(i).getGenre() + " - " + Integer.toString(i + 1));
                newShelfs.get(i).addBook(book); 
                k++;
            }
        }
        return newShelfs;
    }

    // create a new shelf, add book and add shelf to library
    private void addBookToNewShelf(Book book, int shelfNum, ArrayList<Shelf> curShelfs){
        Shelf newShelf = new Shelf(maxShelfSize, book.getGenre(), shelfNum);
        if (curShelfs == null)
            curShelfs = new ArrayList<Shelf>();
        book.setShelfName(newShelf.getName());
        newShelf.addBook(book);
        curShelfs.add(newShelf);
        library.put(book.getGenre(), curShelfs);
    }

    public String getBookTitle(long isbn) {
        if (isbnSet.containsKey(isbn))
            return isbnSet.get(isbn).getTitle();
        return null;
    }

    public List<String> getShelfNames() {
        ArrayList<String> names = new ArrayList<String>();
        // iterate over library hashmap and get shelf names
        Iterator<String> iter = library.keySet().iterator();
        while (iter.hasNext()){
            String genre = iter.next();
            ArrayList<Shelf> shelfs = library.get(genre);
            for (int i = 0; i < shelfs.size(); i++){
                names.add(shelfs.get(i).getName());
            }
        }
        return names;
    }

    public String findShelfNameForISBN(long isbn) {
        if (isbnSet.containsKey(isbn))
            return isbnSet.get(isbn).getShelfName();
        return null;
    }

    public List<Long> getISBNsOnShelf(String shelfName) {
        ArrayList<Long> isbns = new ArrayList<Long>();
        // iterate over library hashmap until finding shelfName
        Iterator<String> iter = library.keySet().iterator();
        Shelf shelf = null;
        Iterator<Book> books;
        while (iter.hasNext()){
            String genre = iter.next();
            ArrayList<Shelf> shelfs = library.get(genre);
            for (int i = 0; i < shelfs.size(); i++){
                if (shelfs.get(i).getName().equals(shelfName))
                    shelf = shelfs.get(i);
            }
        }
        // iterate over books in shelf and get all isbns
        if (shelf != null){
            books = shelf.getBooks().iterator();
            while(books.hasNext()){
                isbns.add(books.next().getIsbn());
            }
        }
        return isbns;
    }
    
    public List<Long> getISBNs(TreeSet<Book> books){
        Iterator<Book> iter = books.iterator();
        List<Long> isbns = new ArrayList<Long>();
        while (iter.hasNext()){
            isbns.add(iter.next().getIsbn());
        }
        return isbns;
    }

    public List<Long> getISBNsForGenre(String genre, int limit) {
        int cnt = 0;
        ArrayList<Shelf> genreShelfs = library.get(genre);
        Iterator<Shelf> iter = genreShelfs.iterator();
        Iterator<Book> books;
        TreeSet<Book> returnBooks = new TreeSet<Book>(new Shelf.BookSortingComparator());
        while (iter.hasNext() && cnt < limit){
            books = iter.next().getBooks().iterator();
            while(books.hasNext() && cnt < limit){
                returnBooks.add(books.next());
                cnt++;
            }
        }
        return getISBNs(returnBooks);
    }

    public List<Long> getISBNsForAuthor(String author, int limit) {
        int cnt = 0;
        // iterate over entire library hashmap
        Iterator<String> iter = library.keySet().iterator();
        Iterator<Book> books;
        Shelf shelf;
        TreeSet<Book> returnBooks = new TreeSet<Book>(new Shelf.BookSortingComparator());
        while (iter.hasNext() && cnt < limit){
            ArrayList<Shelf> shelfs = library.get(iter.next());
            for (int i = 0; i < shelfs.size() && cnt < limit; i++){
                books = shelfs.get(i).getBooks().iterator();
                while (books.hasNext() && cnt < limit){
                    Book book = books.next();
                    if (book.getAuthor().equals(author)){
                        returnBooks.add(book);
                        cnt++;
                    }
                }
            }
        }
        return getISBNs(returnBooks);
    }

    public List<Long> getISBNsForPublisher(String publisher, int limit) {
        int cnt = 0;
        // iterate over entire library hashmap
        Iterator<String> iter = library.keySet().iterator();
        Iterator<Book> books;
        Shelf shelf;
        TreeSet<Book> returnBooks = new TreeSet<Book>(new Shelf.BookSortingComparator());
        while (iter.hasNext() && cnt < limit){
            ArrayList<Shelf> shelfs = library.get(iter.next());
            for (int i = 0; i < shelfs.size() && cnt < limit; i++){
                books = shelfs.get(i).getBooks().iterator();
                while (books.hasNext() && cnt < limit){
                    Book book = books.next();
                    if (book.getPublisher().equals(publisher)){
                        returnBooks.add(book);
                        cnt++;
                    }
                }
            }
        }
        return getISBNs(returnBooks);
    }

    public List<Long> getISBNsPublishedAfterYear(short publicationYear, int limit) {
        int cnt = 0;
        // iterate over entire library hashmap
        Iterator<String> iter = library.keySet().iterator();
        Iterator<Book> books;
        Shelf shelf;
        TreeSet<Book> returnBooks = new TreeSet<Book>(new Shelf.BookSortingComparator());
        while (iter.hasNext() && cnt < limit){
            ArrayList<Shelf> shelfs = library.get(iter.next());
            for (int i = 0; i < shelfs.size() && cnt < limit; i++){
                books = shelfs.get(i).getBooks().iterator();
                while (books.hasNext() && cnt < limit){
                    Book book = books.next();
                    if (book.getYear() > publicationYear){
                        returnBooks.add(book);
                        cnt++;
                    }
                }
            }
        }
        return getISBNs(returnBooks);
    }

    public List<Long> getISBNsWithMinimumPageCount(int minimumPageCount, int limit) {
        int cnt = 0;
        // iterate over entire library hashmap
        Iterator<String> iter = library.keySet().iterator();
        Iterator<Book> books;
        Shelf shelf;
        TreeSet<Book> returnBooks = new TreeSet<Book>(new Shelf.BookSortingComparator());
        while (iter.hasNext() && cnt < limit){
            ArrayList<Shelf> shelfs = library.get(iter.next());
            for (int i = 0; i < shelfs.size() && cnt < limit; i++){
                books = shelfs.get(i).getBooks().iterator();
                while (books.hasNext() && cnt < limit){
                    Book book = books.next();
                    if (book.getPageCount() >= minimumPageCount){
                        returnBooks.add(book);
                        cnt++;
                    }
                }
            }
        }
        return getISBNs(returnBooks);
    }
}
