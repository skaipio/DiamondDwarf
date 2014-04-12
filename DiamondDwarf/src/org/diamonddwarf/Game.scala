package org.diamonddwarf

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.GL20
import org.diamonddwarf.stage._
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color
import org.diamonddwarf.ui.StageRenderer
import org.diamonddwarf.ui.InventoryRenderer
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys
class Game extends ApplicationListener {

import org.diamonddwarf.ui.InventoryRenderer
import org.diamonddwarf.ui.InventoryRenderer

class Game extends ApplicationListener {
  private var batch : SpriteBatch = null
  private var stageRenderer: StageRenderer = null
  private var inventoryRenderer : InventoryRenderer = null

  private var texture: Texture = null
  private var sprite: Sprite = null

  private var player = new Player("Hessu")
  private var game = new DiamondDwarf(player)

  game.startStage(Stage.stage1)

  val controller = new Controller(game)

  override def create() {
    batch = new SpriteBatch
    stageRenderer = new StageRenderer(game, batch, ResourceLoader.spriteMap)
    stageRenderer.create
    stageRenderer.setNewRandomIds(Stage.stage1)
    
    inventoryRenderer = new InventoryRenderer(game, batch)
    inventoryRenderer.create
    

    //    val region = new TextureRegion(texture, 0, 0, 512, 275)
    //
    //    sprite = new Sprite(region);
    //    sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
    //    sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    //    sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

    Gdx.input.setInputProcessor(controller)
  }

  override def dispose() {
    this.batch.dispose()
    stageRenderer.dispose;
    inventoryRenderer.dispose
  }

  private def checkInput() {
    if (player.moving) return
    if (Gdx.input.isKeyPressed(Keys.A)) {
      game.player.direction = Coordinate.Left
      game.movePlayer(Coordinate.Left)
    } else if (Gdx.input.isKeyPressed(Keys.D)) {
      game.player.direction = Coordinate.Right
      game.movePlayer(Coordinate.Right)
    } else if (Gdx.input.isKeyPressed(Keys.W)) {
      game.player.direction = Coordinate.Up
      game.movePlayer(Coordinate.Up)
    } else if (Gdx.input.isKeyPressed(Keys.S)) {
      game.player.direction = Coordinate.Down
      game.movePlayer(Coordinate.Down)

    } else if (Gdx.input.isKeyPressed(Keys.SPACE)) {
      game.playerDig
    }
  }

  override def render() {
    checkInput
    player.update(Gdx.graphics.getDeltaTime())

    Gdx.gl.glClearColor(0,0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stageRenderer.render
    inventoryRenderer.render
  }

  override def resize(width: Int, height: Int) {
  }

  override def pause() {
  }

  override def resume() {
  }

}