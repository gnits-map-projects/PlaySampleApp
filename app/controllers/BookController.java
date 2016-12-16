package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import models.Book;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BookController extends Controller {

    // store books using id as key
    private Map<Integer, Book> booksIndex;

    public BookController() {

        // temporary collection of books
        final Collection<Book> books = ImmutableList.of(
                new Book(1, "Title 1"),
                new Book(2, "Title 3"),
                new Book(3, "Title 3")
        );

        // populate the map using the temp array
        // use the book id as key
        booksIndex = new HashMap<>();
        for (Book book : books) {
            booksIndex.put(book.getId(), book);
        }

    }

    public Result getAllBooks() {

        // grab all values from the map, ignoring the keys
        final Collection<Book> books = booksIndex.values();
        Logger.debug("books {}", books);

        JsonNode json = Json.toJson(books);

        return ok(json);
    }

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
        if (null == book.getId() || null == book.getTitle()) {
            return badRequest();
        }

        // check if the id already exists
        if (booksIndex.containsKey(book.getId())) {
            Logger.error("Book with id {} already exists", book.getId());
            return badRequest();
        }

        // will replace any book with the same id
        booksIndex.put(book.getId(), book);

        return created();
    }

    public Result getBookById(Integer id) {

        if (null == id) {
            return badRequest();
        }

        final Book book = booksIndex.get(id);
        if (null == book) {
            return notFound();
        }

        JsonNode json = Json.toJson(book);

        return ok(json);
    }

    public Result deleteBookById(Integer id) {

        if (null == id) {
            return badRequest();
        }

        final Book book = booksIndex.remove(id);
        if (null == book) {
            return notFound();
        }

        return noContent();
    }

    public Result updateBookById(Integer id) {

        if (null == id) {
            return badRequest();
        }

        // TODO this code is identical to the code in createBook, remove into a separate method

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
        if (null == book.getId() || null == book.getTitle()) {
            return badRequest();
        }

        // check if the id already exists
        if (null == booksIndex.replace(book.getId(), book)) {
            return notFound();
        }

        // return the new updated book
        return ok(json);
    }

}
