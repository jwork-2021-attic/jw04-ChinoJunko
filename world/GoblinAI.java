/*
 * Copyright (C) 2015 Winterstorm
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
package world;

import java.awt.*;
import java.util.Random;

/**
 *
 * @author Winterstorm
 */
public class GoblinAI extends CreatureAI {

    Random random;

    private CreatureFactory factory;

    public GoblinAI(Creature creature, CreatureFactory factory) {
        super(creature);
        this.factory = factory;
        random = new Random();
    }

    public void onUpdate() {
        switch (random.nextInt(4)){
            case 0:
                creature.moveBy(1,0);
                break;
            case 1:
                creature.moveBy(-1,0);
                break;
            case 2:
                creature.moveBy(0,1);
                break;
            case 3:
                creature.moveBy(0,-1);
                break;
        }
    }

    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.setX(x);
            creature.setY(y);
        } else if (tile.isDiggable()) {
            creature.dig(x, y);
        }
    }
}
