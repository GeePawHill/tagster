package org.geepawhill.tagster

import tornadofx.*


class TagEditorModel {
    val tags = observableListOf(
        Tag("tiny"),
        Tag("fairly long-ish"),
        Tag("some"),
        Tag("more added"),
        Tag("for"),
        Tag("visuals")
    )
}