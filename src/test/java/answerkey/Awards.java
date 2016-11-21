package answerkey;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 */
public class Awards {
    private final List<answerkey.Award> awards;
    private final Players players;

    public Awards(List<answerkey.Award> awards) {
        this.awards = awards;
        this.players = new Players(Collections.emptyList());
    }

    public Awards(List<answerkey.Award> awards, Players players) {
        this.awards = awards;
        this.players = players;
    }

    public List<answerkey.Award> find(final String id) {
        Stream<answerkey.Award> withId = awards.stream().filter((e) -> Objects.equals(e.player().id(), id));
        return withId.collect(Collectors.toList());
    }

    public static Awards loadFiles() {
        File file = new File("./src/main/java/awards.txt");
        final Players players = Players.load();

        try {
            List<answerkey.Award> awards = Files.lines(file.toPath())
                    .filter((line) -> !players.findById(line.split(",")[0]).isEmpty())
                    .map((line) -> Awards.strToAward(line, players))
                    .collect(Collectors.toList());

            return new Awards(awards, players);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Awards(Collections.emptyList(), players);
    }

    public static void load(String pathName){

    }

    public final Players players() {
        return players;
    }

    private static answerkey.Award strToAward(String s, Players players) {
        String[] params = s.split(",");
        String playerId = params[0];
        answerkey.Player player = players.findById(playerId).get(0);
        answerkey.Award award = new answerkey.Award(player, params[1], params[2], params[3]);
        return award;
    }

    public void printAwards(String path){
        awards.forEach(System.out::println);
    }
}
