package csc212hw07;

/**
 */
class Award {
    private final String title;
    private final String year;
    private final String league;
    private final Player player;

    public Award(Player player, String title, String year, String league) {
        this.title = title;
        this.year = year;
        this.league = league;
        this.player = player;
    }

    public String title() {
        return title;

    }

    public String year() {
        return year;
    }

    public String league() {
        return league;
    }

    public Player player() {
        return player;
    }

    public String toString() {
        return year() + " -" + league() + " " + title();
    }
}
