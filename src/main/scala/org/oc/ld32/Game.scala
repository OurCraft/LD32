package org.oc.ld32

import org.lengine.GameBase
import org.lengine.maths.Vec2f
import org.lengine.render.{FontRenderer, RenderEngine, Shader, TextureAtlas}
import org.lwjgl.input.{Controller, Mouse}
import org.oc.ld32.entity.EntityPlayer
import org.oc.ld32.gui.{GuiMainMenu, GuiScreen}
import org.oc.ld32.input.gamepad.Controls
import org.oc.ld32.input.keyboard.KeyControls
import org.oc.ld32.level.{BaguetteLevel, LevelLoader}

import scala.collection.JavaConversions._

object Game extends GameBase("Baguettes") {

  var level: BaguetteLevel = _
  var fontRenderer: FontRenderer = _
  var player: EntityPlayer = _
  var currentGui: GuiScreen = _
  var usingGamepad = false

  override def getBaseHeight: Int = 640

  override def update(delta: Float): Unit = {
    if (level != null) {
      level.update(delta)

      if (!usingGamepad) {
        if (isKeyPressed(KeyControls.left)) {
          player.walkLeft(delta)
        }
        if (isKeyPressed(KeyControls.right)) {
          player.walkRight(delta)
        }
        if (isKeyPressed(KeyControls.up)) {
          player.walkUp(delta)
        }
        if (isKeyPressed(KeyControls.down)) {
          player.walkDown(delta)
        }
        val mousePos = player.getPos - new Vec2f(Mouse.getX, Mouse.getY)
        player.setAngle(Math.atan2(mousePos.y, mousePos.x).toFloat)
      } else {
        val threshold = 0.10
        val xMove = getAxisValue(Controls.moveX)
        if(Math.abs(xMove) >= threshold)
          player.walkRight(delta, xMove*2f)

        val yMove = -getAxisValue(Controls.moveY)
        if(Math.abs(yMove) >= threshold)
          player.walkUp(delta, yMove*2f)

        val lookX = getAxisValue(Controls.lookX)
        val lookY = getAxisValue(Controls.lookY)
        val angle = Math.atan2(lookY, -lookX)
        if(Math.abs(lookX) >= threshold || Math.abs(lookY) >= threshold)
          player.setAngle(angle.toFloat)
      }
    }
  }

  override def initGame: Unit = {
    KeyControls.init()
    postProcessShader = new Shader("assets/shaders/base.vsh", "assets/shaders/postProcess.fsh")

    fontRenderer = new FontRenderer(new TextureAtlas("assets/textures/font.png", 16, 16))

    if (currentGui != null) currentGui.init()
    displayGuiScreen(new GuiMainMenu)
  }

  def loadLevel(id: String, reloadMusic: Boolean = false): Unit = {
    level = LevelLoader.load(id)
    player = new EntityPlayer
    level spawn player

    player setPos level.spawnpoint

    if(reloadMusic && level.music != null) {
    //  soundManager.play("musics/"+level.music+".ogg")
    }
  }

  override def onKeyReleased(keyCode: Int, char: Char): Unit = {
    if (currentGui != null) {
      currentGui.onKeyReleased(keyCode, char)
    }
    usingGamepad = false
  }

  override def onMouseMoved(x: Int, y: Int, dx: Int, dy: Int): Unit = {
    if (currentGui != null) {
      currentGui.onMouseMove(x,y,dx,dy)
    }
    usingGamepad = false
  }

  override def onKeyPressed(keyCode: Int, char: Char): Unit = {
    if (currentGui != null) {
      currentGui.onKeyPressed(keyCode, char)
      for (element <- currentGui.elements) {
        element.onKeyPressed(keyCode, char)
      }
    }
    usingGamepad = false
  }

  override def render(delta: Float): Unit = {
    RenderEngine.clearColorBuffer(1f/15f,1f/40f,1f/20f,1)
    if(level != null ) {
      level.render(delta)
      val x: Float = player.getPos.x
      val y: Float = player.getPos.y
      fontRenderer.renderString(s"pos: $x, $y", 0, getBaseHeight-17, 0xFFFFFFF, 1)
    }

    if (currentGui != null) currentGui.render(delta)
  }

  override def onMousePressed(x: Int, y: Int, button: Int): Unit = {
    if (currentGui != null) {
      currentGui.onMousePressed(x,y,button)
    }
  }

  override def onMouseReleased(x: Int, y: Int, button: Int): Unit = {
    if (currentGui != null) {
      currentGui.onMouseReleased(x, y, button)
    }
  }

  override def onScroll(x: Int, y: Int, dir: Int): Unit = {
    if (currentGui != null) {
      for (element <- currentGui.elements) {
        element.onScroll(x, y, dir)
      }
    }
  }

  def displayGuiScreen(gui: GuiScreen): Unit = {
    currentGui = gui
    currentGui.init()
  }

  override def onAxisMoved(value: Float, index: Int, source: Controller): Unit = {
    // TODO: Use for mappings
  //  println(s"$value $index")
    if (currentGui != null) {
      currentGui.onAxisMoved(value, index)
    }
    usingGamepad = true
  }

  override def onButtonPressed(index: Int, source: Controller): Unit = {
    println(s"button pressed $index")
    if (currentGui != null) {
      currentGui.onButtonPressed(index)
    }
    usingGamepad = true
  }

  override def onButtonReleased(index: Int, source: Controller): Unit = {
    println(s"button released $index")
    if (currentGui != null) {
      currentGui.onButtonReleased(index)
    }
    usingGamepad = true
  }

  override def onPovYMoved(value: Float, index: Int, source: Controller): Unit = {
    println(s"pov y $value")
    usingGamepad = true
  }

  override def onPovXMoved(value: Float, index: Int, source: Controller): Unit = {
    println(s"pov x $value")
    usingGamepad = true
  }
}
