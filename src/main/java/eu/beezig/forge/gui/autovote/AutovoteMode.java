/*
 * This file is part of BeezigForge.
 *
 * BeezigForge is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BeezigForge is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BeezigForge.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.beezig.forge.gui.autovote;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AutovoteMode {
    BED("BedWars"), SKY("SkyWars"),
    TIMV("Trouble in Mineville"), DR("DeathRun"),
    GRAV("Gravity"), HIDE("Hide and Seek"),
    GNT("SkyGiants"), SGN("Survival Games 2"),
    CAI("Cowboys and Indians"), MIMV("Murder in Mineville");


    private String display;

    AutovoteMode(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public static AutovoteMode get(int index) {
        return AutovoteMode.values()[index];
    }

    public static ArrayList<String> getDisplays() {
        return Stream.of(values()).map(AutovoteMode::getDisplay).collect(Collectors.toCollection(ArrayList::new));
    }
}
