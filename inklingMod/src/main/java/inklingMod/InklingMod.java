package inklingMod;

import basemod.*;
import basemod.interfaces.*;
import inklingMod.cards.Strike;
import inklingMod.characters.TheInkling;
import inklingMod.relics.FreshKicks;
import inklingMod.util.IDCheckDontTouchPls;
import inklingMod.util.TextureLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

// <Removed instructions for renaming the package etc>

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class InklingMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki).
    // Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to
    // make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(InklingMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    // This is for the in-game mod settings panel.
    private static final String MODNAME = "The Inkling";
    private static final String AUTHOR = "Matchu"; // And pretty soon - You!
    private static final String DESCRIPTION = "A character inspired by Splatoon.";

    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);

    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark
                                                                                                    // Red/Brown

    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE
    // PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE
    // PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE
    // PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE
    // PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE
    // PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE
    // PATHS!!!!!!!!!!!

    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "inklingModResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "inklingModResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "inklingModResources/images/512/bg_power_default_gray.png";

    private static final String ENERGY_ORB_DEFAULT_GRAY = "inklingModResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "inklingModResources/images/512/card_small_orb.png";

    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "inklingModResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "inklingModResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "inklingModResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "inklingModResources/images/1024/card_default_gray_orb.png";

    // Character assets
    private static final String THE_INKLING_BUTTON = "inklingModResources/images/charSelect/InklingCharacterButton.png";
    private static final String THE_INKLING_PORTRAIT = "inklingModResources/images/charSelect/InklingCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "inklingModResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "inklingModResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "inklingModResources/images/char/defaultCharacter/corpse.png";

    // Mod Badge - A small icon that appears in the mod settings menu next to your
    // mod.
    public static final String BADGE_IMAGE = "inklingModResources/images/Badge.png";

    // =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================

    // =============== /INPUT TEXTURE LOCATION/ =================

    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE
    // =================

    public InklingMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        /*
         * ( ( /( ( ( /( ( ( ` ( /( )\ ) )\ ))\ )
         * )\ )\()) )\ )\()))\ ) ( )\))( )\()|()/( (()/(()/(
         * (((_)((_)((((_)( ((_)\(()/( )\ ((_)()\((_)\ /(_)) /(_))(_))
         * )\___ _((_)\ _ )\ _((_)/(_))_((_) (_()((_) ((_|_))_ _(_))(_))_
         * ((/ __| || (_)_\(_) \| |/ __| __| | \/ |/ _ \| \ |_ _|| (_)
         * | (__| __ |/ _ \ | .` | (_ | _| | |\/| | (_) | |) | | | | |) |
         * \___|_||_/_/ \_\|_|\_|\___|___| |_| |_|\___/|___/ |___||___(_)
         */

        setModID("inklingMod");
        // cool
        // <Removed instructions for replacing the Mod ID>

        logger.info("Done subscribing");

        logger.info("Creating the color " + TheInkling.Enums.COLOR_GRAY.toString());

        BaseMod.addColor(TheInkling.Enums.COLOR_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");

    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO
    // ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        // String IDjson =
        // Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8));
        // // i hate u Gdx.files
        InputStream in = InklingMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT
                                                                                                       // THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8),
                IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP
                                                      // JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        // String IDjson =
        // Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8));
        // // i still hate u btw Gdx.files
        InputStream in = InklingMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT
                                                                                                       // THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8),
                IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = InklingMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE,
                                                                                      // THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT
                                                                                                                    // THIS
            } // NO
        } // NO
    }// NO

    // ====== YOU CAN EDIT AGAIN ======

    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        new InklingMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/
    // =================

    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheInkling.Enums.THE_INKLING.toString());

        BaseMod.addCharacter(new TheInkling("the Inkling", TheInkling.Enums.THE_INKLING),
                THE_INKLING_BUTTON, THE_INKLING_PORTRAIT, TheInkling.Enums.THE_INKLING);

        logger.info("Added " + TheInkling.Enums.THE_INKLING.toString());
    }

    // =============== /LOAD THE CHARACTER/ =================

    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }

    // =============== / POST-INITIALIZE/ =================

    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // Take a look at https://github.com/daviscook477/BaseMod/wiki/AutoAdd
        // as well as
        // https://github.com/kiooeht/Bard/blob/e023c4089cc347c60331c78c6415f489d19b6eb9/src/main/java/com/evacipated/cardcrawl/mod/bard/BardMod.java#L319
        // for reference as to how to turn this into an "Auto-Add" rather than having to
        // list every relic individually.
        // Of note is that the bard mod uses it's own custom relic class (not dissimilar
        // to our AbstractDefaultCard class for cards) that adds the 'color' field,
        // in order to automatically differentiate which pool to add the relic too.

        // This adds a character specific relic. Only when you play with the mentioned
        // color, will you get this relic.
        BaseMod.addRelicToCustomPool(new FreshKicks(), TheInkling.Enums.COLOR_GRAY);
        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================

    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        // Ignore this
        pathCheck();

        logger.info("Adding cards");
        // Add the cards
        // Don't delete these default cards yet. You need 1 of each type and rarity
        // (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        // This method automatically adds any cards so you don't have to manually load
        // them 1 by 1
        // For more specific info, including how to exclude cards from being added:
        // https://github.com/daviscook477/BaseMod/wiki/AutoAdd

        // The ID for this function isn't actually your modid as used for prefixes/by
        // the getModID() method.
        // It's the mod id you give MTS in ModTheSpire.json - by default your artifact
        // ID in your pom.xml

        // <Removed instructions to use artifactId here>
        new AutoAdd("InklingMod") // ${project.artifactId}
                .packageFilter(Strike.class) // filters to any class in the same package as
                                             // AbstractDefaultCard, nested packages included
                .setDefaultSeen(true)
                .cards();

        // .setDefaultSeen(true) unlocks the cards
        // This is so that they are all "seen" in the library,
        // for people who like to look at the card list before playing your mod

        logger.info("Done adding cards!");
    }

    // ================ /ADD CARDS/ ===================

    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/InklingMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/InklingMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/InklingMod-Relic-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/InklingMod-Character-Strings.json");

        logger.info("Done edittting strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in
        // Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in
        // your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights
        // the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the
        // first element in NAMES be a long keyword, and the second element be
        // a_long_keyword

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/InklingMod-Keyword-Strings.json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json,
                com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                // getModID().toLowerCase() makes your keyword mod specific (it won't show up in
                // other cards that use that word)
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
