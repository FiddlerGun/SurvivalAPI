package net.samagames.survivalapi.utils;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftWolf;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ChunkUtils class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class ChunkUtils
{
    /**
     * Send to a given players the chunks around a given radius
     *
     * @param p The player
     * @param dest The block of the chunk
     * @param radius The radius
     */
    public static void loadDestination(Player p, Location dest, int radius)
    {
        WorldServer handle = ((CraftWorld) dest.getWorld()).getHandle();

        for (int x = -radius; x < radius; x++)
        {
            for (int z = -radius; z < radius; z++)
            {
                Chunk chunk = handle.getChunkAtWorldCoords(new BlockPosition(dest.getBlockX() + x, 0, dest.getBlockZ() + z));
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutMapChunk(chunk, true, 0x11111111));
            }
        }
    }
}