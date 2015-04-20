package org.oc.ld32

import java.util

import org.lengine.maths.Quaternion
import org.lengine.render.{RenderEngine, Sprite}
import org.oc.ld32.gui.{GuiMainMenu, GuiIngame, GuiScreen}

class GuiLevelCleared(parent: GuiScreen) extends GuiScreen {

  val titleSprite = new Sprite("assets/textures/gui/levelCleared.png")

  var lastTimeAdded: Float = 0

  var trail: util.List[Sprite] = new util.ArrayList
  var unused: util.List[Sprite] = new util.ArrayList

  private val minColor: Int = 0x000000
  private val maxColor: Int = 0xF8F8F8

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
    copy.setPos(sprite.getPos().x+offsetX, sprite.getPos().y + offsetY)
    copy
  }

  def outside(sprite: Sprite): Boolean = {
    sprite.getPos().x+sprite.width < 0f || sprite.getPos().y+sprite.height < 0f || sprite.getPos().x > width || sprite.getPos().y > height
  }

  def drawTrail(original: Sprite, delta: Float) = {
    if(RenderEngine.time - lastTimeAdded >= 1f/60f) {
      lastTimeAdded = RenderEngine.time
      if (unused.isEmpty)
        trail.add(createTrailPart(original))
      else {
        val popped: Sprite = unused.remove(0)

        val offsetX = Math.cos(RenderEngine.time).toFloat
        val offsetY = Math.sin(RenderEngine.time).toFloat
        popped.setPos(original.getPos().x+offsetX, original.getPos().y + offsetY)
        trail.add(popped)
      }
    }
    for(i <- 0 until trail.size) {
      val trailPart = trail.get(i)
      val dir = (trailPart.getPos() - original.getPos()).norm()
      trailPart.setPos(trailPart.getPos().x+dir.x*delta*60f, trailPart.getPos().y+dir.y*delta*60f)
      if(outside(trailPart) || ~(trailPart.getPos() - original.getPos()) >= 25f) {
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

    var text = "Press any key or button to go to the next level"
    if(Game.level.nextID == null) {
      text = "Press any key or button to go back to the previous menu"
    }
    val textX = width/2f-Game.fontRenderer.getWidth(text)/2f
    val textY = height/2f-8f
    Game.fontRenderer.renderString(text, textX, textY)
  }

  def next(): Unit = {
    if(Game.level.nextID != null) {
      Game.loadLevel(Game.level.nextID, reloadMusic = true)
      Game.displayGuiScreen(parent)
    } else {
      parent match {
        case ingameMenu: GuiIngame => {
          if (ingameMenu.guiEditor != null) {
            Game.displayGuiScreen(ingameMenu.guiEditor)
          } else {
            Game.displayGuiScreen(new GuiMainMenu)
            Game.stopAllSounds()
            Game.playMusic("LD32 - Third Track - Abstraction")
          }
        }

        case _ => {
          println(s"$parent")
          Game.displayGuiScreen(parent)
        }
      }
    }
    Game.level = null
    Game.isPaused = false
  }

  override def onKeyPressed(keycode: Int, char: Char): Unit = {
    super.onKeyPressed(keycode, char)
    next()
  }

  override def onMousePressed(x: Int, y: Int, button: Int): Unit = {
    super.onMousePressed(x,y,button)
    next()
  }

  override def onButtonPressed(button: Int): Unit = {
    super.onButtonReleased(button)
    next()
  }
}
