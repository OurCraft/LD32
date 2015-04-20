package org.oc.ld32.entity

import org.lengine.render.{RenderEngine, Texture, TextureAtlas}
import org.oc.ld32.level.BaguetteLevel
import org.oc.ld32.render.Animation

abstract class EntityBiped(id: String, range: Float = 16.0F) extends BaguetteEntity {

  var anim = new Animation(new TextureAtlas(new Texture(s"assets/textures/entities/$id.png"), 64,64))
  val legAnim = new Animation(new TextureAtlas(new Texture(s"assets/textures/entities/$id"+"_legs.png"), 64,64))
  val attackRange: Float = range
  var lastAttack = 0f

  private var dead = false

  anim.speed = 5f
  legAnim.speed = 5f

  override def init: Unit = {
    super.init
    boundingBox.width = 64f
    boundingBox.height = 64f
  }

  override def render(delta: Float): Unit = {
    anim.transform.pos.set(getPos)
    anim.transform.angle = getAngle+(Math.PI/2f).toFloat

    legAnim.transform.pos.set(getPos)
    legAnim.transform.angle = (Math.atan2(movingDir.y, movingDir.x)+Math.PI/2f).toFloat

    legAnim.render(delta)
    anim.render(delta)
  }

  override def update(delta: Float): Unit = {
    if(moving) {
      anim.update(delta)
      legAnim.update(delta)
    }

    super.update(delta)
  }

  def walkLeft(delta: Float, speedMultiplier: Float = 2f): Boolean = {
    walkRight(delta, -speedMultiplier)
  }

  def walkRight(delta: Float, speedMultiplier: Float = 2f): Boolean = {
    val speed: Float = delta * 60f * 2f * speedMultiplier
    val lvl: BaguetteLevel = level.asInstanceOf[BaguetteLevel]
    if(lvl.canGoTo(boundingBox, getPos.x+speed, getPos.y)) {
      getPos.x += speed
      return true
    }
    false
  }

  def walkUp(delta: Float, speedMultiplier: Float = 2f): Boolean = {
    val speed: Float = delta * 60f * 2f * speedMultiplier
    val lvl: BaguetteLevel = level.asInstanceOf[BaguetteLevel]
    if(lvl.canGoTo(boundingBox, getPos.x, getPos.y+speed)) {
      getPos.y += speed
      return true
    }
    false
  }

  def walkDown(delta: Float, speedMultiplier: Float = 2f): Boolean = {
    walkUp(delta, -speedMultiplier)
  }

  def isDead(): Boolean = dead;

  def die(): Unit = {
    dead = true
    this.level.despawn(this)
  }


  def getAttackRange(): Float = attackRange


  def attack(): Unit = {

    if(RenderEngine.time - lastAttack >= 1f) {
      lastAttack = RenderEngine.time
      val x = getPos.x + boundingBox.width/2f - (Math.cos(getAngle)).toFloat*getAttackRange() - getAttackRange()/2f
      val y = getPos.y + boundingBox.height/2f - (Math.sin(getAngle)).toFloat*getAttackRange() - getAttackRange()/2f

      val vx = ~(movingDir) * 10f * (-Math.cos(getAngle)).toFloat
      val vy = ~(movingDir) * 10f * (-Math.sin(getAngle)).toFloat
      val attackEntity = new EntityAttack(this, x+vx, y+vy, getAttackRange(), getAttackRange())
      level spawn attackEntity
    }

/*    var x = -1f
    var y = -1f
    if(this.lastX > target.lastX)
      x = Math.abs(this.lastX - target.lastX)
    else
      x = Math.abs(target.lastX - this.lastX)

    if(this.lastY > target.lastY)
      y = Math.abs(this.lastY - target.lastY)
    else
      y = Math.abs(target.lastY - this.lastY)

    if(x == -1 || y == -1) return

    if(x < getAttackRange() && y < getAttackRange())
      target.die()*/

  }
}
