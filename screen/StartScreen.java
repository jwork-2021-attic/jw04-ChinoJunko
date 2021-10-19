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

import asciiPanel.AsciiPanel;

/**
 *
 * @author Aeranythe Echosong
 */
public class StartScreen extends RestartScreen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int left = 2;
        int top = 7;
        terminal.write("ooo        ooooo       .o.        oooooooooooo oooooooooooo ",left,top+0);
        terminal.write("`88.       .888'      .888.      d'\"\"\"\"\"\"d888' `888'     `8 ",left,top+1);
        terminal.write(" 888b     d'888      .8\"888.           .888P    888        ",left,top+2);
        terminal.write(" 8 Y88. .P  888     .8' `888.         d888'     888oooo8    ",left,top+3);
        terminal.write(" 8  `888'   888    .88ooo8888.      .888P       888    \"    ",left,top+4);
        terminal.write(" 8    Y     888   .8'     `888.    d888'    .P  888       o ",left,top+5);
        terminal.write("o8o        o888o o88o     o8888o .8888888888P  o888ooooood8 ",left,top+6);
        terminal.write("                    Press ENTER to Start                    ", left, top+11);

    }

}
