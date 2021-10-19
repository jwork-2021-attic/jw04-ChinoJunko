/*
 * Copyright (C) 2015 Aeranythe Echosong
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package screen;

import world.*;
import asciiPanel.AsciiPanel;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aeranythe Echosong
 */
public class PlayScreen implements Screen {

    private World world;
    private Creature player;
    private int screenWidth;
    private int screenHeight;
    private List<String> messages;
    private List<String> oldMessages;
    private boolean atuoPathFindingMode;

    public PlayScreen() {
        this.screenWidth = 64;
        this.screenHeight = 36;
        createWorld();
        this.messages = new ArrayList<String>();
        this.oldMessages = new ArrayList<String>();
        atuoPathFindingMode = true;

        CreatureFactory creatureFactory = new CreatureFactory(this.world);
        createCreatures(creatureFactory);
    }

    private void createCreatures(CreatureFactory creatureFactory) {
        this.player = creatureFactory.newPlayer(this.messages);

        for (int i = 0; i < 100; i++) {
            creatureFactory.newGoblin();
        }
    }

    private void createWorld() {
        world = new WorldBuilder(64, 40).generateMazes().build();
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        // Show terrain
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                if (player.canSee(wx, wy)) {
                    terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
                } else if(world.getVisit(wx, wy)){
                    terminal.write(world.glyph(wx, wy), x, y, Color.DARK_GRAY);
                } else{
                    terminal.write((char) 219, x, y, Color.BLACK);
                }
            }
        }
        // Show path
        if(atuoPathFindingMode){
            int arrowX = player.x();
            int arrowY = player.y();
            for (int i = 0; i < 5; i++) {
                int direction = world.path(arrowX,arrowY);

                if(direction == 1){
                    terminal.write((char) 24, arrowX-left, arrowY-top, Color.RED);
                    arrowY--;
                }
                else if(direction == 2){
                    terminal.write((char) 25, arrowX-left, arrowY-top, Color.RED);
                    arrowY++;
                }
                else if(direction == 3){
                    terminal.write((char) 27, arrowX-left, arrowY-top, Color.RED);
                    arrowX--;
                }
                else if(direction == 4){
                    terminal.write((char) 26, arrowX-left, arrowY-top, Color.RED);
                    arrowX++;
                }
                else{
                    break;
                }
            }
        }
        // Show creatures
        for (Creature creature : world.getCreatures()) {
            if (creature.x() >= left && creature.x() < left + screenWidth && creature.y() >= top
                    && creature.y() < top + screenHeight) {
                if (player.canSee(creature.x(), creature.y())) {
                    terminal.write(creature.glyph(), creature.x() - left, creature.y() - top, creature.color());
                }
            }
        }
        // Creatures can choose their next action now
        world.update();
    }

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = this.screenHeight + 2 - messages.size();
        for (int i = 0; i < messages.size(); i++) {
            terminal.write(messages.get(i), 1, top + i + 1);
        }
        this.oldMessages.addAll(messages);
        messages.clear();
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        // Terrain and creatures
        displayTiles(terminal, getScrollX(), getScrollY());
        // Player
        terminal.write(player.glyph(), player.x() - getScrollX(), player.y() - getScrollY(), player.color());
        // Stats
        String stats = String.format("%3d/%3d hp", player.hp(), player.maxHP());
        terminal.write(stats, 1, screenHeight-1);
        String mode;
        if(atuoPathFindingMode){
            mode = new String("auto Mode is On. Press 'K' to auto move, 'L' to turn off");
        }
        else{
            mode = new String("auto Mode is Off. Press 'L' to turn on");
        }
        terminal.write(mode, 1, screenHeight+3);
        // Messages
        displayMessages(terminal, this.messages);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                player.moveBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                player.moveBy(1, 0);
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                player.moveBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                player.moveBy(0, 1);
                break;
            case KeyEvent.VK_L:
                atuoPathFindingMode = !atuoPathFindingMode;
                break;
            case KeyEvent.VK_K:
                if(atuoPathFindingMode){
                    switch (world.path(player.x(),player.y())){
                        case 1:
                            player.moveBy(0, -1);
                            break;
                        case 2:
                            player.moveBy(0, 1);
                            break;
                        case 3:
                            player.moveBy(-1, 0);
                            break;
                        case 4:
                            player.moveBy(1, 0);
                            break;
                    }
                }
                break;
        }
        if(player.x()==world.width()-1&&player.y()==world.height()-1){
            return new WinScreen();
        }
        else if(player.hp()<=0){
            return new LoseScreen();
        }
        return this;
    }

    public int getScrollX() {
        return Math.max(0, Math.min(player.x() - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player.y() - screenHeight / 2, world.height() - screenHeight));
    }

}
