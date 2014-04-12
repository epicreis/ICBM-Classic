package icbm;

import java.io.File;
import java.util.Arrays;

import net.minecraftforge.common.Configuration;
import calclavia.lib.config.Config;
import calclavia.lib.content.IDManager;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModMetadata;

/** Settings class for various configuration settings.
 * 
 * @author Calclavia */
public class Settings
{
    /** Auto-incrementing configuration IDs. Use this to make sure no config ID is the same. */
    public static final IDManager idManager = new IDManager(3880, 19220);
	public static final String DOMAIN = "icbm";

	public static int getNextBlockID()
    {
        return idManager.getNextBlockID();
    }

    public static int getNextItemID()
    {
        return idManager.getNextItemID();
    }

    /** Configuration file for ICBM. */
    public static final Configuration CONFIGURATION = new Configuration(new File(Loader.instance().getConfigDir(), "ICBM.cfg"));

    /** Should ICBM use external fuel? **/
    @Config(key = "Use Fuel", category = Configuration.CATEGORY_GENERAL)
    public static boolean USE_FUEL = true;
    @Config(key = "Allow Chunk Loading", category = Configuration.CATEGORY_GENERAL)
    public static boolean LOAD_CHUNKS = true;
    @Config(key = "Max Missile Distance", category = Configuration.CATEGORY_GENERAL)
    public static int DAO_DAN_ZUI_YUAN = 10000;
    @Config(key = "Antimatter Explosion Size", category = Configuration.CATEGORY_GENERAL)
    public static int ANTIMATTER_SIZE = 55;
    @Config(key = "Antimatter Destroy Bedrock", category = Configuration.CATEGORY_GENERAL)
    public static boolean DESTROY_BEDROCK = true;
    @Config(key = "Max Tier the Rocket Launcher can Fire", category = Configuration.CATEGORY_GENERAL)
    public static int MAX_ROCKET_LAUCNHER_TIER = 2;

    @Deprecated
    public static void initiate()
    {
        CONFIGURATION.load();
        USE_FUEL = CONFIGURATION.get(Configuration.CATEGORY_GENERAL, "Use Fuel", Settings.USE_FUEL).getBoolean(Settings.USE_FUEL);
        LOAD_CHUNKS = Settings.CONFIGURATION.get(Configuration.CATEGORY_GENERAL, "Allow Chunk Loading", LOAD_CHUNKS).getBoolean(LOAD_CHUNKS);
        DAO_DAN_ZUI_YUAN = Settings.CONFIGURATION.get(Configuration.CATEGORY_GENERAL, "Max Missile Distance", Settings.DAO_DAN_ZUI_YUAN).getInt(Settings.DAO_DAN_ZUI_YUAN);
        ANTIMATTER_SIZE = Settings.CONFIGURATION.get(Configuration.CATEGORY_GENERAL, "Antimatter Explosion Size", ANTIMATTER_SIZE).getInt(ANTIMATTER_SIZE);
        DESTROY_BEDROCK = Settings.CONFIGURATION.get(Configuration.CATEGORY_GENERAL, "Antimatter Destroy Bedrock", DESTROY_BEDROCK).getBoolean(DESTROY_BEDROCK);
        MAX_ROCKET_LAUCNHER_TIER = Settings.CONFIGURATION.get(Configuration.CATEGORY_GENERAL, "Limits the max missile tier for rocket launcher item", MAX_ROCKET_LAUCNHER_TIER).getInt(MAX_ROCKET_LAUCNHER_TIER);
        CONFIGURATION.save();
    }

    public static void setModMetadata(String id, String name, ModMetadata metadata)
    {
        setModMetadata(id, name, metadata, "");
    }

    public static void setModMetadata(String id, String name, ModMetadata metadata, String parent)
    {
        metadata.modId = id;
        metadata.name = name;
        metadata.description = "ICBM is a Minecraft Mod that introduces intercontinental ballistic missiles to Minecraft. But the fun doesn't end there! This mod also features many different explosives, missiles and machines classified in three different tiers. If strategic warfare, carefully coordinated airstrikes, messing with matter and general destruction are up your alley, then this mod is for you!";
        metadata.url = "http://www.calclavia.com/icbm/";
        metadata.logoFile = "/icbm_logo.png";
        metadata.version = Reference.VERSION;
        metadata.authorList = Arrays.asList(new String[] { "Calclavia" });
        metadata.parent = parent;
        metadata.credits = "Please visit the website.";
        metadata.autogenerated = false;
    }

}
