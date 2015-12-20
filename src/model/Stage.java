package model;

import java.util.List;

public interface Stage {
    
    public String getName();

    public void setName(String name);

    public List<Entity> getEnemyList();
    
}
