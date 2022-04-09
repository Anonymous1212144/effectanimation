package clientmods.effectanimation;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Effectanimation implements ModInitializer {

    public static final Item BAD_OMEN = new Item(new Item.Settings());
    public static final Item HERO_OF_THE_VILLAGE = new Item(new Item.Settings());

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("effectanimation", "bad_omen"), BAD_OMEN);
        Registry.register(Registry.ITEM, new Identifier("effectanimation", "hero_of_the_village"), HERO_OF_THE_VILLAGE);
    }
}
