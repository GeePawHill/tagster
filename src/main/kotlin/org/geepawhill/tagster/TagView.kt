package org.geepawhill.tagster

import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.input.ClipboardContent
import javafx.scene.input.DataFormat
import javafx.scene.input.MouseEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import tornadofx.*

class TagView(val model: Tag) : Fragment() {
    override val root: Parent = stackpane {
        alignment = Pos.CENTER
        padding = Insets(5.0, 10.0, 5.0, 15.0)
        background = Background(
            BackgroundFill(
                Color.BLACK,
                CornerRadii(18.0),
                Insets(2.0)
            ),
            BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii(20.0),
                Insets(3.0)
            )
        )
        onDragDetected = EventHandler<MouseEvent> { handleStartDrag(it) }
        hbox {
            stackpane {
                alignment = Pos.CENTER
                label(model.text)
            }
            stackpane {
                alignment = Pos.CENTER
                hyperlink("X")
            }
        }
    }

    fun handleStartDrag(event: MouseEvent) {
        println("Start drag")
        val dragboard = root.startDragAndDrop(TransferMode.COPY)
        val content = ClipboardContent()
        dragboard.put(TAG_FORMAT, model)
        dragboard.putString(model.text)
        println(dragboard.string)
        event.consume()
    }

    companion object {
        val TAG_FORMAT = DataFormat("tagster-tag")
    }
}
