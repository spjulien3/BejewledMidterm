package com.example.bejewledmidterm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.Interpolator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int [] skulls = {
            R.drawable.white_skull,
            R.drawable.red_skull,
            R.drawable.green_skull,
            R.drawable.blue_skull,
            R.drawable.purple_skull,
            R.drawable.yellow_skull
    };
    int tileWidth, numOfTiles = 8, screenWidth, screenHeight;
    TextView score;
    ArrayList <ImageView> skullList =  new ArrayList<>();
    int blank = R.drawable.fire_tile;
    Handler handler;
    int selectedTile, replacementTile;
    int scoreBoard = 0;
    int winConditon = 100;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score = findViewById(R.id.score_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = getWindowManager().getCurrentWindowMetrics();
            screenWidth = windowMetrics.getBounds().width();
            screenHeight = windowMetrics.getBounds().height();
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;
        }
        tileWidth = screenWidth/numOfTiles;
        generateBoard();
        for(final ImageView imageView: skullList){
            imageView.setOnTouchListener(new OnSwipeListener(this){
                @Override
                void onSwipeLeft() {
                    super.onSwipeLeft();
                    selectedTile = imageView.getId();
                    replacementTile = selectedTile-1;
                    int temp = (int) skullList.get(replacementTile).getTag();
                    int temp2 =(int) skullList.get(selectedTile).getTag();
                    skullList.get(selectedTile).setImageResource(temp);
                    skullList.get(replacementTile).setImageResource(temp2);
                    skullList.get(selectedTile).setTag(temp);
                    skullList.get(replacementTile).setTag(temp2);
                }

                @Override
                void onSwipeRight() {
                    super.onSwipeRight();
                    selectedTile = imageView.getId();
                    replacementTile = selectedTile+1;
                    int temp = (int) skullList.get(replacementTile).getTag();
                    int temp2 =(int) skullList.get(selectedTile).getTag();
                    skullList.get(selectedTile).setImageResource(temp);
                    skullList.get(replacementTile).setImageResource(temp2);
                    skullList.get(selectedTile).setTag(temp);
                    skullList.get(replacementTile).setTag(temp2);
                }

                @Override
                void onSwipeUp() {
                    super.onSwipeUp();
                    selectedTile = imageView.getId();
                    replacementTile = selectedTile-numOfTiles;
                    int temp = (int) skullList.get(replacementTile).getTag();
                    int temp2 =(int) skullList.get(selectedTile).getTag();
                    skullList.get(selectedTile).setImageResource(temp);
                    skullList.get(replacementTile).setImageResource(temp2);
                    skullList.get(selectedTile).setTag(temp);
                    skullList.get(replacementTile).setTag(temp2);
                }

                @Override
                void onSwipeDown() {
                    super.onSwipeDown();
                    selectedTile = imageView.getId();
                    replacementTile = selectedTile+numOfTiles;
                    int temp = (int) skullList.get(replacementTile).getTag();
                    int temp2 =(int) skullList.get(selectedTile).getTag();
                    skullList.get(selectedTile).setImageResource(temp);
                    skullList.get(replacementTile).setImageResource(temp2);
                    skullList.get(selectedTile).setTag(temp);
                    skullList.get(replacementTile).setTag(temp2);
                }
            });
        }
        handler =  new Handler();
        startRunnable();
    }
    private void rowCheck() {
        for (int i = 0; i < 62; i++) {
            int tempSkull = (int) skullList.get(i).getTag();
            boolean emptyTile = (int) skullList.get(i).getTag() == blank;
            Integer[] invalid = {6, 7, 14, 15, 22, 23, 30, 31, 38, 39, 48, 47, 54, 55};
            List<Integer> list = Arrays.asList(invalid);
            if (!list.contains(i)) {
                int j = i;
                if ((int) skullList.get(j++).getTag() == tempSkull && !emptyTile && (int) skullList.get(j++).getTag() == tempSkull && (int) skullList.get(j).getTag() == tempSkull)
                {

                    skullList.get(j).setImageResource(blank);
                    skullList.get(j).setTag(blank);
                    j--;
                    skullList.get(j).setImageResource(blank);
                    skullList.get(j).setTag(blank);
                    j--;
                    skullList.get(j).setImageResource(blank);
                    skullList.get(j).setTag(blank);
                    scoreBoard+=3;

                }

            }

        }
    }

    private void columnCheck() {
        for (int i = 0; i < 47; i++) {
            int tempSkull = (int) skullList.get(i).getTag();
            boolean emptyTile = (int) skullList.get(i).getTag() == blank;
            int j = i;
            if ((int) skullList.get(j).getTag() == tempSkull && !emptyTile && (int) skullList.get(j + numOfTiles).getTag() == tempSkull && (int) skullList.get(j + (2 * numOfTiles)).getTag() == tempSkull) {

                skullList.get(j).setImageResource(blank);
                skullList.get(j).setTag(blank);
                j += numOfTiles;
                skullList.get(j).setImageResource(blank);
                skullList.get(j).setTag(blank);
                j += numOfTiles;
                skullList.get(j).setImageResource(blank);
                skullList.get(j).setTag(blank);
                scoreBoard+= 1;

            }

        }


    }
    final Runnable matchRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                rowCheck();
                columnCheck();
                skullDrop();
                updateScore();
            } finally {
                handler.postDelayed(matchRunnable, 100);
            }

        }
    };
    void startRunnable(){
        matchRunnable.run();
    }
    private void skullDrop() {
        Integer[] rowOne = {0, 1, 2, 3, 4, 5, 6, 7};
        List<Integer> list = Arrays.asList(rowOne);
        for (int i = 55; i > 0; i--) {
            if ((int) skullList.get(i + numOfTiles).getTag() == blank) {
                skullList.get(i + numOfTiles).setImageResource((int) skullList.get(i).getTag());
                skullList.get(i + numOfTiles).setTag((skullList.get(i).getTag()));
                skullList.get(i).setImageResource(blank);
                skullList.get(i).setTag(blank);
                if (list.contains(i) && (int) skullList.get(i).getTag() == blank) {
                    int randomTilegenerator = ((int) Math.floor(Math.random() * skulls.length));
                    skullList.get(i).setImageResource(skulls[randomTilegenerator]);
                    skullList.get(i).setTag(skulls[randomTilegenerator]);
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            if((int) skullList.get(i).getTag() == blank){
                int randomTilegenerator = ((int) Math.floor(Math.random() * skulls.length));
                skullList.get(i).setImageResource(skulls[randomTilegenerator]);
                skullList.get(i).setTag(skulls[randomTilegenerator]);
            }

            
        }
    }
    public void updateScore(){
        if(scoreBoard>winConditon){
           Toast.makeText(this, "YOU WON RESTART THE  APP", Toast.LENGTH_SHORT).show();
        }
        else{
            score.setText(scoreBoard + " / "+ winConditon);
        }
    }


    private void generateBoard() {
        GridLayout gridLayout = findViewById(R.id.gameboard);
        gridLayout.setRowCount(numOfTiles);
        gridLayout.setColumnCount(numOfTiles);
        gridLayout.getLayoutParams().width = screenWidth;
        gridLayout.getLayoutParams().height = screenHeight;
        for (int i = 0; i < numOfTiles * numOfTiles; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(tileWidth,tileWidth));
            imageView.setMaxHeight(tileWidth);
            imageView.setMaxWidth(tileWidth);
            int randomTileGenerator = (int) Math.floor(Math.random()*skulls.length);
            imageView.setImageResource(skulls[randomTileGenerator]);
            imageView.setTag(skulls[randomTileGenerator]);
            skullList.add(imageView);
            gridLayout.addView(imageView);
        }


    }
}