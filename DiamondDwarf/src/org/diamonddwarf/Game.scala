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
import org.diamonddwarf.factories.AnimationFactory
import org.diamonddwarf.resources.ResourceLoader
import org.diamonddwarf.factories.StageFactory
import org.diamonddwarf.resources.Sounds
import org.diamonddwarf.factories.ActorFactory
import org.diamonddwarf.factories.EffectFactory

class Game extends ApplicationListener {
  private var batch: SpriteBatch = null
  private var stageRenderer: StageRenderer = null
  private var inventoryRenderer: InventoryRenderer = null
  private var resourceLoader: ResourceLoader = null
  private var animFactory: AnimationFactory = null

  private var texture: Texture = null
  private var sprite: Sprite = null

  private var player: Player = null
  private var game: DiamondDwarf = null

  override def create() {
    batch = new SpriteBatch
    resourceLoader = new ResourceLoader

    val sounds = new Sounds(this.resourceLoader)
    animFactory = new AnimationFactory(resourceLoader)
    val effectFactory = new EffectFactory(animFactory)

    val stageFactory = new StageFactory(resourceLoader)
    val stage = stageFactory.createStage(0)

    val actorFactory = new ActorFactory(this.resourceLoader, effectFactory, this.animFactory, sounds)

    this.player = actorFactory.createPlayer

    game = new DiamondDwarf(this.player)
    val controller = new Controller(game)
    stageRenderer = new StageRenderer(game, batch, this.resourceLoader.textureRegionMapForVariants, this.resourceLoader.seamMap)
    stageRenderer.create
    stageRenderer.setNewRandomIds(stage)

    inventoryRenderer = new InventoryRenderer(game, batch)
    inventoryRenderer.create

    Gdx.input.setInputProcessor(controller)

    if (resourceLoader.hasTrack(0)) {}
    val track = resourceLoader.getTrack(0)
    track.setLooping(true)
    track.play()

    game.startStage(stage)
  }

  override def dispose() {
    this.batch.dispose()
    stageRenderer.dispose;
    inventoryRenderer.dispose
    resourceLoader.dispose
  }

  private def move(c: Coordinate) {
    game.player.direction = c
    game.movePlayer(c)
  }

  private def checkInput() {
    var shift = false
    if (player.activeState != player.states.idle) return
    if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
      shift = true
    }
    if (Gdx.input.isKeyPressed(Keys.A)) {
      game.player.facing = Coordinate.Left
      if (!shift) move(Coordinate.Left)

    } else if (Gdx.input.isKeyPressed(Keys.D)) {
      game.player.facing = Coordinate.Right
      if (!shift) move(Coordinate.Right)

    } else if (Gdx.input.isKeyPressed(Keys.W)) {
      if (!shift) move(Coordinate.Up)

    } else if (Gdx.input.isKeyPressed(Keys.S)) {
      if (!shift) move(Coordinate.Down)

    } else if (Gdx.input.isKeyPressed(Keys.SPACE)) {
      game.playerDig

    } else if (Gdx.input.isKeyPressed(Keys.F)) {
      game.detectGems
    } else if (Gdx.input.isKeyPressed(Keys.E)) {
      game.build
    } else if (Gdx.input.isKeyPressed(Keys.Q)) {
      game.use

    }
  }

  def updateActors(delta: Float) {
    game.actors.foreach(_.update(delta))
  }

  override def render() {
    game.activeMap.currentTime += Gdx.graphics.getDeltaTime()
    this.updateActors(Gdx.graphics.getDeltaTime())
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