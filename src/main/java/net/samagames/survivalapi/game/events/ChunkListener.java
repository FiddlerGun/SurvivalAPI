package net.samagames.survivalapi.game.events;

import net.samagames.survivalapi.SurvivalAPI;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldLoadEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ChunkListener class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class ChunkListener implements Runnable, Listener
{
    private final ConcurrentHashMap<Chunk, Long> lastChunkCleanUp;

    /**
     * Constructor
     */
    public ChunkListener()
    {
        this.lastChunkCleanUp = new ConcurrentHashMap<>();
    }

    /**
     * Clean the cache
     */
    @Override
    public void run()
    {
        long currentTime = System.currentTimeMillis();

        List<Map.Entry<Chunk, Long>> temp = new ArrayList<>();
        temp.addAll(this.lastChunkCleanUp.entrySet());

        for (Map.Entry<Chunk, Long> entry : temp)
        {
            Chunk chunk = entry.getKey();

            if (!chunk.isLoaded() || (currentTime - entry.getValue() <= 60000))
                continue;

            for (Entity entity : chunk.getEntities())
                if (!(entity instanceof Item || entity instanceof HumanEntity || entity instanceof Minecart))
                    entity.remove();

            this.lastChunkCleanUp.remove(chunk);
        }
    }

    /**
     * Save unloaded chunk
     *
     * @param event Event
     */
    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event)
    {
        for (Entity entity : event.getChunk().getEntities())
            if (!(entity instanceof Item || entity instanceof HumanEntity || entity instanceof Minecart))
                entity.remove();

        //event.setCancelled(true);
    }

    /**
     * Listen when world loaded
     */

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event)
    {
        //NOFIXME if nether exist think about it. EDIT : No need to do it =P
        //Continue process of start by call the chain
        SurvivalAPI.get().fireEvents(SurvivalAPI.EventType.WORLDLOADED);
    }
}
