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
public class WinScreen extends RestartScreen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int left = 4;
        int top = 9;
        terminal.write("                     __    __   _____      __     _ ",left,top+0);
        terminal.write("/\\_/\\  ___   _   _  / / /\\ \\ \\  \\_   \\  /\\ \\ \\   / \\",left,top+1);
        terminal.write("\\_ _/ / _ \\ | | | | \\ \\/  \\/ /   / /\\/ /  \\/ /  /  /",left,top+2);
        terminal.write(" / \\ | (_) || |_| |  \\  /\\  / /\\/ /_  / /\\  /  /\\_/ ",left,top+3);
        terminal.write(" \\_/  \\___/  \\__,_|   \\/  \\/  \\____/  \\_\\ \\/   \\/   ",left,top+4);
        terminal.write("               Press ENTER to Play again                ", left, top+11);

    }

}
