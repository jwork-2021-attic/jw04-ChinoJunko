package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

import com.anish.calabashbros.ShellSorter;
import com.anish.calabashbros.Goblin;
import com.anish.calabashbros.World;

import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    private World world;
    private Goblin[][] goblins;
    String[] sortSteps;
    public static final int GOBLIN_NUMBERS = 64;
    public static final int COLUMN_SIZE = 8;
    public static final int ROW_SIZE = GOBLIN_NUMBERS / COLUMN_SIZE;

    class RandomArrayGenerator{
        int[] array;
        int arraySize;
        int width,height;
        RandomArrayGenerator(int size){
            arraySize = size;
            width=0;
            height=0;
            array = new int[arraySize];
            for (int j = 0; j < arraySize; j++) {
                array[j] = j+1;
            }
        }
        RandomArrayGenerator(int width, int height){
            this.width=width;
            this.height=height;
            arraySize = width*height;
            array = new int[arraySize];
            for (int j = 0; j < arraySize; j++) {
                array[j] = j+1;
            }
            randomArray();
        }
        void swap(int i, int j){
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        void randomArray(){
            Random random = new Random();
            for (int i = array.length; i > 1; i--) {
                swap(i - 1, random.nextInt(i));
            }
        }
        int[] getArray(){
            return array;
        }
        int[][] getMatrix()throws Exception{
            if(height==0||width==0){
                throw new Exception("Not define a matrix");
            }
            int[][] matrix = new int[width][height];
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    matrix[j][k] = array[j*height+k];
                }
            }
            return matrix;
        }
    }

    public static Color getColor(int goblin_rank){
        int r,g,b;
        int column=(goblin_rank-1)%COLUMN_SIZE;
        if(column*3<COLUMN_SIZE){
            r=255;
            g=(255*3*column)/COLUMN_SIZE;
            b=0;
        }else if(column*2<COLUMN_SIZE){
            r=750-(column*250*6)/COLUMN_SIZE;
            g=255;
            b=0;
        }else if(column*3<COLUMN_SIZE*2){
            r=0;
            g=255;
            b=(column*250*6)/COLUMN_SIZE-750;
        }else if(column*6<COLUMN_SIZE*5){
            r=0;
            g=1250-(column*250*6)/COLUMN_SIZE;
            b=255;
        }else{
            r=(150*column*6)/COLUMN_SIZE-750;
            g=0;
            b=255;
        }
        return new Color(r,g,b);
    }

    public WorldScreen() {
        world = new World();

        RandomArrayGenerator randomArrayGenerator = new RandomArrayGenerator(ROW_SIZE,COLUMN_SIZE);
        int[][] rank = new int[ROW_SIZE][COLUMN_SIZE];
        try{
            rank = randomArrayGenerator.getMatrix();
        }catch (Exception e){
            System.out.println("Not define as a matrix!");
        }

        goblins = new Goblin[ROW_SIZE][COLUMN_SIZE];

        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COLUMN_SIZE; j++) {
                goblins[i][j] = new Goblin(getColor(rank[i][j]),rank[i][j],world);
                world.put(goblins[i][j],World.HEIGHT/2-ROW_SIZE+2*i,World.WIDTH/2-COLUMN_SIZE+2*j);
            }
        }

        ShellSorter<Goblin> b = new ShellSorter<>();
        b.load(goblins);
        b.sort();

        sortSteps = this.parsePlan(b.getPlan());
    }

    private String[] parsePlan(String plan) {
        return plan.split("\n");
    }

    private void execute(Goblin[][] gobs, String step) {
        String[] couple = step.split("<->");
        getGobByRank(gobs, Integer.parseInt(couple[0])).swap(getGobByRank(gobs, Integer.parseInt(couple[1])));
    }

    private Goblin getGobByRank(Goblin[][] gobs, int rank) {
        for (Goblin[] array : gobs) {
            for (Goblin gob : array){
                if (gob.getRank() == rank) {
                    return gob;
                }
            }
        }
        return null;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.HEIGHT; x++) {
            for (int y = 0; y < World.WIDTH; y++) {

                terminal.write(world.get(x, y).getGlyph(), y, x, world.get(x, y).getColor());

            }
        }
    }

    int i = 0;

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        if (i < this.sortSteps.length) {
            this.execute(goblins, sortSteps[i]);
            i++;
        }

        return this;
    }

}
