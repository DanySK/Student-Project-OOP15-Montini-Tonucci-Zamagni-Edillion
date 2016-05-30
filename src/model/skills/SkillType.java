package model.skills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Skills Type data.
 */
public enum SkillType {
    //Heroes
    BASIC, MELEE, FIRESPELL, WHITESPELL, DISTANCE, BLACKSPELL, ARCHER,

    //Monsters
    SKULL, ORC, GOBLIN;

    @Override
    public String toString() {
        return this.name();
    }

    /**
     * For each skill in SkillData on the game finds everyone that matches with SkillType from input
     * Then sorts for 1.Level, 2.Damage, 3.Name. 
     * @param type skill types
     * @return skill list
     */
    public static List<Skill> getSkillList(final SkillType... type) {
        List<Skill> list = new ArrayList<>();

        list.addAll(Arrays.asList(SkillData.values()).stream().filter(s -> 
                Arrays.asList(s.getAssign()).stream().anyMatch(st -> Arrays.asList(type).contains(st))
                ).collect(Collectors.toList()));

        //source: http://stackoverflow.com/questions/4805606/how-to-sort-by-two-fields-in-java
        Comparator<Skill> comparator = Comparator.comparing(s -> s.getRequiredLevel());
        comparator = comparator.thenComparing(Comparator.comparing(s -> s.getDamage()));
        comparator = comparator.thenComparing(Comparator.comparing(s -> s.getName()));
        list.sort(comparator);
        return list;
    }
}