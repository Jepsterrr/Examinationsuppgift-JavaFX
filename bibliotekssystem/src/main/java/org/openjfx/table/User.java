package org.openjfx.table;

public class User {
    private int loanUserId;
    private int loanTypeId;
    private String firstName;
    private String lastName;
    private final String username;
    private String email;
    private String userType;
    private String password;

    public User(int loanUserId, int loanTypeId, String firstName, String lastName, String username, String email, String userType, String password) {
        this.loanUserId = loanUserId;
        this.loanTypeId = loanTypeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = firstName.substring(0, Math.min(3, firstName.length())) + lastName.substring(0, Math.min(3, lastName.length()));
        this.email = email;
        this.userType = userType;
        this.password = password;
    }

    public int getLoanUserId() {
        return loanUserId;
    }

    public void setLoanUserId(int loanUserId) {
        this.loanUserId = loanUserId;
    }

    public int getloanTypeId() {
        return loanTypeId;
    }

    public void loanTypeId(int loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

