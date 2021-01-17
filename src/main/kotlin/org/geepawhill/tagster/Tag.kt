package org.geepawhill.tagster

import java.io.Serializable

class Tag(val text: String) : Serializable {
    override fun toString(): String {
        return text
    }
}