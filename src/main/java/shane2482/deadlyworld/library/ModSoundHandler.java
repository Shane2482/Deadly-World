package shane2482.deadlyworld.library;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public final class ModSoundHandler {

	public static SoundEvent furnace_open;
	public static SoundEvent furnace_close;
	
	
	private static int size = 0;
	
	public static void init() {
		size = SoundEvent.REGISTRY.getKeys().size();
		
		furnace_open = registerSound("furnace_open");
		furnace_close = registerSound("furnace_close");
		
	}
	
	private static SoundEvent registerSound(String name){
        ResourceLocation resLoc = new ResourceLocation(Reference.MOD_ID, name);

        SoundEvent event = new SoundEvent(resLoc);
        SoundEvent.REGISTRY.register(size, resLoc, event);

        size++;
        return event;
    }
	
}