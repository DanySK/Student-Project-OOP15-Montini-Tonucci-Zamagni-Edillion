package model.skills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SkillProvider {

    private static SkillProvider SINGLETON;
    private List<Skills> globalList = new ArrayList<>(Arrays.asList(Skills.values()));
    
    
    private SkillProvider() {    }

    public static SkillProvider get() {
        if (SINGLETON == null) {
            synchronized (SkillProvider.class) {
                if (SINGLETON == null) {
                    SINGLETON = new SkillProvider();
                }
            }
        }
        return SINGLETON;
    }

    /**
     * 
     * @param type
     * @return
     */
    public List<Skills> getSkillList(SkillType... type) {
        List<Skills> list = new ArrayList<>();
        boolean flag;
        //TODO Lo scriverò prima o poi con le lambda... o comuque in un modo meno brutto
        //list.addAll(globalList.stream().filter(s -> s.getAssign().stream().anyMatch(predicate))); 
        for (Skills s : globalList) {
            flag = false;
            for (SkillType t : s.getAssign()) {
                for (SkillType a : type) {
                    if (a.equals(t)) {
                        flag = true;
                    }
                }
            }
            if (flag) {
                list.add(s);
            }
            flag = false;
        }
        //source: http://stackoverflow.com/questions/4805606/how-to-sort-by-two-fields-in-java
        Comparator<Skills> comparator = Comparator.comparing(s -> s.getRequiredLevel());
        comparator = comparator.thenComparing(Comparator.comparing(s -> s.getDamage()));
        list.sort(comparator);
        return list;
    }
    
}
