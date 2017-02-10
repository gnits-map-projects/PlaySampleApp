package models;


public class User {

    public User(String username, String password, Integer role, Integer status) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    String username;

    String password;

    Integer role;

    Integer status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
