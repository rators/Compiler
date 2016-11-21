package answerkey;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 */
public class Players {
    private final List<Player> players;
    private final Random randGenerator = new Random();
    public Players(List<Player> players) {
        this.players = players;
    }

    public List<Player> findById(final String id) {
        Stream<Player> withId = players.stream().filter((e) -> Objects.equals(e.id(), id));
        return withId.collect(Collectors.toList());
    }

    public List<Player> findByLastName(final String lastName) {
        Stream<Player> withId = players.stream().filter((e) -> Objects.equals(e.lastName(), lastName));
        return withId.collect(Collectors.toList());
    }

    public static Players load() {
        File file = new File("./src/main/java/players.txt");

        try {
            List<Player> players = Files.lines(file.toPath())
                    .map(Players::strToPlayer)
                    .collect(Collectors.toList());
            return new Players(players);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static Player strToPlayer(String s) {
        String[] params = s.split(",");

        return new Player(params[0], params[1], params[2]);
    }

    public Player randPlayer(){
        return players.get(randGenerator.nextInt(players.size()));
    }
}
