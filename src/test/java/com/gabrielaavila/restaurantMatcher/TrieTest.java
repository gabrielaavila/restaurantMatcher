package com.gabrielaavila.restaurantMatcher;

import com.gabrielaavila.restaurantMatcher.domain.Trie;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TrieTest {
    private final Trie trie = new Trie();
    private static final String WORD_1 = "banana";
    private static final String WORD_2 = "bananas";
    private static final String WORD_3 = "camiseta";
    private static final String WORD_4 = "camisa";

    @Test
    public void testWhenInsertWordIntoTrieAndSearchItThenReturnWord() {
        trie.insert(WORD_1, 0);
        assertTrue(trie.searchWord(WORD_1));
    }

    @Test
    public void testWhenInsertMultipleWordsIntoTrieAndSearchThemThenAllWordsFound() {
        trie.insert(WORD_1, 0);
        trie.insert(WORD_2, 1);
        trie.insert(WORD_3, 2);
        trie.insert(WORD_4, 3);
        assertTrue(trie.searchWord(WORD_1));
        assertTrue(trie.searchWord(WORD_2));
        assertTrue(trie.searchWord(WORD_3));
        assertTrue(trie.searchWord(WORD_4));
    }

    @Test
    public void testWhenSearchWordByPrefixThenReturnCorrectly() {
        trie.insert(WORD_2, 1);
        trie.insert(WORD_3, 2);
        trie.insert(WORD_4, 3);
        assertFalse(trie.searchWord(WORD_1));
        assertTrue(trie.searchPrefix(WORD_1));
    }

    @Test
    public void testWhenSearchWithCaseInsensitiveThenReturnCorrectly() {
        trie.insert(WORD_2, 1);
        trie.insert(WORD_3, 2);
        trie.insert(WORD_4, 3);
        HashSet<Integer> allIndex = trie.searchWordsIndexByPrefix(WORD_2.toUpperCase());
        assertThat(allIndex).isNotEmpty()
                .hasSize(1)
                .containsExactlyInAnyOrder(1);
    }

    @Test
    public void testWhenSearchByPrefixWithMultipleMatchesThenReturnAllWordsMatched() {
        trie.insert(WORD_1, 0);
        trie.insert(WORD_2, 1);
        HashSet<Integer> allIndex = trie.searchWordsIndexByPrefix(WORD_1);
        assertThat(allIndex).isNotEmpty()
                .hasSize(2)
                .containsExactlyInAnyOrder(0,1);
    }

    @Test
    public void testWhenSearchByPrefixWordsWithSameIndexThenDoesNotReturnDuplicatedIndexes() {
        trie.insert(WORD_1, 1);
        trie.insert(WORD_2, 1);
        HashSet<Integer> allIndex = trie.searchWordsIndexByPrefix(WORD_1);
        assertThat(allIndex).isNotEmpty()
                .hasSize(1)
                .containsExactlyInAnyOrder(1);
    }

    @Test
    public void testSearchWordsBySuffixThenDoesNotReturnResults() {
        trie.insert(WORD_2, 1);
        trie.insert(WORD_3, 2);
        trie.insert(WORD_4, 3);
        HashSet<Integer> allIndex = trie.searchWordsIndexByPrefix("ana");

        assertThat(allIndex).isEmpty();
    }
}