package com.oshewo.panic.powerups;

import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.BasePowerup;

public class ExtraLife extends BasePowerup {

    PiazzaPanic game;

    public ExtraLife(PiazzaPanic game){
        super(game, "heart.png");
        this.game = game;
    }

    @Override
    public void usePowerup() {

}
