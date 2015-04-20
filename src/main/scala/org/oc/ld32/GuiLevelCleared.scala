package org.oc.ld32

import java.util

import org.lengine.maths.Quaternion
import org.lengine.render.{RenderEngine, Sprite}
import org.oc.ld32.gui.GuiScreen

class GuiLevelCleared(parent: GuiScreen) extends GuiScreen {

  val titleSprite = new Sprite("assets/textures/gui/levelCleared.png")

  var lastTimeAdded: Float = 0

  var trail: util.List[Sprite] = new util.ArrayList
  var unused: util.List[Sprite] = new util.ArrayList

  private val minColor: Int = 0x7C7C7C
  private val maxColor: Int = 0x0F0F0F

  override def init(): Unit = {
    trail.clear()
    unused.clear()
  }

  def createTrailPart(sprite: Sprite): Sprite = {
    val color: Int = (Math.random() * (maxColor - minColor) + minColor).toInt
    val r = (color >> 16 & 0xFF) / 255f
    val g = (color >> 8 & 0xFF) / 255f
    val b = (color & 0xFF) / 255f
    val colorObject = new Quaternion(r,g,b,1f)
    val copy = new Sprite(sprite.texture, sprite.region, colorObject)
    val offsetX = Math.cos(RenderEngine.time).toFloat
    val offsetY = Math.sin(RenderEngine.time).toFloat
    copy.setPos(sprite.getPos.x+offsetX, sprite.getPos.y + offsetY)
    copy
  }

  def outside(sprite: Sprite): Boolean = {
    sprite.getPos().x+sprite.width < 0f || sprite.getPos().y+sprite.height < 0f || sprite.getPos().x > width || sprite.getPos().y > height
  }

  def drawTrail(original: Sprite, delta: Float) = {
    if(RenderEngine.time - lastTimeAdded >= 1f/30f) {
      lastTimeAdded = RenderEngine.time
      if (unused.isEmpty)
        trail.add(createTrailPart(original))
      else {
        val popped: Sprite = unused.remove(0)

        val offsetX = Math.cos(RenderEngine.time).toFloat
        val offsetY = Math.sin(RenderEngine.time).toFloat
        popped.setPos(original.getPos.x+offsetX, original.getPos.y + offsetY)
        trail.add(popped)
      }
    }
    for(i <- 0 until trail.size) {
      val trailPart = trail.get(i)
      val dir = (trailPart.getPos() - original.getPos()).norm()
      trailPart.setPos(trailPart.getPos.x+dir.x*delta*60f, trailPart.getPos.y+dir.y*delta*60f)
      if(outside(trailPart)) {
        if(trailPart.texture == original.texture)
          unused.add(trailPart)
      } else {
        trailPart.render(delta)
      }
    }
    trail.removeAll(unused)
  }

  override def renderScreen(delta: Float): Unit = {
    titleSprite.setPos(width/2f-titleSprite.width/2f, height-titleSprite.height-75f)

    drawTrail(titleSprite,delta)

    titleSprite.render(delta)
  }
}
