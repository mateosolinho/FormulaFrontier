package com.mygdx.game.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BodyHolder;
import com.mygdx.game.Tools.MapLoader;


public class Car extends BodyHolder {
    public static int DRIVE_2WD = 0;
    public static int DRIVE_4WD = 1;

    public static final int DRIVE_DIRECTION_NONE = 0;
    public static final int DRIVE_DIRECTION_FORWARD = 1;
    public static final int DRIVE_DIRECTION_BACKWARD = 2;

    public static final int TURN_DIRECTION_NONE = 0;
    public static final int TURN_DIRECTION_LEFT = 1;
    public static final int TURN_DIRECTION_RIGHT = 2;

    private Vector2 WHEEL_SIZE = new Vector2(16, 32);
    private float LINEAR_DAMPING = 0.5f;
    private float RESTITUTION = 0.2f;

    private static final float MAX_WHEEL_ANGLE = 20.0f;
    private static final float WHEEL_TURN_INCREMENT = 1.0f;

    private static float WHEEL_OFFSET_X = 64;
    private static float WHEEL_OFFSET_Y = 80;
    private static int WHEEL_NUMBER = 4;

    private static float BREAK_POWER = 1.3f;
    private static float REVERSE_POWER = 0.5f;

    private int driveDirection = DRIVE_DIRECTION_NONE;
    private int turnDirection = TURN_DIRECTION_NONE;

    private float currentWheelAngle = 0;
    private final Array<Wheel> allWheels = new Array<Wheel>();
    private final Array<Wheel> revolvingWheels = new Array<Wheel>();
    private final float drift;
    private float currentMaxSpeed;
    private final float regularMaxSpeed;
    private final float acceleration;

    public Car(final float maxSpeed, final float drift, final float acceleration, final MapLoader mapLoader, final int wheelDrive, final World world) {
        super(mapLoader.getPlayer());
        this.regularMaxSpeed = maxSpeed;
        this.drift = drift;
        this.acceleration = acceleration;
        getBody().setLinearDamping(LINEAR_DAMPING);
        getBody().getFixtureList().get(0).setRestitution(RESTITUTION);
        createWheels(world, wheelDrive);
    }

    private void createWheels(final World world, final int wheelDrive) {
        for (int i = 0; i < WHEEL_NUMBER; i++) {
            float xOffset = 0;
            float yOffset = 0;

            switch (i) {
                case Wheel.UPPER_LEFT:
                    xOffset = -WHEEL_OFFSET_X;
                    yOffset = WHEEL_OFFSET_Y;
                    break;
                case Wheel.UPPER_RIGHT:
                    xOffset = WHEEL_OFFSET_X;
                    yOffset = WHEEL_OFFSET_Y;
                    break;
                case Wheel.DOWN_LEFT:
                    xOffset = -WHEEL_OFFSET_X;
                    yOffset = -WHEEL_OFFSET_Y;
                    break;
                case Wheel.DOWN_RIGHT:
                    xOffset = WHEEL_OFFSET_X;
                    yOffset = -WHEEL_OFFSET_Y;
                    break;
            }
            final boolean powered = wheelDrive == DRIVE_4WD || (wheelDrive == DRIVE_2WD && i < 2);

            final Wheel wheel = new Wheel(
                    new Vector2(getBody().getPosition().x * 50.0f + xOffset, getBody().getPosition().y * 50.0f + yOffset),
                    WHEEL_SIZE,
                    world,
                    i,
                    this,
                    powered);

            if (i < 2) {
                final RevoluteJointDef jointDef = new RevoluteJointDef();
                jointDef.initialize(getBody(), wheel.getBody(), wheel.getBody().getWorldCenter());
                jointDef.enableMotor = false;
                world.createJoint(jointDef);
            } else {
                final PrismaticJointDef jointDef = new PrismaticJointDef();
                jointDef.initialize(getBody(), wheel.getBody(), wheel.getBody().getWorldCenter(), new Vector2(1, 0));
                jointDef.enableLimit = true;
                jointDef.lowerTranslation = jointDef.upperTranslation = 0;
                world.createJoint(jointDef);
            }

            allWheels.add(wheel);
            if (i < 2) {
                revolvingWheels.add(wheel);
            }
            wheel.setDrift(drift);
        }

    }

    private void processInput() {
        final Vector2 baseVector = new Vector2(0, 0);

        if (turnDirection == TURN_DIRECTION_LEFT) {
            if (currentWheelAngle < 0) {
                currentWheelAngle = 0;
            }
            currentWheelAngle = Math.min(currentWheelAngle += WHEEL_TURN_INCREMENT, MAX_WHEEL_ANGLE);
        } else if (turnDirection == TURN_DIRECTION_RIGHT) {
            if (currentWheelAngle > 0) {
                currentWheelAngle = 0;
            }
            currentWheelAngle = Math.max(currentWheelAngle -= WHEEL_TURN_INCREMENT, -MAX_WHEEL_ANGLE);
        } else {
            currentWheelAngle = 0;
        }

        for (final Wheel wheel : new Array.ArrayIterator<Wheel>(revolvingWheels)) {
            wheel.setAngle(currentWheelAngle);
        }

        if (driveDirection == DRIVE_DIRECTION_FORWARD) {
            baseVector.set(0, acceleration);
        } else if (driveDirection == DRIVE_DIRECTION_BACKWARD) {
            if (direction() == DIRECTION_BACKWARD) {
                baseVector.set(0, -acceleration * REVERSE_POWER);
            } else if (direction() == DIRECTION_FORWARD) {
                baseVector.set(0, -acceleration * BREAK_POWER);
            } else {
                baseVector.set(0, -acceleration);
            }
        }

        currentMaxSpeed = regularMaxSpeed;

        if (getBody().getLinearVelocity().len() < currentMaxSpeed) {
            for (final Wheel wheel : new Array.ArrayIterator<Wheel>(allWheels)) {
                if (wheel.isPowered()) {
                    wheel.getBody().applyForceToCenter(wheel.getBody().getWorldVector(baseVector), true);
                }
            }
        }
    }


    public void setDriveDirection(final int driveDirection) {
        this.driveDirection = driveDirection;
    }


    public void setTurnDirection(final int turnDirection) {
        this.turnDirection = turnDirection;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        processInput();
        for (final Wheel wheel : new Array.ArrayIterator<Wheel>(allWheels)) {
            wheel.update(delta);
        }

    }
}
