import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SnakeAndLadderMain {
    private static boolean winner;
    private static List<Integer> playerPositions = Arrays.asList(0, 0, 0);
    private static int WINNING_VALUE = 20;
    private static int PLAYER_COUNT = 3;

    //TODO: Implementation of Snake and Ladder head and tail pending
    public static void main(String[] args) {

        Observable.interval(200, TimeUnit.MILLISECONDS)
                .map(emission -> (emission % PLAYER_COUNT) + 1)
                .map(playerNumber -> Arrays.asList(playerNumber, diceRoll()))
                .takeWhile(playaNumber -> !winner)
                .map(playerNumberandPosition -> {
                    int playerNumber = playerNumberandPosition.get(0).intValue() - 1;
                    int diceValue = playerNumberandPosition.get(1).intValue();
                    int updatedPosition = playerPositions.get(playerNumber) + diceValue;
                    Integer newPosition = updatedPosition < WINNING_VALUE ?
                            updatedPosition : (updatedPosition == WINNING_VALUE ? WINNING_VALUE : playerPositions.get(playerNumber));
                    winner = (newPosition == WINNING_VALUE);
                    System.out.println("For Player Number " + (playerNumber + 1) + " the dice value is " + diceValue + " and the new Position is " + + +newPosition);
                    System.out.println(winner ? "The Winner is Player: " + (playerNumber + 1) : "");
                    playerPositions.set(playerNumber, newPosition);
                    return playerPositions;
                })
                .doOnNext(System.out::println)
                .subscribe();

        sleepDuration(7000L);
    }

    private static void sleepDuration(Long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int diceRoll() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
}
