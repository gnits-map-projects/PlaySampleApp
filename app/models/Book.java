package models;


import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Basic
    private String title;

    public Book() {
    }

    public Book(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    // this method will be called when printing a book object to the log
    // return your custom human readable name
    public String toString() {
        return id + " : " + title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
