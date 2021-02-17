package org.geepawhill.tagster

import javafx.event.EventTarget
import javafx.scene.Parent
import tornadofx.*

class TagEditorView(model: TagEditorModel) : Fragment() {
    override val root: Parent = tabpane {
        tab("All") {
            content = vbox {
                text("Editor box would go here.")
                flowpane {
                    model.tags.forEach {
                        paddingLeft = 10.0
                        this += TagView(it)
                    }
                }
            }
        }
    }
}

fun EventTarget.tagEditorView(model: TagEditorModel, op: TagEditorView.() -> Unit = {}) {
    val view = TagEditorView(model)
    this.addChildIfPossible(view.root)
    view.op()
}
