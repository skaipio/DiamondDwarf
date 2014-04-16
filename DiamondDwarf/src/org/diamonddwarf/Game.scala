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
import org.diamonddwarf.ui.AnimationFactory
import org.diamonddwarf.resources.ResourceLoader
import org.diamonddwarf.factories.StageFactory
import org.diamonddwarf.resources.Sounds

class Game extends ApplicationListener {
  private var batch: SpriteBatch = null
  private var stageRenderer: StageRenderer = null
  private var inventoryRenderer: InventoryRenderer = null
  private var resourceLoader: ResourceLoader = null
  private var animFactory: AnimationFactory = null

  private var texture: Texture = null
  private var sprite: Sprite = null

  private var player = new Player("Hessu")
  private var game = new DiamondDwarf(player)

  val controller = new Controller(game)

  override def create() {
    batch = new SpriteBatch
    resourceLoader = new ResourceLoader
    resourceLoader.associateActorWithRegion(game.player, "tileobj/dwarf")

    val stageFactory = new StageFactory(resourceLoader)
    val stage = stageFactory.createStage(0)

    game.startStage(stage)

    stageRenderer = new StageRenderer(game, batch, resourceLoader)
    stageRenderer.create
    stageRenderer.setNewRandomIds(stage)

    inventoryRenderer = new InventoryRenderer(game, batch)
    inventoryRenderer.create

    animFactory = new AnimationFactory(resourceLoader)

    val sounds = new Sounds(this.resourceLoader)

    player.defaultTextureRegion = animFactory.dwarfIdle

    player.associateStateWithAnim(player.states.moving, animFactory.createDwarfMoveAnim)
    player.associateStateWithAnim(player.states.digging, animFactory.createDwarfDigAnim)
    // player.associateStateWithSound(player.states.detectingGems, sounds.dwarfNope)
    player.associateStateWithSound(player.states.noGemsFound, sounds.dwarfNope)
    player.associateStateWithSound(player.states.foundGems, sounds.foundGems)
    player.associateStateWithSound(player.states.digging, sounds.digging)

    Gdx.input.setInputProcessor(controller)
    if (resourceLoader.hasTrack(0)) {}
    val track = resourceLoader.getTrack(0)
    track.setLooping(true)
    track.play()
  }

  override def dispose() {
    this.batch.dispose()
    stageRenderer.dispose;
    inventoryRenderer.dispose
    resourceLoader.dispose
  }

  private def checkInput() {
    if (player.activeState != player.states.idle) return
    if (Gdx.input.isKeyPressed(Keys.A)) {
      game.player.direction = Coordinate.Left
      game.player.facing = Coordinate.Left
      game.movePlayer(Coordinate.Left)
    } else if (Gdx.input.isKeyPressed(Keys.D)) {
      game.player.direction = Coordinate.Right
      game.player.facing = Coordinate.Right
      game.movePlayer(Coordinate.Right)
    } else if (Gdx.input.isKeyPressed(Keys.W)) {
      game.player.direction = Coordinate.Up
      game.movePlayer(Coordinate.Up)
    } else if (Gdx.input.isKeyPressed(Keys.S)) {
      game.player.direction = Coordinate.Down
      game.movePlayer(Coordinate.Down)

    } else if (Gdx.input.isKeyPressed(Keys.SPACE)) {
      game.playerDig

    } else if (Gdx.input.isKeyPressed(Keys.F)) {
      println(game.detectGems)
    }
  }

  override def render() {

    player.update(Gdx.graphics.getDeltaTime())
    checkInput

    Gdx.gl.glClearColor(0, 0, 0, 1);
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