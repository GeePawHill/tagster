package org.geepawhill.tagster

import javafx.event.EventHandler
import javafx.geometry.Orientation
import javafx.scene.input.DragEvent
import javafx.scene.input.TransferMode
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*

class Main : App(MainView::class) {
}

class MainView : View() {
    val tagEditor = TagEditorModel()
    lateinit var target: Rectangle

    override val root = borderpane {
        top = toolbar {
            button("Button")
        }
        center = splitpane {
            rectangle(0, 0, 100, 100) {
                target = this
                fill = Color.BLACK
                onDragOver = EventHandler<DragEvent> { handleDragOver(it) }
                onDragDropped = EventHandler<DragEvent> { handleDragDrop(it) }
                onDragEntered = EventHandler<DragEvent> { handleDragEntered(it) }
                onDragExited = EventHandler<DragEvent> { handleDragExited(it) }
            }
            label("Tree goes here") {
            }
            splitpane(Orientation.VERTICAL) {
                tagEditorView(tagEditor) { }
                label("preview goes here")
            }
        }
    }

    fun handleDragOver(event: DragEvent) {
        println("Drag over.")
        if (event.dragboard.hasString()) {
            event.acceptTransferModes(TransferMode.COPY)
        }
        event.consume()
    }

    fun handleDragDrop(event: DragEvent) {
        println("Drag dropped.")
        if (event.dragboard.hasString() || event.dragboard.hasContent(TagView.TAG_FORMAT)) {
            println("${event.dragboard.string}")
            println("${event.dragboard.getContent(TagView.TAG_FORMAT)}")
            event.isDropCompleted = true
        }
        event.consume()
    }

    fun handleDragEntered(event: DragEvent) {
        println("Entered")
        event.consume()
    }

    fun handleDragExited(event: DragEvent) {
        println("Exited")
        event.consume()
    }
}



	
