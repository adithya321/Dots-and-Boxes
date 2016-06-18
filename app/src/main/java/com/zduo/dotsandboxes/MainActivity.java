package com.zduo.dotsandboxes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.zduo.dotsandboxes.model.HumanPlayer;
import com.zduo.dotsandboxes.model.Player;
import com.zduo.dotsandboxes.view.GameView;
import com.zduo.dotsandboxes.view.PlayersStateView;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements PlayersStateView {

    protected GameView gameView;
    protected TextView player1state, player2state, player1occupying, player2occupying;
    ImageView currentPlayerPointer;
    Player[] players;
    Integer[] playersOccupying = new Integer[]{0, 0};
    Player currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameView = (GameView) findViewById(R.id.gameView);
        gameView.setPlayersState(this);

        player1state = (TextView) findViewById(R.id.player1state);
        player2state = (TextView) findViewById(R.id.player2state);
        player1occupying = (TextView) findViewById(R.id.player1occupying);
        player2occupying = (TextView) findViewById(R.id.player2occupying);
        currentPlayerPointer = (ImageView) findViewById(R.id.playerNowPointer);

        new Thread() {
            @Override
            public void run() {
                startGame();
            }
        }.start();
    }


    private Player[] initPlayers() {
        return new Player[]{new HumanPlayer("Player1"), new HumanPlayer("Player2")};
    }

    private void startGame() {
        players = initPlayers();
        currentPlayer = players[0];
        gameView.startGame(currentPlayer, players);
        updateState();
    }

    public void updateState() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (currentPlayer == players[0]) {
                    player1state.setText("Thinking");
                    player2state.setText("Waiting");
                    currentPlayerPointer.setImageResource(R.drawable.a1);
                } else {
                    player2state.setText("Thinking");
                    player1state.setText("Waiting");
                    currentPlayerPointer.setImageResource(R.drawable.a2);
                }
                player1occupying.setText("Occupying " + playersOccupying[0]);
                player2occupying.setText("Occupying " + playersOccupying[1]);
            }
        });
    }

    @Override
    public void setPlayerNow(Player player) {
        currentPlayer = player;
        updateState();
    }

    @Override
    public void setPlayerOccupyingBoxesCount(Map<Player, Integer> player_occupyingBoxesCount_map) {
        playersOccupying[0] = (player_occupyingBoxesCount_map.get(players[0]));
        playersOccupying[1] = (player_occupyingBoxesCount_map.get(players[1]));
        updateState();
    }

    @Override
    public void setWinner(final Player[] winner) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String winnerNames = "";
                for (Player player : winner) {
                    winnerNames += player.getName();
                    winnerNames += " ";
                }
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Dots And Boxes")
                        .setMessage(winnerNames + "Win!")
                        .setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        recreate();
                                    }
                                }).show();
            }
        });
    }
}
