package com.mygdx.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.hellinspace.GameMain;

public class HomingBullet extends Bullet {
    Vector2 targetPos, targetVel;
    private float maxV;

    public HomingBullet(GameMain game, float x, float y, float vx, float vy, float maxVx, float maxVy, boolean isGood, float bearing, float damageValue, Vector2 targetPos, Vector2 targetVel) {
        super(game, x, y, vx, vy, maxVx, maxVy, isGood, bearing, damageValue);

        //set position and velocity of the target
        this.targetPos = targetPos;
        this.targetVel = targetVel;
        this.maxV = maxV;

        //load custom laser sprite -- might need to scale this
        bulletTexture = new Texture(Gdx.files.internal("Projectiles\\redlaser.png"));
    }

    /**
     *
     * https://code.tutsplus.com/tutorials/hit-the-target-with-a-deadly-homing-missile--active-8933
     * @param delta time delta
     */
    @Override
    public void move(float delta) {
        if (targetPos == null) { //no targetPos, use default movement
            super.move(delta);
            return;
        }

        rotate();

        //update vx and vy
        initalV.x = maxV * (90 - Math.abs(bearing)) /90; //DEGREES
        initalV.y = (bearing < 0 ? -maxV + Math.abs(initalV.x) : maxV - Math.abs(initalV.x));

        pos.x += initalV.x; //could add timedelta and a constant here.
        pos.y += initalV.y;
    }

    /**
     * rotates the bullet to face the targetPos
     */
    private void rotate() {
        double targetX = targetPos.x - pos.x;
        double targetY = targetPos.y - pos.y;
        bearing += Math.atan2(targetY, targetX) * 180 / Math.PI;
    }

    public Vector2 getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(Vector2 newTarget) {
        targetPos = newTarget;
    }
}