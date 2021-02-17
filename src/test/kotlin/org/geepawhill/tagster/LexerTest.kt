package org.geepawhill.tagster

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LexerTest {

    class LexerTest {

        private val lexer = Lexer()

        @Test
        fun `empty gives EOL`() {
            lexer.start("")
            assertThat(lexer.peek()).isEqualTo(Token(TokenType.EOL))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))
        }

        @Test
        fun `read past EOL gives UNKNOWN`() {
            lexer.start("")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.UNKNOWN))
        }

        @Test
        fun `whitespace skips`() {
            lexer.start(" \t\n")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.UNKNOWN))
        }

        @Test
        fun `whitespace skips before word`() {
            lexer.start(" nonsense")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "nonsense"))
        }

        @Test
        fun `does not recognize nonsense`() {
            lexer.start("nonsense")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "nonsense"))
        }

        @Test
        fun `stops at whitespace`() {
            lexer.start("nonsense ")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "nonsense"))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))
        }

        @Test
        fun `recognizes ANDs`() {
            lexer.start("AND")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.AND))
            lexer.start("anD")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.AND))
            lexer.start("&")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.AND))
        }

        @Test
        fun `recognizes ORs`() {
            lexer.start("OR")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.OR))
            lexer.start("Or")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.OR))
            lexer.start("|")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.OR))
        }

        @Test
        fun `recognizes NOTs`() {
            lexer.start("NOT")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.NOT))
            lexer.start("noT")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.NOT))
            lexer.start("!")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.NOT))
        }

        @Test
        fun `recognizes parenthesis`() {
            lexer.start("(")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.LEFT_PAREN))
            lexer.start("((")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.LEFT_PAREN))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.LEFT_PAREN))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))


            lexer.start(")")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.RIGHT_PAREN))
            lexer.start("))")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.RIGHT_PAREN))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.RIGHT_PAREN))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))
        }

        @Test
        fun `words returned in lower case`() {
            lexer.start("HEY THERE")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "hey"))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "there"))
        }

        @Test
        fun `recognizes word`() {
            lexer.start("It is   not today")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "it"))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "is"))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.NOT))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "today"))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))
        }

        @Test
        fun `recognizes word - with special Characters`() {
            lexer.start("one_word")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "one_word"))
            lexer.start("one.word")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "one.word"))
            lexer.start("one-word")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "one-word"))
            lexer.start("one-word.along_with.this")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "one-word.along_with.this"))
        }
    }
}