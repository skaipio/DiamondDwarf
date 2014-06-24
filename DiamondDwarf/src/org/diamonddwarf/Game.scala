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
import org.diamonddwarf.factories.TileFactory
import org.diamonddwarf.resources.ResourceLoader
import org.diamonddwarf.menu.StageMenu
import org.diamonddwarf.ui.MenuRenderer
import org.diamonddwarf.menu.StageMenuGrid
import org.diamonddwarf.ui.MenuInputProcessor
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.audio.Music
import org.diamonddwarf.stage.tileobjects.Player

class Game extends ApplicationListener {
  private var batch: SpriteBatch = null
  private var resourceLoader: ResourceLoader = null

  private var stageRenderer: StageRenderer = null
  private var interfaceRenderer: InventoryRenderer = null
  private var menuRenderer: MenuRenderer = null

  private var texture: Texture = null
  private var sprite: Sprite = null

  private var player: Player = null

  private var currentScreen = Screen.menu

  private var stageController: Controller = null

  private var stageMenu: StageMenu = null
  private var stageMenuGrid: StageMenuGrid = null
  
  private var track: Music = null

  override def create() {
    batch = new SpriteBatch
    resourceLoader = new ResourceLoader

    val sounds = new Sounds(resourceLoader)
    val animFactory = new AnimationFactory(resourceLoader.defaultTexture, resourceLoader.animationTemplateMap, resourceLoader.numberMap)
    val effectFactory = new EffectFactory(animFactory)
    val tileFactory = new TileFactory(resourceLoader)

    stageMenu = new StageMenu(new StageFactory(resourceLoader.stageTemplates, tileFactory))

    val actorFactory = new ActorFactory(resourceLoader, effectFactory, animFactory, sounds)

    this.player = actorFactory.createPlayer

    DiamondDwarf.player = this.player
    stageController = new Controller()
    stageRenderer = new StageRenderer(batch, resourceLoader.seamMap)
    stageRenderer.create

    interfaceRenderer = new InventoryRenderer(batch, resourceLoader)
    interfaceRenderer.create

    stageMenuGrid = new StageMenuGrid(4, 5, stageMenu)
    menuRenderer = new MenuRenderer(batch, resourceLoader, stageMenu, stageMenuGrid)
    val menuController = new MenuInputProcessor(stageMenuGrid)

    Gdx.input.setInputProcessor(menuController)

    if (resourceLoader.hasTrack(0)) {}
    track = resourceLoader.getTrack(0)

    DiamondDwarf.startStage(this.stageMenu.createStage(0))

  }

  override def dispose() {
    this.batch.dispose()
    stageRenderer.dispose;
    interfaceRenderer.dispose
    resourceLoader.dispose
  }

  private def move(c: Coordinate) {
    DiamondDwarf.player.direction = c
    DiamondDwarf.moveOrBreakStone(c)
  }

  private def checkStageInput() {
    var shift = false
    if (player.activeState != player.states.idle) return
    if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
      shift = true
    }
    if (Gdx.input.isKeyPressed(Keys.A)) {
      DiamondDwarf.player.facing = Coordinate.Left
      if (!shift) move(Coordinate.Left)

    } else if (Gdx.input.isKeyPressed(Keys.D)) {
      DiamondDwarf.player.facing = Coordinate.Right
      if (!shift) move(Coordinate.Right)

    } else if (Gdx.input.isKeyPressed(Keys.W)) {
      if (!shift) move(Coordinate.Up)

    } else if (Gdx.input.isKeyPressed(Keys.S)) {
      if (!shift) move(Coordinate.Down)

    } else if (Gdx.input.isKeyPressed(Keys.SPACE)) {
      DiamondDwarf.playerDig

    } else if (Gdx.input.isKeyPressed(Keys.F)) {
      DiamondDwarf.detectGems
    } else if (Gdx.input.isKeyPressed(Keys.E)) {
      DiamondDwarf.build
    } else if (Gdx.input.isKeyPressed(Keys.Q)) {
      DiamondDwarf.use

    }
  }

  private def checkMenuInput() {
    if (Gdx.input.isKeyPressed(Keys.SPACE)) {
      this.currentScreen = Screen.stage
      Gdx.input.setInputProcessor(stageController)
      DiamondDwarf.startStage(this.stageMenu.createStage(stageMenuGrid.selectedStage))
      track.setLooping(true)
      track.play()
    }
  }

  def updateActors(delta: Float) {
    DiamondDwarf.actors.foreach(_.update(delta))
  }

  private def renderStageScreen {
    DiamondDwarf.activeMap.currentTime += Gdx.graphics.getDeltaTime()
    this.updateActors(Gdx.graphics.getDeltaTime())
    checkStageInput
    stageRenderer.render
    interfaceRenderer.render
  }

  private def renderMenuScreen {
    batch.setColor(Color.GRAY)
    stageRenderer.render
    batch.setColor(Color.WHITE)
    this.menuRenderer.render
    checkMenuInput

  }

  override def render() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    if (currentScreen == Screen.stage) {
      renderStageScreen
    } else if (currentScreen == Screen.menu) {
      renderMenuScreen
    }
  }

  private def startStage {
    currentScreen = Screen.stage
  }

  override def resize(width: Int, height: Int) {
  }

  override def pause() {
  }

  override def resume() {
  }

}