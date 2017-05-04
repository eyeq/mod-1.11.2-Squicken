package eyeq.squicken;

import eyeq.squicken.client.renderer.entity.RenderSquicken;
import eyeq.squicken.entity.passive.EntitySquicken;
import eyeq.squicken.entity.projectile.EntityEggSquicken;
import eyeq.squicken.item.ItemEggSquicken;
import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.common.registry.UEntityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

import static eyeq.squicken.Squicken.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class Squicken {
    public static final String MOD_ID = "eyeq_squicken";

    @Mod.Instance(MOD_ID)
    public static Squicken instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Item eggSquiken;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addRecipes();
        registerEntities();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        registerEntityRenderings();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        eggSquiken = new ItemEggSquicken().setUnlocalizedName("eggSquiken").setCreativeTab(CreativeTabs.MATERIALS);

        GameRegistry.register(eggSquiken, resource.createResourceLocation("egg_squicken"));
    }

    public static void addRecipes() {
        GameRegistry.addShapelessRecipe(new ItemStack(eggSquiken), Items.EGG, new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage()));
    }

    public static void registerEntities() {
        UEntityRegistry.registerModEntity(resource, EntityEggSquicken.class, "SquickenEgg", 0, instance);

        UEntityRegistry.registerModEntity(resource, EntitySquicken.class, "Squicken", 1, instance, 0xA1A1A1, 0x708899);
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(eggSquiken);
    }

	@SideOnly(Side.CLIENT)
    public static void registerEntityRenderings() {
        RenderingRegistry.registerEntityRenderingHandler(EntityEggSquicken.class, manager -> new RenderSnowball(manager, eggSquiken, Minecraft.getMinecraft().getRenderItem()));

        RenderingRegistry.registerEntityRenderingHandler(EntitySquicken.class, RenderSquicken::new);
    }
	
    public static void createFiles() {
    	File project = new File("../1.11.2-Squicken");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, eggSquiken, "Squicken Egg");
        language.register(LanguageResourceManager.JA_JP, eggSquiken, "イカトリの卵");

        language.register(LanguageResourceManager.EN_US, EntityEggSquicken.class, "Egg of Squicken");
        language.register(LanguageResourceManager.JA_JP, EntityEggSquicken.class, "イカトリの卵");
        language.register(LanguageResourceManager.EN_US, EntitySquicken.class, "Squicken");
        language.register(LanguageResourceManager.JA_JP, EntitySquicken.class, "イカトリ");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, eggSquiken, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
    }
}
