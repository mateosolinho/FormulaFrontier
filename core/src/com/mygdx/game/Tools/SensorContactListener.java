package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class SensorContactListener implements ContactListener {
    private int vueltas;
    private boolean isCheck1Activated = false;
    private boolean isCheck2Activated = false;
    private ButtonCreator buttonCreator;

    public SensorContactListener(ButtonCreator buttonCreator) {
        this.buttonCreator = buttonCreator;
    }

    public int getVueltas() {
        return vueltas;
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
            if (isCheck1Activated) {
                isCheck2Activated = true;
            } else {
                isCheck2Activated = false;
            }
        }

        if (isCheck1Activated) {
            if (isCheck2Activated) {
                if ("player".equals(fixtureA.getBody().getUserData()) && "meta".equals(fixtureB.getBody().getUserData())
                        || "meta".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {
                    vueltas++;
                    buttonCreator.updateVueltas(vueltas);
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
