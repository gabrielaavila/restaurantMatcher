package com.gabrielaavila.restaurantMatcher.domain;

import java.util.Collections;
import java.util.HashSet;

import static java.util.Objects.isNull;

public class Trie {
    private Node root;

    public Trie() {
        root = new Node('\0');
    }

    public void insert(String word, int index) {
        Node curr = root;
        for (int i=0; i < word.length(); i++) {
            char c = Character.toLowerCase(word.charAt(i));
            if (curr.children[c - 'a'] == null) {
                curr.children[c - 'a'] = new Node(c);
            }
            curr = curr.children[c - 'a'];
        }
        curr.isWord = true;
        curr.wordIndices.add(index);
    }

    public HashSet<Integer> searchWordsIndexByPrefix(String word) {
        Node node = getNode(word);
        return depthFirstSearchWords(node, new HashSet<>());
    }

    public boolean searchPrefix(String word) {
        Node node = getNode(word);
        return node != null;
    }

    public boolean searchWord(String word) {
        Node node = getNode(word);
        return node != null && node.isWord;
    }

    private Node getNode(String word) {
        Node curr = root;
        for (int i=0; i < word.length(); i++) {
            char c = Character.toLowerCase(word.charAt(i));
            if (curr.children[c - 'a'] == null) {
                return null;
            }
            curr = curr.children[c - 'a'];
        }
        return curr;
    }

    private HashSet<Integer> depthFirstSearchWords(Node node, HashSet<Integer> results) {
        if (isNull(node)) {
            return results;
        }
        if (node.isWord) {
            results.addAll(node.wordIndices);
        }
        for (Node child: node.children) {
            if (child != null) {
                depthFirstSearchWords(child, results);
            }
        }
        return results;
    }

    class Node {
        char c;
        boolean isWord;
        Node[] children;
        //it will not allow adding duplicated indices into same word search
        HashSet<Integer> wordIndices;

        public Node(char c) {
            this.c = c;
            isWord = false;
            children = new Node[26];
            wordIndices = new HashSet<>();
        }
    }

}
