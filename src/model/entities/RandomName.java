package model.entities;

/**
 * Name pool to randomize monsters' name.
 *
 */
public enum RandomName {
    EDGAR, BARTHOLOMEW, MARIO, PAUL, CHRISTOPHER, MAURICE, VINZ, ASGWANO;

    @Override
    public String toString() {
        return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
    }
}