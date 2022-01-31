package net.prison.foggies.core.utils;

import com.fastasyncworldedit.core.Fawe;
import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import net.prison.foggies.core.OPPrison;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;

public class SchematicUtil {

    private SchematicUtil() {
        throw new AssertionError();
    }

    /**
     * Paste Schematic
     *
     * @param file
     * @param location
     * @throws IOException
     */
    public static void paste(OPPrison plugin, File file, Location location) {

            try {
                final BlockVector3 vector = BlockVector3.at(location.toVector().getX(), location.toVector().getY(), location.toVector().getZ());

                Clipboard schematic = FaweAPI.load(file);
                EditSession editSession = Fawe.get().getWorldEdit().newEditSession(FaweAPI.getWorld(location.getWorld().getName()));

                FaweAPI.createQueue(editSession.getWorld(), true).enableQueue();

                Operation operation = new ClipboardHolder(schematic)
                        .createPaste(editSession)
                        .ignoreAirBlocks(true)
                        .to(vector).build();

                try {
                    Operations.complete(operation);
                    editSession.close();
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }

            } catch (MaxChangedBlocksException | IOException e) {
                e.printStackTrace();
            }

    }

}
