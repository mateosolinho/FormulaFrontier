package com.mygdx.game.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BodyHolder;
import com.mygdx.game.Screens.MapLoader;

public class Car extends BodyHolder {
    // Variables para indicar la dirección de conducción
    public static int DRIVE_DIRECTION_NONE = 0;
    public static int DRIVE_DIRECTION_FORWARD = 1;
    public static int DRIVE_DIRECTION_BACKWARD = 2;


    // Variables para indicar hacia donde es el giro
    public  static int TURN_DIRECTION_NONE = 0;
    public  static int TURN_DIRECTION_LEFT = 1;
    public  static int TURN_DIRECTION_RIGHT = 2;

    // Velocidad de conducción
    private static float DRIVE_SPEED = 120.0f;
    // Velocidad de giro
    private static float TURN_SPEED = 2.0f;
    // Velocidad máxima
    private static float MAX_SPEED = 35.0f;

    private static float LINEAR_DAMPING = 0.5f;
    private static float RESTITUTION = 0.5f;


    // Variable para comprobar la dirección de conducción del objeto
    private int mDriveDirection = DRIVE_DIRECTION_NONE;
    // Variable para comprobar la direccion de giro del objeto
    private int mTurnDirection = TURN_DIRECTION_NONE;

    private float currentWheelAngle = 0;
    private Array<Wheel> allWheels = new Array<Wheel>();
    private Array<Wheel> revolvingWheels = new Array<Wheel>();
    private float mDrift;
    private float mMaxSpeed;
    private float mAcceleration;

    public Car(float maxSpeed, float drift, float acceleration, MapLoader mapLoader, int wheelDrive, World world) {
        super(mapLoader.getPlayer());
        this.mMaxSpeed = maxSpeed;
        this.mDrift = drift;
        this.mAcceleration = acceleration;
        getBody().setLinearDamping(LINEAR_DAMPING);
        getBody().getFixtureList().get(0).setRestitution(RESTITUTION);
        createWheels(world, wheelDrive);
    }

    private void createWheels(World world, int wheelDrive) {
        for (int i = 0; i< 4; i++){
            float xOffset = 0;
            float yOffset = 0;

            //switch (){

            //}
        }
    }

    private void processInput() {
        Vector2 baseVector = new Vector2(0,0);

        if (mTurnDirection == TURN_DIRECTION_RIGHT){
            getBody().setAngularVelocity(-TURN_SPEED);
        } else if (mTurnDirection == TURN_DIRECTION_LEFT) {
            getBody().setAngularVelocity(TURN_SPEED);
        } else if (mTurnDirection == TURN_DIRECTION_NONE  && getBody().getAngularVelocity() != 0) {
            getBody().setAngularVelocity(0.0f);
        }

        if (mDriveDirection == DRIVE_DIRECTION_FORWARD){
            baseVector.set(0, DRIVE_SPEED);
        } else if (mDriveDirection == DRIVE_DIRECTION_BACKWARD){
            baseVector.set(0, -DRIVE_SPEED);
        }

        // Comprueba si hay una entrada activa para avanzar o retroceder y la velocidad actual del objeto es menor que la velocidad máxima
        if (!baseVector.isZero() && getBody().getLinearVelocity().len() < MAX_SPEED){
            // Aplica una fuerta en el centro del objeto
            getBody().applyForceToCenter(getBody().getWorldVector(baseVector), true);
        }
    }

    public void setDriveDirection(int driveDirection) {
        this.mDriveDirection = driveDirection;
    }

    public void setTurnDirection(int turnDirection) {
        this.mTurnDirection = turnDirection;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        processInput();
    }
}
