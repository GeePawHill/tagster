package org.geepawhill.tagster

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LexerTest {

    class LexerTest {

        private val lexer = Lexer()

        @Test
        fun `empty gives EOL`() {
            lexer.parsing("")
            assertThat(lexer.peek()).isEqualTo(Token(TokenType.EOL))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))
        }

        @Test
        fun `read past EOL gives UNKNOWN`() {
            lexer.parsing("")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.UNKNOWN))
        }

        @Test
        fun `whitespace skips`() {
            lexer.parsing(" \t\n")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.UNKNOWN))
        }

        @Test
        fun `whitespace skips before word`() {
            lexer.parsing(" nonsense")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "nonsense"))
        }

        @Test
        fun `does not recognize nonsense`() {
            lexer.parsing("nonsense")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "nonsense"))
        }

        @Test
        fun `stops at whitespace`() {
            lexer.parsing("nonsense ")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "nonsense"))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))
        }

        @Test
        fun `recognizes ANDs`() {
            lexer.parsing("AND")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.AND))
            lexer.parsing("anD")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.AND))
            lexer.parsing("&")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.AND))
        }

        @Test
        fun `recognizes ORs`() {
            lexer.parsing("OR")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.OR))
            lexer.parsing("Or")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.OR))
            lexer.parsing("|")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.OR))
        }

        @Test
        fun `recognizes NOTs`() {
            lexer.parsing("NOT")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.NOT))
            lexer.parsing("noT")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.NOT))
            lexer.parsing("!")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.NOT))
        }

        @Test
        fun `recognizes parenthesis`() {
            lexer.parsing("(")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.LEFT_PAREN))
            lexer.parsing("((")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.LEFT_PAREN))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.LEFT_PAREN))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))


            lexer.parsing(")")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.RIGHT_PAREN))
            lexer.parsing("))")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.RIGHT_PAREN))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.RIGHT_PAREN))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))
        }

        @Test
        fun `relative periods`() {
            lexer.parsing("=y")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "=y"))
            lexer.parsing("=m")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "=m"))
            lexer.parsing(">y")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, ">y"))
            lexer.parsing(">m")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, ">m"))
        }

        @Test
        fun `words returned in lower case`() {
            lexer.parsing("HEY THERE")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "hey"))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "there"))
        }

        @Test
        fun `recognizes word`() {
            lexer.parsing("It is   not today")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "it"))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "is"))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.NOT))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "today"))
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.EOL))
        }

        @Test
        fun `recognizes word - with special Characters`() {
            lexer.parsing("one_word")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "one_word"))
            lexer.parsing("one.word")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "one.word"))
            lexer.parsing("one-word")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "one-word"))
            lexer.parsing("one-word.along_with.this")
            assertThat(lexer.pop()).isEqualTo(Token(TokenType.WORD, "one-word.along_with.this"))
        }
    }
}