package com.mygdx.game.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BodyHolder;

public class Wheel extends BodyHolder {
    private boolean mPowered;
    private  Car mCar;
    private int mId;
    private static float DEGTORAD = 0.0174532925199432957f;

    public Wheel(Vector2 position, Vector2 size, BodyDef.BodyType type, World world, float density, int id, Car car, boolean powered) {
        super(position, size, type, world, density, true);
        this.mCar = car;
        this.mPowered = powered;
        this.mId = id;
    }

    public void setAngle(float angle){
        getBody().setTransform(getBody().getPosition(), mCar.getBody().getAngle() + angle * DEGTORAD);
    }
}
