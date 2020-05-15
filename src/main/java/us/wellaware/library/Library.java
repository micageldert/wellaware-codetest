package us.wellaware.library;

import java.util.List;

/*

This interface defines a simulated library that contains books on shelves in the specified order and groupings.
These methods will allow the calling program to adds books to library shelves and fetch books by certain criteria.

Implementations of this interface must adhere to the following rules:

1. Book ISBN numbers are numeric and unique. There cannot be two books with the same ISBN number, so an attempt to
add a book to a shelf when that ISBN number already exists in the library should fail.

2. Books are only put on the same shelf as other books of the same genre.

3. Each shelf can only hold a maximum number of books, specified by the library class member variable.

4. When a shelf is full, a new shelf must be created to accommodate another book for that genre.

5. Each shelf will have an associated genre and shelf number within the genre. The name of the shelf should be
formatted as "Genre - Number". For example, “Science Fiction – 1”, “Science Fiction – 2”, “Science Fiction – 3”,
“Cooking - 1”, “Cooking – 2”, “Travel – 1”.

6. Books are ordered alphabetically first by author, then title. The first books in the order are placed on that
genre’s shelf #1, when that shelf is full the next books in order go on that genre’s shelf #2, etc. When adding a
book to a shelf that’s already full, books will get displaced to the next numbered shelf to maintain sort order.

7. Only create the number of shelves necessary for all the books. There should not be any empty shelves in the library.

8. Any specific instructions for a particular interface method will be given in comments in the interface source file.

 */

public interface Library {

    /* ISBN numbers should be unique in the library. Any failure to add a book to the library should
       return false, otherwise return true.
     */
    boolean addBookToShelf(long isbn, String title, String author, String genre, String publisher,
                           int publicationYear, int pageCount);

    /* Return the title of a book given an ISBN. If book is not found, return null. */
    String getBookTitle(long isbn);

    /* Shelf names should be formatted as 'Genre - Number', e.g. 'Science Fiction - 2' */
    List<String> getShelfNames();

    /* Shelf name in the above mentioned format should be return where the ISBN is found. If not found, return null. */
    String findShelfNameForISBN(long isbn);

    /* Return a list of all ISBNs found for the given shelf name. ISBNs should be ordered according to the book sort rules
       described in the instructions in the comment header of this file.
     */
    List<Long> getISBNsOnShelf(String shelfName);

    /* Return a list of all ISBNs found for the given genre. ISBNs across all shelves for the genre should be ordered
       according to the book sort rules described in the instructions in the comment header of this file. The limit
       parameter specifies the maximum number of ISBNs to return.
     */
    List<Long> getISBNsForGenre(String genre, int limit);

    /* Return a list of all ISBNs found for the given author. ISBNs should be ordered according to the book sort rules
       described in the instructions in the comment header of this file. The limit parameter specifies the maximum
       number of ISBNs to return.
     */
    List<Long> getISBNsForAuthor(String author, int limit);

    /* Return a list of all ISBNs found for the given publisher. ISBNs should be ordered according to the book sort rules
       described in the instructions in the comment header of this file. The limit parameter specifies the maximum
       number of ISBNs to return.
     */
    List<Long> getISBNsForPublisher(String publisher, int limit);

    /* Return a list of all ISBNs found after the given publication year. ISBNs should be ordered according to the book sort rules
       described in the instructions in the comment header of this file. The limit parameter specifies the maximum
       number of ISBNs to return.
     */
    List<Long> getISBNsPublishedAfterYear(short publicationYear, int limit);

    /* Return a list of all ISBNs found with the minimum page count. ISBNs should be ordered according to the book sort rules
       described in the instructions in the comment header of this file. The limit parameter specifies the maximum
       number of ISBNs to return.
     */
    List<Long> getISBNsWithMinimumPageCount(int minimumPageCount, int limit);
}
