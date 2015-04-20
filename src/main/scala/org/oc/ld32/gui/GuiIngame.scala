package org.oc.ld32.gui

import java.util.{HashMap, Map}
import org.lengine.render.{TextureRegion, Sprite, Texture}
import org.lwjgl.input.Keyboard
import org.oc.ld32.Game
import org.oc.ld32.gui.editor.GuiEditor
import org.oc.ld32.input.keyboard.KeyControls

class GuiIngame extends GuiScreen {

  val map: Map[Float, Sprite] = new HashMap
  val texture: Texture = "assets/textures/gui/baguette.png"
  var guiEditor: GuiEditor = _

  def this(guiEditor: GuiEditor) {
    this()
    this.guiEditor = guiEditor

  }

  override def init(): Unit = {
  }

  def getSprite(p: Float): Sprite = {
    if(map.containsKey(p)) {
      map.get(p)
    } else {
      val sprite: Sprite = new Sprite(texture, new TextureRegion(0,0,p,1))
      map.put(p, sprite)
      sprite
    }
  }

  override def renderScreen(delta: Float): Unit = {
    if(Game.player != null) {
      val baguette: Sprite = getSprite(Game.player.baguetteCompletion)
      baguette.setPos(0,Game.getBaseHeight-baguette.height)
      baguette.render(delta)


      if (Game.isPaused) {
        this.fontRenderer.renderString("Pause", 800, 300, 0xFFFFFFFF, 2f)
        this.fontRenderer.renderString("Resume (Enter)", 800, 270)
        if(guiEditor != null)
          this.fontRenderer.renderString("Return to Editor (Q)", 800, 245)
        else
          this.fontRenderer.renderString("Quit (Q)", 800, 245)
      }
      else if(Game.player.isDead())
      {
        this.fontRenderer.renderString("You are dead! Retry? [R]", 800, 600)
      }

    }
  }
  override def onKeyPressed(keyCode: Int, char: Char): Unit = {

    if(Game.player != null && Game.player.isDead() && keyCode == KeyControls.retry)
    {
      Game.loadLevel(Game.level.getName(), true)
    }
    else if(!Game.isPaused && keyCode == KeyControls.pause)
    {
      Game.pause()
    }
    else if(keyCode == KeyControls.pause || keyCode == KeyControls.confirm)
    {
      Game.resume()
      elements.clear()
    }
    else if(keyCode == Keyboard.KEY_Q)
    {
      Game.level = null
      Game.player = null
      Game.resume()
      if(guiEditor != null)
        Game.displayGuiScreen(this.guiEditor)
      else
        Game.displayGuiScreen(new GuiMainMenu)
    }

    super.onKeyPressed(keyCode, char)
  }
}
