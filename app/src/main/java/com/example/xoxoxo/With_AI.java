package com.example.xoxoxo;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import com.example.xoxoxo.databinding.ActivityWithAiBinding;

import java.util.ArrayList;
import java.util.List;

public class With_AI extends AppCompatActivity {

    ActivityWithAiBinding binding;
    private final List<int[]> winningPositions = new ArrayList<>();
    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0}; // 9 zeros for the board
    private int playerTurn = 1; // 1 for Player, 2 for AI
    private int totalSelectedBoxes = 0; // Count of selected boxes
    private String playerSymbol; // Track the player's symbol
    private String aiSymbol; // Track the AI's symbol
    private final Handler handler = new Handler();// Handler for delay
    private  ImageView set_ai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWithAiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String playerName = getIntent().getStringExtra("p1");
        playerSymbol = getIntent().getStringExtra("playerChoice");
        aiSymbol = playerSymbol.equals("X") ? "O" : "X"; // AI plays the opposite symbol
        binding.PlayerName.setText(playerName);
        binding.playerTwoName.setText("Computer");

        set_ai=findViewById(R.id.setting_ai);

        set_ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(With_AI.this,Setting.class);
                startActivity(i);
            }
        });

        // Update the player's symbol in the ImageView
        updatePlayerSymbolImageView();

        // Set up winning combinations
        setupWinningPositions();

        setupClickListeners();
    }

    private void updatePlayerSymbolImageView() {
        if (playerSymbol.equals("X")) {
            binding.playerSymbol.setImageResource(R.drawable.x);
            binding.computerSymbol.setImageResource(R.drawable.lettero);

        } else {
            binding.playerSymbol.setImageResource(R.drawable.x);
            binding.computerSymbol.setImageResource(R.drawable.lettero);
        }
    }

    private void setupWinningPositions() {
        winningPositions.add(new int[]{0, 1, 2});
        winningPositions.add(new int[]{3, 4, 5});
        winningPositions.add(new int[]{6, 7, 8});
        winningPositions.add(new int[]{0, 3, 6});
        winningPositions.add(new int[]{1, 4, 7});
        winningPositions.add(new int[]{2, 5, 8});
        winningPositions.add(new int[]{0, 4, 8});
        winningPositions.add(new int[]{2, 4, 6});
    }

    private void setupClickListeners() {
        binding.image1.setOnClickListener(view -> handleBoxClick(0, binding.image1));
        binding.image2.setOnClickListener(view -> handleBoxClick(1, binding.image2));
        binding.image3.setOnClickListener(view -> handleBoxClick(2, binding.image3));
        binding.image4.setOnClickListener(view -> handleBoxClick(3, binding.image4));
        binding.image5.setOnClickListener(view -> handleBoxClick(4, binding.image5));
        binding.image6.setOnClickListener(view -> handleBoxClick(5, binding.image6));
        binding.image7.setOnClickListener(view -> handleBoxClick(6, binding.image7));
        binding.image8.setOnClickListener(view -> handleBoxClick(7, binding.image8));
        binding.image9.setOnClickListener(view -> handleBoxClick(8, binding.image9));
    }

    private void handleBoxClick(int position, ImageView imageView) {
        if (isBoxSelectable(position)) {
            boxPositions[position] = playerTurn;
            imageView.setImageResource(playerSymbol.equals("X") ? R.drawable.x : R.drawable.lettero);

            totalSelectedBoxes++;
            if (checkResults()) {
                showResultDialog(binding.PlayerName.getText() + " wins!");
            } else if (totalSelectedBoxes == 9) {
                showResultDialog("Match Draw");
            } else {
                playerTurn = 2; // Switch to AI turn
                handler.postDelayed(this::aiMove, 400);
            }
        }
    }



    private boolean checkResults() {
        for (int[] combination : winningPositions) {
            if (boxPositions[combination[0]] == playerTurn &&
                    boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn) {
                return true;
            }
        }
        return false;
    }

    private void aiMove() {
        int bestMove = findBestMove();
        if (bestMove != -1) {
            boxPositions[bestMove] = playerTurn;
            ImageView imageView = getImageViewByPosition(bestMove);
            if (imageView != null) {
                imageView.setImageResource(aiSymbol.equals("X") ? R.drawable.x : R.drawable.lettero);
            }

            totalSelectedBoxes++;
            if (checkResults()) {
                showResultDialog("Computer wins!");
            } else if (totalSelectedBoxes == 9) {
                showResultDialog("Match Draw");
            } else {
                playerTurn = 1; // Switch back to player
            }
        }
    }

    private int findBestMove() {
        int bestValue = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i = 0; i < boxPositions.length; i++) {
            if (boxPositions[i] == 0) { // If the box is empty
                boxPositions[i] = 2; // AI's move
                int moveValue = minimax(boxPositions, 0, false);
                boxPositions[i] = 0; // Undo move

                if (moveValue > bestValue) {
                    bestMove = i;
                    bestValue = moveValue;
                }
            }
        }
        return bestMove;
    }

    private int minimax(int[] board, int depth, boolean isMax) {
        if (checkWinningCondition(board, 2)) {
            return 10 - depth; // AI wins
        }
        if (checkWinningCondition(board, 1)) {
            return depth - 10; // Player wins
        }
        if (isBoardFull(board)) {
            return 0; // Draw
        }

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) {
                    board[i] = 2; // AI's move
                    best = Math.max(best, minimax(board, depth + 1, false));
                    board[i] = 0; // Undo move
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) {
                    board[i] = 1; // Player's move
                    best = Math.min(best, minimax(board, depth + 1, true));
                    board[i] = 0; // Undo move
                }
            }
            return best;
        }
    }

    private boolean checkWinningCondition(int[] board, int player) {
        for (int[] position : winningPositions) {
            if (board[position[0]] == player && board[position[1]] == player && board[position[2]] == player) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull(int[] board) {
        for (int box : board) {
            if (box == 0) return false; // Found an empty box
        }
        return true; // No empty boxes found
    }

    private ImageView getImageViewByPosition(int position) {
        switch (position) {
            case 0:
                return binding.image1;
            case 1:
                return binding.image2;
            case 2:
                return binding.image3;
            case 3:
                return binding.image4;
            case 4:
                return binding.image5;
            case 5:
                return binding.image6;
            case 6:
                return binding.image7;
            case 7:
                return binding.image8;
            case 8:
                return binding.image9;
            default:
                return null;
        }
    }

    private boolean isBoxSelectable(int boxPosition) {
        return boxPositions[boxPosition] == 0; // Return true if the box is not selected
    }

    private void showResultDialog(String message) {
        ResultDialog resultDialog = new ResultDialog(With_AI.this, message, With_AI.this);
        resultDialog.setCancelable(false);
        resultDialog.show();
    }

    public void restartMatch() {
        boxPositions = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}; // Reset to 9 zeros
        playerTurn = 1;
        totalSelectedBoxes = 0;
        binding.image1.setImageResource(R.drawable.white_box);
        binding.image2.setImageResource(R.drawable.white_box);
        binding.image3.setImageResource(R.drawable.white_box);
        binding.image4.setImageResource(R.drawable.white_box);
        binding.image5.setImageResource(R.drawable.white_box);
        binding.image6.setImageResource(R.drawable.white_box);
        binding.image7.setImageResource(R.drawable.white_box);
        binding.image8.setImageResource(R.drawable.white_box);
        binding.image9.setImageResource(R.drawable.white_box);
    }
}