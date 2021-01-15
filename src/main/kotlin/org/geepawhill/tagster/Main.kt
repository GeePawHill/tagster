package org.geepawhill.tagster

import javafx.geometry.Orientation
import tornadofx.*

class Main : App(MainView::class) {
}

class MainView : View() {
    override val root = borderpane {
        top = toolbar {
            button("Button")
        }
        center = splitpane {
            label("Tree goes here")
            splitpane(Orientation.VERTICAL) {
                tageditorview { }
                label("preview goes here")
            }
        }
    }
}



	
