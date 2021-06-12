package es.uji.ei1027.proyecto1027.model;

public class UserDetails {
    String username;
    String password;
    UserDetailsEnum userType;

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

    public String getUserType() {
        return userType.toString();
    }

    public void setUserType(String userType) {
        this.userType = UserDetailsEnum.valueOf(userType);
    }
}