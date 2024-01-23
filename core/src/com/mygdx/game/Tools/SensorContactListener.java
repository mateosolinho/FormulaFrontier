package com.mygdx.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Gamemodes.TimeAttack;
import com.mygdx.game.Screens.PlayScreen;

public class SensorContactListener implements ContactListener {
    public static int vVueltas;
    private boolean isCheck1Activated = false;
    private boolean isCheck2Activated = false;
    private final ButtonCreator buttonCreator;

    public SensorContactListener(ButtonCreator buttonCreator) {
        this.buttonCreator = buttonCreator;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if ("player".equals(fixtureA.getBody().getUserData()) && "check1".equals(fixtureB.getBody().getUserData())
                || "check1".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {
            isCheck1Activated = true;
        }
        if ("player".equals(fixtureA.getBody().getUserData()) && "check2".equals(fixtureB.getBody().getUserData())
                || "check2".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {
            isCheck2Activated = isCheck1Activated;
        }

        if ("player".equals(fixtureA.getBody().getUserData()) && "meta".equals(fixtureB.getBody().getUserData())
                || "meta".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {
                    PlayScreen.timeAttack.setStartTime(true);
        }

        if (isCheck1Activated) {
            if (isCheck2Activated) {
                if ("player".equals(fixtureA.getBody().getUserData()) && "meta".equals(fixtureB.getBody().getUserData())
                        || "meta".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {
                    vVueltas++;

                    TimeAttack.addNewTime();
                    TimeAttack.setTiempo(0);
                    buttonCreator.updateVueltas(vVueltas);
                    isCheck1Activated = false;
                    isCheck2Activated = false;
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
