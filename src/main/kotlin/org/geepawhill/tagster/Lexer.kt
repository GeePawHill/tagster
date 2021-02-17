package org.geepawhill.tagster

enum class TokenType {
    EOL,
    AND,
    OR,
    NOT,
    WORD,
    LEFT_PAREN,
    RIGHT_PAREN,
    UNKNOWN
}

class FilterParseError(message: String) : RuntimeException(message)

data class Token(val type: TokenType, val text: String = "")

class Lexer {
    private var source = ""
    private var next = 0
    private var cache: Token? = null

    fun parsing(text: String) {
        next = 0
        source = text
        cache = null
    }

    fun peek(): Token {
        if (cache == null) cache = pop()
        return cache!!
    }

    fun pop(): Token {
        if (cache == null) cache = next()
        val result = cache!!
        cache = null
        return result
    }

    private fun next(): Token {
        if (next > source.length) return Token(TokenType.UNKNOWN)
        if (next == source.length) {
            next += 1
            return Token(TokenType.EOL)
        }
        val text = nextSameness()
        if (text[0].isWhitespace()) return next()
        if (text == "and" || text == "&") return Token(TokenType.AND)
        if (text == "or" || text == "|") return Token(TokenType.OR)
        if (text == "not" || text == "!") return Token(TokenType.NOT)
        if (text == "=y") return Token(TokenType.WORD, text)
        if (text == "(") return Token(TokenType.LEFT_PAREN)
        if (text == ")") return Token(TokenType.RIGHT_PAREN)
        if (text.isNotEmpty()) return Token(TokenType.WORD, text)
        return Token(TokenType.UNKNOWN, text)
    }

    private fun nextSameness(): String {
        val first = source[next]
        if ("()&!!".contains(first)) {
            next += 1
            return "" + first
        }
        var result = ""
        while (next < source.length && sameType(first, source[next])) {
            result += source[next++]
        }
        return result.toLowerCase()
    }

    private fun sameType(first: Char, second: Char): Boolean = when {
        isId(first) -> isId(second)
        first.isWhitespace() -> second.isWhitespace()
        "&|!".contains(first) -> "&|!".contains(second)
        else -> throw FilterParseError("Illegal character in filter string.")
    }

    private fun isId(c: Char): Boolean {
        val specialWordCharacters = ".-_"
        val relativeDateOperators = "=+>"
        return c.isLetterOrDigit() || (specialWordCharacters + relativeDateOperators).contains(c)
    }
}