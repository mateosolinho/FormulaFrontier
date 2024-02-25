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
 * Clase encargada de manejar los contactos entre sensores y otros cuerpos en el juego.
 */
public class SensorContactListener implements ContactListener {

    /**
     * Número de vueltas en la sesión de juego actual
     */
    public static int vVueltas;

    /**
     * Número de vueltas totales de manera general
     */
    public static int vVueltasTotales;

    /**
     * Indica si el primer sensor está activado.
     */
    private boolean isCheck1Activated = false;

    /**
     * Indica si el segundo sensor está activado.
     */
    private boolean isCheck2Activated = false;

    /**
     * Indica si el tercer sensor está activado.
     */
    private boolean isCheck3Activated = false;

    /**
     * Instancia de ButtonCreator para manejar botones.
     */
    private final ButtonCreator buttonCreator;

    /**
     * Instancia de ButtonCreator para manejar preferencias.
     */
    private final PreferencesManager preferencesManager;

    private final AudioManager audioManager;

    /**
     * Constructor de la clase SensorContactListener.
     *
     * @param buttonCreator Instancia de ButtonCreator para manejar botones.
     */
    public SensorContactListener(ButtonCreator buttonCreator) {
        this.buttonCreator = buttonCreator;
        preferencesManager = new PreferencesManager();
        audioManager = new AudioManager();
        vVueltasTotales = preferencesManager.getVueltas();
    }

    /**
     * Método creado cuando se detecta un contacto entre dos fixtures.
     *
     * @param contact El objeto Contact que contiene información sobre el contacto.
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
     * Método creado cuando se deja de detectar un contacto entre dos fixtures.
     *
     * @param contact El objeto Contact que contiene información sobre el contacto.
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
     * Método que se llama antes de resolver el contacto entre fixtures.
     *
     * @param contact     El objeto Contact que contiene información sobre el contacto.
     * @param oldManifold El objeto Manifold que contiene información sobre el antiguo estado del contacto.
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    /**
     * Método que se llama después de resolver el contacto entre fixtures.
     *
     * @param contact El objeto Contact que contiene información sobre el contacto.
     * @param impulse El objeto ContactImpulse que contiene información sobre el impulso del contacto.
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
