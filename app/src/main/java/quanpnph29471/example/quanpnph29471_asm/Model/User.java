package quanpnph29471.example.quanpnph29471_asm.Model;

public class User {
    int id ;
    String username,password,email,fullname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public User(String username, String password, String email, String fullname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
    }

    public User() {
    }

    public User(int id, String username, String password, String email, String fullname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
    }
}
