package Containers;

import Models.Book;

import java.util.ArrayList;

public class BookContainer implements  IContainer<Book> {


    private ArrayList<Book> lBooks;

    public BookContainer(){lBooks = new ArrayList<>();}

    @Override
    public void deleteElem(Book elem) {
        lBooks.remove(elem);
    }

    @Override
    public void addElem(Book elem) {
        lBooks.add(elem);
    }

    @Override
    public void updateElem(Book oldElem, Book newElem) {
        lBooks.remove(oldElem);
        lBooks.add(newElem);
    }

    @Override
    public ArrayList<Book> requestToDatabase() {
        return null;
    }
}
