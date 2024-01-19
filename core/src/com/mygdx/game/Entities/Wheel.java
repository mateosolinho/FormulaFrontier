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

    private static final float WHEEL_DENSITY = 0.4f;

    private static final float DEGTORAD = 0.0174532925199432957f;

    private final boolean mPowered;
    private final Car mCar;

    public Wheel(final Vector2 position, final Vector2 size, final World world, final int id, final Car car, final boolean powered) {
        super(position, size, BodyDef.BodyType.DynamicBody, world, WHEEL_DENSITY, true, id);
        this.mCar = car;
        this.mPowered = powered;
    }

    public void setAngle(final float angle) {
        getBody().setTransform(getBody().getPosition(), mCar.getBody().getAngle() + angle * DEGTORAD);
    }

    public boolean isPowered() {
        return mPowered;
    }
}