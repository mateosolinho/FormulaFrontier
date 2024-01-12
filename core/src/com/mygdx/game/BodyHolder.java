package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import com.mygdx.game.Tools.ShapeFactory;

public class BodyHolder {

    private static int DIRECTION_NONE = 0;
    private static int DIRECTION_FORWARD = 1;
    private static int DIRECTION_BACKWARD = 2;

    private static float DRIFT_OFFSET = 1.0f;

    Vector2 forwardSpeed;
    Vector2 lateralSpeed;

    private Body body;
    // Deceleración del vehiculo al deslizarse o girar
    private float mDrift = 1;

    public BodyHolder(Body body) {
        this.body = body;
    }

    public BodyHolder(Vector2 position, Vector2 size, BodyDef.BodyType type, World world, float density, boolean sensor){
       body = ShapeFactory.createRectangle(position, size, type, world, density, sensor);
    }

    public void update(float delta){
        forwardSpeed = getForwardVelocity();
        lateralSpeed = getLateralVelocity();
        if (lateralSpeed.len() < DRIFT_OFFSET){
            killDrift();
        } else {
            handleDrift();
        }
    }

    public void setDrift(float drift) {
        this.mDrift = drift;
    }

    public Body getBody() {
        return body;
    }

    /**
     * Método para gestionar el deslizamiento del objeto
     * Primero se obtiene la velocidad hacia adelante y lateral del objeto
     * Posteriormente se combinan las velocidades para gestionar el deslizamiento
     */
    private void handleDrift() {
        Vector2 forwardSpeed = getForwardVelocity();
        Vector2 lateralSpeed = getLateralVelocity();
        body.setLinearVelocity(forwardSpeed.x + lateralSpeed.x * mDrift, forwardSpeed.y + lateralSpeed.y * mDrift);
    }

    /**
     * Metodo para gestionar la velocidad hacia adelante del objeto
     * @return multiplica el producto punto * el vector normal para obtener la velocidad hacia adelante
     */
    private Vector2 getForwardVelocity(){
        // Obtiene el vector normal del objeto en relación con el eje y
        Vector2 currentNormal = body.getWorldVector(new Vector2(0,1));
        float dotProduct = currentNormal.dot(body.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    /**
     * Metodo para obtener la velocidad lateral del objeto
     * @return multiplica el producto punto * el vector normal para obtener la velocidad lateral
     */
    private Vector2 getLateralVelocity(){
        Vector2 currentNormal = body.getWorldVector(new Vector2(1,0));
        float dotProduct = currentNormal.dot(body.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    public void killDrift(){
        body.setLinearVelocity(forwardSpeed);
    }

    public int direction(){
        float tolerance = 0.2f;
        if (getLocalVelocity().y < -tolerance){
            return DIRECTION_BACKWARD;
        } else if (getLocalVelocity().y > tolerance) {
            return DIRECTION_FORWARD;
        } else {
            return DIRECTION_NONE;
        }
    }

    private Vector2 getLocalVelocity(){
        return body.getLocalVector(body.getLinearVelocityFromLocalPoint(new Vector2(0,0)));
    }

    /**
     * Vector para multiplicar un escalar por un vector tridimensional
     * @param a El escalar a multiplicar
     * @param v El vector bidimensional a multiplicar
     * @return Un nuevo vector bidimensional resultado de la multiplicación.
     */
    private Vector2 multiply(float a, Vector2 v){
        return new Vector2(a * v.x , a * v.y);
    }
}
