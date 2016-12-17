package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import models.Book;
import play.Logger;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookController extends Controller {

    private JPAApi jpaApi;

    @Inject
    public BookController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Transactional
    public Result getAllBooks() {

        TypedQuery<Book> query = jpaApi.em().createQuery("SELECT b FROM Book b", Book.class);
        List<Book> books = query.getResultList();

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

        // make sure id and title is not null
        if (null == book.getTitle()) {
            return badRequest();
        }

        jpaApi.em().persist(book);

        return created();
    }

    @Transactional
    public Result getBookById(Integer id) {

        if (null == id) {
            return badRequest();
        }

        final Book book = jpaApi.em().find(Book.class, id);
        Logger.debug("get book {}", book);

        final JsonNode json = Json.toJson(book);

        return ok(json);
    }

    @Transactional
    public Result deleteBookById(Integer id) {

        if (null == id) {
            return badRequest();
        }

        final Book book = jpaApi.em().find(Book.class, id);
        if (null == book) {
            return notFound();
        }

        jpaApi.em().remove(book);

        return noContent();
    }

    @Transactional
    public Result updateBookById(Integer id) {

        if (null == id) {
            return badRequest();
        }

        // TODO this code is identical to the code in createBook
        // Code duplicate is bad, move the code into a common method

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

        // make sure id and title is not null
        if (null == book.getTitle()) {
            return badRequest();
        }

        final Book existingBook = jpaApi.em().find(Book.class, id);
        if (null == existingBook) {
            return notFound();
        }

        existingBook.setTitle(book.getTitle());

        jpaApi.em().merge(existingBook);

        // return the new updated book
        return ok(json);
    }

}
