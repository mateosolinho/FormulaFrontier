package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Tools.ShapeFactory;

public abstract class BodyHolder {

    // Constantes para direcciones
    protected static final int DIRECTION_NONE = 0;
    protected static final int DIRECTION_FORWARD = 1;
    protected static final int DIRECTION_BACKWARD = 2;

    // Punto en el cual comienza a derrapar
    private static final float DRIFT_OFFSET = 1.0f;

    private Vector2 forwardSpeed;
    private Vector2 lateralSpeed;

    private final Body body;
    private float drift = 1;
    private final int id;

    /**
     * Constructor para un objeto BodyHolder.
     *
     * @param mBody Cuerpo Box2D asociado al objeto.
     */
    public BodyHolder(final Body mBody) {
        this.body = mBody;
        id = -1;
    }

    /**
     * Constructor para crear un nuevo objeto BodyHolder con un cuerpo rectangular.
     *
     * @param position Posición inicial del objeto.
     * @param size Tamaño del objeto.
     * @param type Tipo de cuerpo Box2D (estático, dinámico o cinemático).
     * @param world Instancia del mundo Box2D donde se creará el cuerpo.
     * @param density Densidad del cuerpo para simulación de física.
     * @param sensor Indica si el cuerpo debe ser un sensor (no afecta colisiones físicas).
     * @param id Identificador del objeto.
     */
    public BodyHolder(Vector2 position, Vector2 size, BodyDef.BodyType type, World world, float density, boolean sensor, int id) {
        body = ShapeFactory.createRectangle(position, size, type, world, density, sensor);
        this.id = id;
    }

    /**
     * Actualiza el estado del objeto en el mundo.
     *
     * @param delta Tiempo transcurrido desde la última actualización.
     */
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

    /**
     * Maneja el drift del objeto aplicando velocidades laterales.
     */
    private void handleDrift() {
        final Vector2 forwardSpeed = getForwardVelocity();
        final Vector2 lateralSpeed = getLateralVelocity();
        body.setLinearVelocity(forwardSpeed.x + lateralSpeed.x * drift, forwardSpeed.y + lateralSpeed.y * drift);
    }

    /**
     * Obtiene la velocidad hacia adelante del objeto.
     *
     * @return Vector de velocidad hacia adelante.
     */
    private Vector2 getForwardVelocity() {
        final Vector2 currentNormal = body.getWorldVector(new Vector2(0, 1));
        final float dotProduct = currentNormal.dot(body.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    /**
     * Reinica el drift del objeto a su velocidad original.
     */
    public void killDrift() {

        body.setLinearVelocity(forwardSpeed);
    }

    /**
     * Obtiene la velocidad lateral del objeto.
     *
     * @return Vector de velocidad lateral.
     */
    private Vector2 getLateralVelocity() {
        final Vector2 currentNormal = body.getWorldVector(new Vector2(1, 0));
        final float dotProduct = currentNormal.dot(body.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    /**
     * Determina la dirección del objeto (adelante, atrás o ninguna).
     *
     * @return Dirección del objeto.
     */
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

    /**
     * Obtiene la velocidad del objeto.
     *
     * @return Vector de velocidad local.
     */
    private Vector2 getLocalVelocity() {
        return body.getLocalVector(body.getLinearVelocityFromLocalPoint(new Vector2(0, 0)));
    }

    /**
     * Multiplica un escalar por un vector.
     *
     * @param a Escalar.
     * @param v Vector a multiplicar.
     * @return Nuevo vector resultado de la multiplicación.
     */
    private Vector2 multiply(float a, Vector2 v) {

        return new Vector2(a * v.x, a * v.y);
    }
}
