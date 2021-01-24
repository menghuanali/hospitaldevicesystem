package cn.pch.hospitaldevicesystem.model;

/**
 * Created by panchenghua on 2021/01/23
 */
public class LoginUser {

    private String username;
    private String password;
    private Integer rememberMe;

    public LoginUser(String username,String password){
        this.username=username;
        this.password=password;
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

    public Integer getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Integer rememberMe) {
        this.rememberMe = rememberMe;
    }
}
