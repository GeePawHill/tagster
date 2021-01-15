package org.geepawhill.tagster

import javafx.event.EventTarget
import javafx.scene.Parent
import tornadofx.Fragment
import tornadofx.addChildIfPossible
import tornadofx.label
import tornadofx.vbox

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
