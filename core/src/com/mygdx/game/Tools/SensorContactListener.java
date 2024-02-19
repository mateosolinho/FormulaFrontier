package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Game;
import com.mygdx.game.Gamemodes.TimeAttack;
import com.mygdx.game.Screens.PlayScreen;

public class SensorContactListener implements ContactListener {
    public static int vVueltas;
    private boolean isCheck1Activated = false;
    private boolean isCheck2Activated = false;
    private boolean isCheck3Activated = false;
    private final ButtonCreator buttonCreator;
    private PreferencesManager preferencesManager;

    public SensorContactListener(ButtonCreator buttonCreator) {
        this.buttonCreator = buttonCreator;

    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if ("player".equals(fixtureA.getBody().getUserData()) && "check3".equals(fixtureB.getBody().getUserData())
                || "check3".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {
            if (isCheck3Activated && !isCheck2Activated) {
                isCheck3Activated = false;
            } else {
                isCheck3Activated = true;
            }
        }

        if ("player".equals(fixtureA.getBody().getUserData()) && "check2".equals(fixtureB.getBody().getUserData())
                || "check2".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {
            isCheck2Activated = isCheck1Activated;
        }

        if ("player".equals(fixtureA.getBody().getUserData()) && "check1".equals(fixtureB.getBody().getUserData())
                || "check1".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {
            isCheck1Activated = true;
        }


        if ("player".equals(fixtureA.getBody().getUserData()) && "meta".equals(fixtureB.getBody().getUserData())
                || "meta".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {

            PlayScreen.timeAttack.setStartTime(true);

        }

        if ("player".equals(fixtureA.getBody().getUserData()) && "wall".equals(fixtureB.getBody().getUserData())
                || "wall".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {
            if (PreferencesManager.getVibracion()) {
                Gdx.input.vibrate(55);
            }
        }

        if (isCheck1Activated) {
            if (isCheck2Activated) {
                if (isCheck3Activated) {
                    if ("player".equals(fixtureA.getBody().getUserData()) && "meta".equals(fixtureB.getBody().getUserData())
                            || "meta".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {
                        vVueltas++;

                        TimeAttack.addNewTime();
                        TimeAttack.setTiempo(0);
                        buttonCreator.updateVueltas(vVueltas);
                        isCheck1Activated = false;
                        isCheck2Activated = false;
                        isCheck3Activated = false;
                    }
                }
            }
        }

        if (("player".equals(fixtureA.getBody().getUserData()) && "ext".equals(fixtureB.getBody().getUserData()))
                || ("ext".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData()))) {
            PlayScreen.modifyDriveSpeed(50.0f, 1.5f, 30f);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (("player".equals(fixtureA.getBody().getUserData()) && "ext".equals(fixtureB.getBody().getUserData()))
                || ("ext".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData()))) {
            PlayScreen.modifyDriveSpeed(150.0f, 2.5f, 55f);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
