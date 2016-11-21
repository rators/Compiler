package csc212hw07;

/**
 */
class Player {
    private final String firstName;
    private String lastName;
    private final String id;

    public Player(String id, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public String toString() {
        return firstName() + " " + lastName();
    }

    public String id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

}
