package uk.ac.kent.el334.castleadventure;

import java.util.ArrayList;

public class GateKeeper extends Character{
    private Boolean asleep;
    private Boolean bribed;

    public GateKeeper() {
        asleep = true;
        bribed = false;
        super.type = "gatekeeper";

    }

    public Boolean getAsleep() {
        return asleep;
    }

    public void setAsleep(Boolean asleep) {
        this.asleep = asleep;
    }

    public Boolean getBribed() {
        return bribed;
    }

    public void setBribed(Boolean bribed) {
        this.bribed = bribed;
    }

    public void printIntroduction(){

        if (getAsleep() || getBribed()){
            System.out.println();
            System.out.println(" A Gatekeeper is sleeping, leaving the gate unguarded.");
        }else{
            System.out.println();
            System.out.println(" The Gatekeeper has awakened and questions your actions.");
            System.out.println(" a: go back");
            System.out.println(" b: use item");
        }
    }

}
