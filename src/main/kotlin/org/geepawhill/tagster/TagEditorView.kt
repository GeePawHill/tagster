package org.geepawhill.tagster

import javafx.event.EventTarget
import javafx.scene.Parent
import tornadofx.*

class TagEditorView : Fragment() {
    override val root: Parent = vbox {
        label("Tag Editor here.")
    }
}

fun EventTarget.tageditorview(op: TagEditorView.() -> Unit = {}) {
    val view = TagEditorView()
    this.addChildIfPossible(view.root)
    view.op()
}
