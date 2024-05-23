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

/**
 * Class for handling contacts between sensors and other game bodies.
 */
public class SensorContactListener implements ContactListener {

    /**
     * Number of laps in the current game session.
     */
    public static int vVueltas;

    /**
     * Number of total laps.
     */
    public static int vVueltasTotales;

    /**
     * Indicates if the first sensor is activated.
     */
    private boolean isCheck1Activated = false;

    /**
     * Indicates if the second sensor is activated.
     */
    private boolean isCheck2Activated = false;

    /**
     * Indicates if the third sensor is activated.
     */
    private boolean isCheck3Activated = false;

    /**
     * ButtonCreator instance to handle buttons.
     */
    private final ButtonCreator buttonCreator;

    /**
     * ButtonCreator instance to manage preferences.
     */
    private final PreferencesManager preferencesManager;

    private final AudioManager audioManager;

    /**
     * SensorContactListener constructor.
     *
     * @param buttonCreator ButtonCreator instance to handle buttons.
     */
    public SensorContactListener(ButtonCreator buttonCreator) {
        this.buttonCreator = buttonCreator;
        preferencesManager = new PreferencesManager();
        audioManager = new AudioManager();
        vVueltasTotales = preferencesManager.getVueltas();
    }

    /**
     * Function created when a contact is detected between two fixtures.
     *
     * @param contact Contact object that contains information about the contact.
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if ("player".equals(fixtureA.getBody().getUserData()) && "check3".equals(fixtureB.getBody().getUserData())
                || "check3".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {
            isCheck3Activated = !isCheck3Activated || isCheck2Activated;
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

            if (PreferencesManager.getMusica()) {
                audioManager.crashEffect();
            }
        }

        if (isCheck1Activated) {
            if (isCheck2Activated) {
                if (isCheck3Activated) {
                    if ("player".equals(fixtureA.getBody().getUserData()) && "meta".equals(fixtureB.getBody().getUserData())
                            || "meta".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData())) {
                        vVueltas++;
                        vVueltasTotales++;

                        TimeAttack.addNewTime();
                        TimeAttack.setTiempo(0);
                        buttonCreator.updateVueltas(vVueltas);
                        preferencesManager.guardarVueltas(vVueltasTotales);
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

    /**
     * Function created when a contact between two fixtures is no longer detected.
     *
     * @param contact Contact object that contains information about the contact.
     */
    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (("player".equals(fixtureA.getBody().getUserData()) && "ext".equals(fixtureB.getBody().getUserData()))
                || ("ext".equals(fixtureA.getBody().getUserData()) && "player".equals(fixtureB.getBody().getUserData()))) {
            PlayScreen.modifyDriveSpeed(150.0f, 2.5f, 55f);
        }
    }

    /**
     * Function that is called before resolving the contact between fixtures.
     *
     * @param contact     Contact object that contains information about the contact.
     * @param oldManifold Manifold object that contains information about the old state of the contact.
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    // /**
    //  * Function that is called after resolving the contact between fixtures.
    //  *
    //  * @param contact Contact object that contains information about the contact.
    //  * @param impulse ContactImpulse object that contains information about the contact impulse.
    //  */
    // @Override
    // public void postSolve(Contact contact, ContactImpulse impulse) {
    // }
}
