package com.mygdx.game.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BodyHolder;


public class Wheel extends BodyHolder {
    public static final int UPPER_LEFT = 0;
    public static final int UPPER_RIGHT = 1;
    public static final int DOWN_LEFT = 2;
    public static final int DOWN_RIGHT = 3;

    private final boolean powered;
    private final Car car;

    public Wheel(final Vector2 position, final Vector2 size, final World world, final int id, final Car car, final boolean powered) {
        super(position, size, BodyDef.BodyType.DynamicBody, world, 0.4f, true, id);
        this.car = car;
        this.powered = powered;
    }

    public void setAngle(final float angle) {
        getBody().setTransform(getBody().getPosition(), car.getBody().getAngle() + angle * 0.0174532925199432957f);
    }

    public boolean isPowered() {
        return powered;
    }
}

