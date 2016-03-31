package model.skills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Skill database ready to be assigned by the static method.
 */
public enum SkillData implements Skill {
    // Heroes                 damage - level - mod  -  mana
    PUNCH("Punch",               3,     1,      0,      0, SkillType.BASIC),
    KICK("Kick",                 5,     1,      1,      0, SkillType.MELEE),
    DHIT("Double Hit",           7,     2,      1,      0, SkillType.MELEE),
    SLAM("Slam",                 5,     2,      1,      0, SkillType.MELEE),
    FLARE("Flare",              10,     2,      3,     10, SkillType.FIRESPELL),
    PYROBALL("Pyroball",        16,     3,      5,     18, SkillType.FIRESPELL),
    CPUNCH("Comet Punch",       30,     4,      6,      0, SkillType.MELEE),
    METEOR("Meteor",            50,     5,     20,     32, SkillType.BLACKSPELL),


    // Monsters           damage - level - mod
    ROCK("Rock",                3,      1,      0,      0, SkillType.ORC, SkillType.GOBLIN),
    HIT("Hit",                  5,      2,      1,      0, SkillType.ORC, SkillType.GOBLIN, SkillType.SKULL);

    private final String name;
    private final int damage;
    private final int requiredLevel;
    private final int modifier;
    private final int mana;
    private final SkillType[] assign;

    /**
     * 
     * @param name
     * @param damage
     * @param requiredLevel
     * @param modifier
     * @param assign
     */
    SkillData(final String name, final int damage, final int requiredLevel, final int modifier, final int mana, final SkillType... assign) {
        this.name = name;
        this.damage = damage;
        this.requiredLevel = requiredLevel;
        this.modifier = modifier;
        this.mana = mana;
        this.assign = assign;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public int getRequiredLevel() {
        return this.requiredLevel;
    }

    @Override
    public int getModifier() {
        return this.modifier;
    }

    @Override
    public int getMana() {
        return this.mana;
    }

    @Override
    public SkillType[] getAssign() {
        return assign;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        return str.append(this.name)
                  .append("\t\tDamage: ")
                  .append(this.damage)
                  .append("\tModifier: ")
                  .append(this.modifier)
                  .append("\tRequired Level: ")
                  .append(this.requiredLevel)
                  .append("\tMana Usage: ")
                  .append(this.mana)
                  .append("\tAssigned to: ")
                  .append(Arrays.toString(this.getAssign()))
                  .append("\n")
                  .toString();
    }

    /**
     * For each skill in Skill on the game finds everyone that matches with skilltype from input
     * Then sorts for 1.Level, 2.Damage, 3.Name. 
     * @param type skill types
     * @return skill list
     */
    public static List<SkillData> computeSkillList(final SkillType... type) {

        List<SkillData> list = new ArrayList<>();

        list.addAll(Arrays.asList(SkillData.values()).stream().filter(s -> 
                Arrays.asList(s.getAssign()).stream().anyMatch(st -> Arrays.asList(type).contains(st))
                ).collect(Collectors.toList()));

        //source: http://stackoverflow.com/questions/4805606/how-to-sort-by-two-fields-in-java
        Comparator<SkillData> comparator = Comparator.comparing(s -> s.getRequiredLevel());
        comparator = comparator.thenComparing(Comparator.comparing(s -> s.getDamage()));
        comparator = comparator.thenComparing(Comparator.comparing(s -> s.getName()));
        list.sort(comparator);
        return list;
    }

}
