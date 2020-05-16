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
        } 
        // no shelfs exisit for genre
        else {
            addBookToNewShelf(newBook, 1, curShelfs);
        }
        return true;
    }

    // restore order in shelves after book is added
    private void sortShelfs(ArrayList<Shelf> shelfs){
        ArrayList<Book> allBooks;
        ArrayList<Book> shelfBooks;
        for (int i = 0; i < shelfs.size(); i++){
            shelfBooks = shelfs.get(i).getBooks();
        }
        sortBooks(allBooks); 
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
        ArrayList<Book> books;
        while (iter.hasNext()){
            String genre = iter.next();
            ArrayList<Shelf> shelfs = library.get(genre);
            for (int i = 0; i < shelfs.size(); i++){
                if (shelfs.get(i).getName() == shelfName)
                    shelf = shelfs.get(i);
            }
        }
        // iterate over books in shelf and get all isbns
        if (shelf != null){
            books = shelf.getBooks();
            for (int i = 0; i < books.size(); i++){
                isbns.add(books.get(i).getIsbn());
            }
        }
        return isbns;
    }

    public List<Long> getISBNsForGenre(String genre, int limit) {
        int cnt = 0;
        ArrayList<Long> isbns = new ArrayList<Long>();
        ArrayList<Shelf> genreShelfs = library.get(genre);
        Iterator<Shelf> iter = genreShelfs.iterator();
        ArrayList<Book> books;
        while (iter.hasNext() && cnt < limit){
            books = iter.next().getBooks();
            for (int i = 0; i < books.size() && cnt < limit; i++, cnt++){
                isbns.add(books.get(i).getIsbn());
            }
        }
        return isbns;
    }

    public List<Long> getISBNsForAuthor(String author, int limit) {
        int cnt = 0;
        ArrayList<Long> isbns = new ArrayList<Long>();
        // iterate over entire library hashmap
        Iterator<String> iter = library.keySet().iterator();
        ArrayList<Book> books;
        Shelf shelf;
        while (iter.hasNext() && cnt < limit){
            ArrayList<Shelf> shelfs = library.get(iter.next());
            for (int i = 0; i < shelfs.size() && cnt < limit; i++){
                books = shelfs.get(i).getBooks();
                for (int j = 0; j < books.size() && cnt < limit; j++){
                    if (books.get(j).getAuthor().equals(author)){
                        isbns.add(books.get(j).getIsbn());
                        cnt++;
                    }
                }
            }
        }
        return isbns;
    }

    public List<Long> getISBNsForPublisher(String publisher, int limit) {
        int cnt = 0;
        ArrayList<Long> isbns = new ArrayList<Long>();
        // iterate over entire library hashmap
        Iterator<String> iter = library.keySet().iterator();
        ArrayList<Book> books;
        Shelf shelf;
        while (iter.hasNext() && cnt < limit){
            ArrayList<Shelf> shelfs = library.get(iter.next());
            for (int i = 0; i < shelfs.size() && cnt < limit; i++){
                books = shelfs.get(i).getBooks();
                for (int j = 0; j < books.size() && cnt < limit; j++){
                    if (books.get(j).getPublisher().equals(publisher)){
                        isbns.add(books.get(j).getIsbn());
                        cnt++;
                    }
                }
            }
        }
        return isbns;
    }

    public List<Long> getISBNsPublishedAfterYear(short publicationYear, int limit) {
        int cnt = 0;
        ArrayList<Long> isbns = new ArrayList<Long>();
        // iterate over entire library hashmap
        Iterator<String> iter = library.keySet().iterator();
        ArrayList<Book> books;
        Shelf shelf;
        while (iter.hasNext() && cnt < limit){
            ArrayList<Shelf> shelfs = library.get(iter.next());
            for (int i = 0; i < shelfs.size() && cnt < limit; i++){
                books = shelfs.get(i).getBooks();
                for (int j = 0; j < books.size() && cnt < limit; j++){
                    if (books.get(j).getYear() > publicationYear){
                        isbns.add(books.get(j).getIsbn());
                        cnt++;
                    }
                }
            }
        }
        return isbns;
    }

    public List<Long> getISBNsWithMinimumPageCount(int minimumPageCount, int limit) {
        int cnt = 0;
        ArrayList<Long> isbns = new ArrayList<Long>();
        // iterate over entire library hashmap
        Iterator<String> iter = library.keySet().iterator();
        ArrayList<Book> books;
        Shelf shelf;
        while (iter.hasNext() && cnt < limit){
            ArrayList<Shelf> shelfs = library.get(iter.next());
            for (int i = 0; i < shelfs.size() && cnt < limit; i++){
                books = shelfs.get(i).getBooks();
                for (int j = 0; j < books.size() && cnt < limit; j++){
                    if (books.get(j).getPageCount() >= minimumPageCount){
                        isbns.add(books.get(j).getIsbn());
                        cnt++;
                    }
                }
            }
        }
        return isbns;
    }
}
