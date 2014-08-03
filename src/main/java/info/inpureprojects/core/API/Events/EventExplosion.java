package info.inpureprojects.core.API.Events;

import cpw.mods.fml.common.eventhandler.Event;

/**
 * Created by den on 8/2/2014.
 */
public class EventExplosion extends Event {

    private float chance;

    public EventExplosion(float chance) {
        this.chance = chance;
    }

    public float getChance() {
        return chance;
    }

    public void setChance(float chance) {
        this.chance = chance;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
