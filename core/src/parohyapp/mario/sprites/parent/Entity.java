package parohyapp.mario.sprites.parent;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import javax.annotation.processing.Filer;

import box2dLight.Light;
import parohyapp.mario.TowerClimber;
import parohyapp.mario.sprites.animated.Climber;
import parohyapp.mario.sprites.animated.Creep;
import parohyapp.mario.sprites.standing.gems.Gemstone;
import parohyapp.mario.tools.Update;
import parohyapp.mario.tools.WorldManager;

/**
 * Created by tomas on 3/25/2016.
 */
public abstract class Entity extends Sprite implements Update{
    protected Rectangle bounds;
    protected World world;
    protected WorldManager worldManager;
    protected Body b2Body;

    protected Fixture fixture;

    public static short DEFAULT_BIT = 1;
    public static short CLIMBER_BIT = 2;
    public static short GEMSTONE_BIT = 4;
    public static short DISPOSE_BIT = 8;
    public static short CREEP_BIT = 16;
    public static short CREEP_HEAD_BIT = 32;
    public static short MARK_BIT = 64;
    public static short LSOURCE_BIT = 128;
    public static short SWITCH_BIT = 256;


    public Entity(World world, Rectangle bounds, WorldManager worldManager) {
        this.bounds = bounds;
        this.world = world;
        this.worldManager = worldManager;

        initRectangle();
    }

    private void initRectangle(){
        BodyDef bDef = new BodyDef();
        FixtureDef fixDef = new FixtureDef();
        PolygonShape pShape = new PolygonShape();

        if(this instanceof Climber){
            bDef.type = BodyDef.BodyType.DynamicBody;
            fixDef.filter.categoryBits = CLIMBER_BIT;
            fixDef.filter.maskBits = (short) (DEFAULT_BIT | GEMSTONE_BIT | CREEP_BIT | CREEP_HEAD_BIT | SWITCH_BIT);
        }
        else if(this instanceof Creep){
            bDef.type = BodyDef.BodyType.DynamicBody;
            fixDef.filter.categoryBits = CREEP_BIT;
            fixDef.filter.maskBits = (short) (DEFAULT_BIT | CLIMBER_BIT | MARK_BIT);
        }
        else{
            bDef.type = BodyDef.BodyType.StaticBody;
            fixDef.filter.categoryBits = DEFAULT_BIT;
        }

        bDef.position.set((bounds.getX() + bounds.getWidth() / 2) / TowerClimber.PPM, (bounds.getY() + bounds.getHeight() / 2) / TowerClimber.PPM);
        b2Body = world.createBody(bDef);

        setPolygonBox(pShape,bounds.getWidth() / 2,bounds.getHeight() / 2);
        fixDef.shape = pShape;
        fixture = b2Body.createFixture(fixDef);
        fixture.setUserData(this);
    }

    public void setPolygonBox(PolygonShape shape, float fx, float fy){
        shape.setAsBox(fx / TowerClimber.PPM, fy / TowerClimber.PPM);
    }

    public Body getB2Body() {
        return b2Body;
    }

    @Override
    public void update(float delta){
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    public void setCategoryFilter(short bit){
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);
    }
}
