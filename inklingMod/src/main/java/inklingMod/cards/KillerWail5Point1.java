package inklingMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.characters.TheInkling;

public class KillerWail5Point1 extends CustomCard {
  public static final String ID = InklingMod.makeID(KillerWail5Point1.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("KillerWail5Point1.png");
  private static final CardRarity RARITY = CardRarity.RARE;
  private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
  private static final CardType TYPE = CardType.ATTACK;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = 3;
  private static final int DAMAGE = 3;
  private static final int UPGRADE_PLUS_DMG = 3;

  public KillerWail5Point1() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    this.baseDamage = DAMAGE;
    this.exhaust = true;
    this.isMultiDamage = true;
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
      for (int i = 0; i < 6; i++) {
        // We have our card compute `multiDamage` so we know how much damage
        // to do to each target. But we have to look up the monster's index in
        // the monster list in order to know which `multiDamage` entry to use!
        AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(
            null, true, AbstractDungeon.cardRandomRng);
        int monsterIndex = AbstractDungeon.getMonsters().monsters.indexOf(target);
        int damage = this.multiDamage[monsterIndex];
        addToBot(new DamageAction(target,
            new DamageInfo(p, damage, this.damageTypeForTurn),
            i % 2 == 0 ? AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
                : AbstractGameAction.AttackEffect.SLASH_VERTICAL));
      }
    }
  }

  @Override
  public void upgrade() {
    if (!upgraded) {
      upgradeName();
      upgradeDamage(UPGRADE_PLUS_DMG);
      initializeDescription();
    }
  }
}