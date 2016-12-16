package models;

import org.junit.Test;

import static org.junit.Assert.*;


public class BookTest {

    @Test
    public void testCreate() throws Exception {

        final Book book = new Book(1, "My Book");

        assertEquals("My Book", book.getTitle());
        assertTrue(book.getId() == 1);


    }
}