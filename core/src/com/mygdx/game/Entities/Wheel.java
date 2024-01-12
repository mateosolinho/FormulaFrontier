package com.mygdx.game.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BodyHolder;

/**
 * Clase que representa una rueda de un vehículo.
 */
public class Wheel extends BodyHolder {
    /** Identificadores de cada una de las ruedas dependiendo de su posición**/
    public static final int UPPER_LEFT = 0;
    public static final int UPPER_RIGHT = 1;
    public static final int DOWN_LEFT = 2;
    public static final int DOWN_RIGHT = 3;

    // Forma de tracción
    private final boolean powered;
    private final Car car;

    /**
     * Constructor de la rueda.
     *
     * @param position Posición de la rueda.
     * @param size Tamaño de la rueda.
     * @param world Mundo Box2D donde se creará la rueda.
     * @param id Identificador de la rueda.
     * @param car Objeto del tipo Car al que está asociada la rueda.
     * @param powered Indica si la rueda está impulsada.
     */
    public Wheel(final Vector2 position, final Vector2 size, final World world, final int id, final Car car, final boolean powered) {
        super(position, size, BodyDef.BodyType.DynamicBody, world, 0.4f, true, id);
        this.car = car;
        this.powered = powered;
    }

    /**
     * Establece el ángulo de la rueda.
     *
     * @param angle Ángulo de la rueda en radianes.
     */
    public void setAngle(final float angle) {
        getBody().setTransform(getBody().getPosition(), car.getBody().getAngle() + angle * 0.0174532925199432957f);
    }

    /**
     * Verifica si la rueda está impulsada.
     *
     * @return true si la rueda está impulsada, false de lo contrario.
     */
    public boolean isPowered() {
        return powered;
    }
}

