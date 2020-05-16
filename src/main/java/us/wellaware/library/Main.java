package us.wellaware.library;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	    Library myLibrary = new LibrarySimulation(2);

	    myLibrary.addBookToShelf(345404475, "Do Androids Dream of Electric Sheep?", "Dick, Philip",
                "Science Fiction", "Ballantine Books", 1996, 244);
        myLibrary.addBookToShelf(517542095, "The Hitchhiker's Guide to the Galaxy", "Adams, Douglas",
                "Science Fiction", "Harmony Books", 1989, 224);
        myLibrary.addBookToShelf(684818701, "The Joy of Cooking: Seventh Edition", "Rombauer, Irma",
                "Cooking", "Simon and Schuster", 1997, 1136);
        myLibrary.addBookToShelf(684818702, "Joy of Cooking: Seventh Edition", "Rombauer, Irma",
                "Cooking", "Simon and Schuster", 1997, 1136);
        myLibrary.addBookToShelf(70064520, "Aunt Erma's cope book", "Bombeck, Erma", "Humor",
                "McGraw-Hill", 1979, 180);
        myLibrary.addBookToShelf(609600672, "Dave Barry is not taking this sitting down!", "Barry, Dave",
                "Humor", "Crown Publishers", 2000, 229);
        myLibrary.addBookToShelf(609600673, "Rave Barry is not taking this sitting down!", "Barry, Dave",
                "Humor", "Crown Publishers", 2000, 229);
        myLibrary.addBookToShelf(609600674, "Zave Barry is not taking this sitting down!", "Barry, Dave",
                "Humor", "Crown Publishers", 2000, 229);

        List<String> shelves = myLibrary.getShelfNames();
//        List<Long> bookIds = myLibrary.getISBNsOnShelf("Humor - 2");
//        List<Long> bookIds = myLibrary.getISBNsForGenre("Science Fiction", 1);
//        List<Long> bookIds = myLibrary.getISBNsForAuthor("Barry, Dave", 4);
//        List<Long> bookIds = myLibrary.getISBNsForPublisher("Simo and Schuster", 3);
//        List<Long> bookIds = myLibrary.getISBNsPublishedAfterYear((short) 1999, 2);
        List<Long> bookIds = myLibrary.getISBNsWithMinimumPageCount(1, 10);
        
        for (Long isbn : bookIds) {
            String title = myLibrary.getBookTitle(isbn);
            System.out.println(String.format("   %s %d", title, isbn));
        }
/*
        for (String shelf : shelves) {
            List<Long> bookIds = myLibrary.getISBNsOnShelf(shelf);

            System.out.println(String.format("Found shelf '%s' with %d books.", shelf, bookIds.size()));
            //System.out.println(String.format("Found shelf '%s'", shelf));

            for (Long isbn : bookIds) {
                String title = myLibrary.getBookTitle(isbn);
                System.out.println(String.format("   %s", title));
            }
        }
*/
    }
}
