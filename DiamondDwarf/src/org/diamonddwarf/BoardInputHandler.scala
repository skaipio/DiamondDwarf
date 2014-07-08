package org.diamonddwarf

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.Gdx

trait BoardInputHandler extends BoardController with InputProcessor {

  abstract override def update(delta: Float) {
    if (Gdx.input.isKeyPressed(Keys.W))
      this.movePlayer(Up)
    if (Gdx.input.isKeyPressed(Keys.A))
      this.movePlayer(Left)
    if (Gdx.input.isKeyPressed(Keys.S))
      this.movePlayer(Down)
    if (Gdx.input.isKeyPressed(Keys.D))
      this.movePlayer(Right)
    super.update(delta)
  }

  override def keyDown(keyCode: Int): Boolean = {

//    keyCode match {
//      case Keys.W     => this.movePlayer(Up)
//      case Keys.A     => this.movePlayer(Left)
//      case Keys.S     => this.movePlayer(Down)
//      case Keys.D     => this.movePlayer(Right)
//      case Keys.SPACE => //this.addActionToPlayer(actionFactory.digAtSelf)
//      case _          => return false
//    }
    true
  }

  /**
   * Called when a key was released
   *
   * @param keycode one of the constants in {@link Input.Keys}
   * @return whether the input was processed
   */
  override def keyUp(keycode: Int) = false

  /**
   * Called when a key was typed
   *
   * @param character The character
   * @return whether the input was processed
   */
  override def keyTyped(character: Char) = false

  /**
   * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Buttons#LEFT} on
   * Android and iOS.
   * @param screenX The x coordinate, origin is in the upper left corner
   * @param screenY The y coordinate, origin is in the upper left corner
   * @param pointer the pointer for the event.
   * @param button the button
   * @return whether the input was processed
   */
  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int) = false

  /**
   * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Buttons#LEFT} on Android
   * and iOS.
   * @param pointer the pointer for the event.
   * @param button the button
   * @return whether the input was processed
   */
  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int) = false

  /**
   * Called when a finger or the mouse was dragged.
   * @param pointer the pointer for the event.
   * @return whether the input was processed
   */
  override def touchDragged(screenX: Int, screenY: Int, pointer: Int) = false

  /**
   * Called when the mouse was moved without any buttons being pressed. Will not be called on either Android or iOS.
   * @return whether the input was processed
   */
  override def mouseMoved(screenX: Int, screenY: Int) = false

  /**
   * Called when the mouse wheel was scrolled. Will not be called on either Android or iOS.
   * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
   * @return whether the input was processed.
   */
  override def scrolled(amount: Int) = false
}