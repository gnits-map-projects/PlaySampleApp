package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import dao.BookDao;
import exceptions.NotFoundException;
import models.Book;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Collection;

public class BookController extends Controller {

    private BookDao bookDao;

    @javax.inject.Inject
    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Transactional
    public Result getAllBooks() {

        Collection<Book> books = bookDao.findAll();

        final JsonNode json = Json.toJson(books);

        return ok(json);
    }

    @Transactional
    public Result createBook() {

        final JsonNode json = request().body().asJson();
        if (null == json) {
            Logger.error("Unable to get json from request");
            return badRequest();
        }

        final Book book = Json.fromJson(json, Book.class);
        if (null == book) {
            Logger.error("Unable to parse json to Book object");
            return badRequest();
        }

        // make sure the title is not null
        if (null == book.getTitle()) {
            return badRequest();
        }

        bookDao.persist(book);

        return created();
    }

    @Transactional
    public Result getBookById(Integer id) {

        if (null == id) {
            return badRequest();
        }

        try {
            final Book book = bookDao.findById(id);
            final JsonNode json = Json.toJson(book);
            return ok(json);
        } catch (NotFoundException ex) {
            return notFound();
        }
    }

    @Transactional
    public Result deleteBookById(Integer id) {

        if (null == id) {
            return badRequest();
        }

        try {
            bookDao.deleteById(id);
            return noContent();
        } catch (NotFoundException ex) {
            return notFound();
        }
    }

    @Transactional
    public Result updateBookById(Integer id) {

        if (null == id) {
            return badRequest();
        }

        // TODO this code is identical to the code in createBook
        // Code duplication is bad, move the code into a common method

        final JsonNode json = request().body().asJson();
        if (null == json) {
            Logger.error("Unable to get json from request");
            return badRequest();
        }

        final Book book = Json.fromJson(json, Book.class);
        if (null == book) {
            Logger.error("Unable to parse json to Book object");
            return badRequest();
        }

        // make sure the title is not null
        if (null == book.getTitle()) {
            return badRequest();
        }

        try {
            bookDao.update(book);
            return ok(json);
        } catch (NotFoundException ex) {
            return notFound();
        }
    }

}
