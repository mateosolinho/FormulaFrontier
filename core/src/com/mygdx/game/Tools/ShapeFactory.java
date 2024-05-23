package com.mygdx.game.Tools;

import static com.mygdx.game.Constants.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * 
 */
public class ShapeFactory {
    /**
     * Class to create shapes in a Box2D world.
     *
     * @param position Position of the center of the rectangle.
     * @param size     Rectangle size.
     * @param type     Box2D body type (static, dynamic or kinematic).
     * @param world    Instance of the Box2D world where the object will be created.
     * @param density  Body density for physics simulation.
     * @param sensor   Indicates whether the body should be a sensor (does not affect physical collisions).
     * @return Created Box2D object.
     */
    public static Body createRectangle(Vector2 position, Vector2 size, BodyDef.BodyType type, World world, float density, boolean sensor) {

        // Define el Body
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        bdef.position.set(position.x / PPM, position.y / PPM);
        bdef.type = type;
        Body body = world.createBody(bdef);

        // Define Fixture
        shape.setAsBox(size.x / PPM, size.y / PPM);
        fdef.shape = shape;
        fdef.density = density;
        fdef.isSensor = sensor;
        body.createFixture(fdef);
        shape.dispose();

        return body;
    }
}
