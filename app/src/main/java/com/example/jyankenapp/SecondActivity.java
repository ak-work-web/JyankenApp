//package com.example.jyankenapp;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class SecondActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_second);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}



package com.example.jyankenapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private Button[] buttons = new Button[9];
    private boolean isPlayerX = true; // true: ○, false: ×
    private int[] board = new int[9]; // 0: 空, 1: ○, 2: ×
    private TextView statusText;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second); // activity_second.xmlにゲームレイアウトを設定

        statusText = findViewById(R.id.status_text);
        resetButton = findViewById(R.id.reset_button);

        for (int i = 0; i < 9; i++) {
            String buttonID = "button_" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (board[finalI] == 0) {
                        board[finalI] = isPlayerX ? 1 : 2;
                        buttons[finalI].setText(isPlayerX ? "○" : "×");
                        if (checkWin()) {
                            statusText.setText((isPlayerX ? "○" : "×") + "の勝ち！");
                            disableButtons();
                        } else if (isBoardFull()) {
                            statusText.setText("引き分け！");
                        } else {
                            isPlayerX = !isPlayerX;
                            statusText.setText(isPlayerX ? "○の番です" : "×の番です");
                        }
                    }
                }
            });
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private boolean checkWin() {
        int[][] winPatterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // 横
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // 縦
                {0, 4, 8}, {2, 4, 6} // 斜め
        };
        for (int[] pattern : winPatterns) {
            if (board[pattern[0]] != 0 &&
                    board[pattern[0]] == board[pattern[1]] &&
                    board[pattern[1]] == board[pattern[2]]) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int cell : board) {
            if (cell == 0) return false;
        }
        return true;
    }

    private void disableButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            board[i] = 0;
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        isPlayerX = true;
        statusText.setText("○の番です");
    }
}

