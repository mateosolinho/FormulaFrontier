package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Tools.ShapeFactory;

public abstract class BodyHolder {

    protected static final int DIRECTION_NONE = 0;
    protected static final int DIRECTION_FORWARD = 1;
    protected static final int DIRECTION_BACKWARD = 2;

    // Punto en el cual comienza a derrapar
    private static final float DRIFT_OFFSET = 3f;

    private Vector2 forwardSpeed;
    private Vector2 lateralSpeed;

    private final Body body;
    private float drift = 1;
    private final int id;

    public BodyHolder(final Body mBody) {
        this.body = mBody;
        id = -1;
    }

    public BodyHolder(Vector2 position, Vector2 size, BodyDef.BodyType type, World world, float density, boolean sensor, int id) {
        body = ShapeFactory.createRectangle(position, size, type, world, density, sensor);
        this.id = id;
    }

    public void update(final float delta) {
        if (drift < 1) {
            forwardSpeed = getForwardVelocity();
            lateralSpeed = getLateralVelocity();
            if (lateralSpeed.len() < DRIFT_OFFSET && id > 1) {
                killDrift();
            } else {
                handleDrift();
            }
        }
    }

    public void setDrift(final float drift) {

        this.drift = drift;
    }

    public Body getBody() {

        return body;
    }

    private void handleDrift() {
        final Vector2 forwardSpeed = getForwardVelocity();
        final Vector2 lateralSpeed = getLateralVelocity();
        body.setLinearVelocity(forwardSpeed.x + lateralSpeed.x * drift, forwardSpeed.y + lateralSpeed.y * drift);
    }

    private Vector2 getForwardVelocity() {
        final Vector2 currentNormal = body.getWorldVector(new Vector2(0, 1));
        final float dotProduct = currentNormal.dot(body.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    public void killDrift() {

        body.setLinearVelocity(forwardSpeed);
    }

    private Vector2 getLateralVelocity() {
        final Vector2 currentNormal = body.getWorldVector(new Vector2(1, 0));
        final float dotProduct = currentNormal.dot(body.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    public int direction() {
        final float tolerance = 0.2f;
        if (getLocalVelocity().y < -tolerance) {
            return DIRECTION_BACKWARD;
        } else if (getLocalVelocity().y > tolerance) {
            return DIRECTION_FORWARD;
        } else {
            return DIRECTION_NONE;
        }
    }


    private Vector2 getLocalVelocity() {
        return body.getLocalVector(body.getLinearVelocityFromLocalPoint(new Vector2(0, 0)));
    }


    private Vector2 multiply(float a, Vector2 v) {

        return new Vector2(a * v.x, a * v.y);
    }
}
