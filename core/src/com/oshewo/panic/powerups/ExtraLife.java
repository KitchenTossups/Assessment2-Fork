package com.oshewo.panic.powerups;

import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.BasePowerUp;

public class ExtraLife extends BasePowerUp {

    PiazzaPanic game;

    public ExtraLife(PiazzaPanic game){
        super(game, "heart.png");
        this.game = game;
    }

    @Override
    public void usePowerUp() {

    }
}
