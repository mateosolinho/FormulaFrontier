package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class SensorContactListener implements ContactListener {
    private int vueltas;
    private ButtonCreator buttonCreator;

    public SensorContactListener(ButtonCreator buttonCreator){
        this.buttonCreator = buttonCreator;
    }
    public int getVueltas() {
        return vueltas;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if ("player".equals(fixtureA.getBody().getUserData()) && "meta".equals(fixtureB.getBody().getUserData())
                || "meta".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {
            Gdx.app.log("Contact", "¡El jugador y la meta han colisionado!");
            vueltas++;
            buttonCreator.updateVueltas(vueltas);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
