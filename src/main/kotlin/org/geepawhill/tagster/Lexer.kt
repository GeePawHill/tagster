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

    fun start(text: String) {
        next = 0
        source = text
        cache = null
    }

    fun peek(): Token = popToCache()
    fun pop(): Token = cache ?: next()

    private fun popToCache(): Token {
        cache = pop()
        return cache!!
    }

    private fun next(): Token {
        if (afterEnd()) return Token(TokenType.UNKNOWN)
        if (atEnd()) return eolAndAdvance()
        return tokenFromBlock(nextBlock())
    }

    private fun eolAndAdvance(): Token {
        next += 1
        return Token(TokenType.EOL)
    }

    private fun tokenFromBlock(text: String): Token {
        if (text[0].isWhitespace()) return next()
        return when (text) {
            "and", "&" -> Token(TokenType.AND)
            "or", "|" -> Token(TokenType.OR)
            "not", "!" -> Token(TokenType.NOT)
            "(" -> Token(TokenType.LEFT_PAREN)
            ")" -> Token(TokenType.RIGHT_PAREN)
            else -> {
                if (text.isNotEmpty()) Token(TokenType.WORD, text)
                else Token(TokenType.UNKNOWN)
            }
        }
    }

    private fun atEnd() = next == source.length

    private fun afterEnd() = next > source.length

    private fun nextBlock(): String {
        var result = source[next++].toString()
        if (isBlockBreaker(result.first())) return result
        while (next < source.length && sameType(result.first(), source[next])) result += source[next++]
        return result.toLowerCase()
    }

    private fun isBlockBreaker(input: Char) = "()&|!".contains(input)

    private fun sameType(first: Char, second: Char): Boolean = when {
        isId(first) -> isId(second)
        first.isWhitespace() -> second.isWhitespace()
        else -> throw FilterParseError("Illegal character in filter string.")
    }

    private fun isId(c: Char): Boolean = c.isLetterOrDigit() || LEGAL_ID_CHARACTERS.contains(c)

    companion object {
        val LEGAL_ID_CHARACTERS = ".-_"
    }
}