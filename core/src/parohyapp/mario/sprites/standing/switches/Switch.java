package parohyapp.mario.sprites.standing.switches;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.Switchable;
import parohyapp.mario.sprites.lights.tools.LightChangeListener;
import parohyapp.mario.sprites.lights.tools.LightStatus;
import parohyapp.mario.sprites.parent.Entity;
import parohyapp.mario.sprites.parent.InteractiveSpriteEntity;

/**
 * Created by tomas on 4/3/2016.
 */
public abstract class Switch extends InteractiveSpriteEntity {

    protected Animation toggleAnimation;
    private boolean toggle;
    private ArrayList<Switchable> listenerSwicth;
    private ArrayList<LightChangeListener> lightChangeListener;

    public Switch(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
        setCategoryFilter(Entity.SWITCH_BIT);
    }

    public abstract void initConnection(SwitchableType... type);

    public void toggleSwitch(){
        toggle = isToggle() ? false : true;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    @Override
    public void onColide() {
        if(listenerSwicth != null){
            for(Switchable tmpSwitch : listenerSwicth){
                tmpSwitch.toggle();
            }
        }

        if(lightChangeListener != null){
            for(LightChangeListener tmpSwitch : lightChangeListener){
                tmpSwitch.changeLightStatus(LightStatus.DOOR);
            }
        }
    }

    public void setListener(Switchable listener) {
        if(listenerSwicth == null){
            listenerSwicth = new ArrayList<Switchable>();
        }
        listenerSwicth .add(listener);
    }

    public void setLightChangeListener(LightChangeListener listener) {
        if(lightChangeListener == null){
            lightChangeListener = new ArrayList<LightChangeListener>();
        }
        lightChangeListener.add(listener);
    }
}
