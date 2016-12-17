package dao;


import exceptions.NotFoundException;
import models.Book;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.List;

public class BookDao implements CrudDao<Integer, Book> {

    private JPAApi jpaApi;

    @Inject
    public BookDao(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public void persist(Book book) {
        jpaApi.em().persist(book);
    }

    public Book update(Book book) {

        Book existingBook = findById(book.getId());
        existingBook.setTitle(book.getTitle());
        jpaApi.em().merge(existingBook);

        return existingBook;
    }

    public void deleteById(Integer id) {

        Book book = findById(id);
        jpaApi.em().remove(book);

    }

    public Book findById(Integer id) {
        final Book book = jpaApi.em().find(Book.class, id);
        if (null == book) {
            String errorMessage = "Book with id: " + id + " not found";
            throw new NotFoundException(errorMessage);
        }
        return book;
    }

    public List<Book> findAll() {
        TypedQuery<Book> query = jpaApi.em().createQuery("SELECT b FROM Book b", Book.class);
        List<Book> books = query.getResultList();
        return books;
    }

}
