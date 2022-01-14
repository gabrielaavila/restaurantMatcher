package com.gabrielaavila.restaurantMatcher;

import com.gabrielaavila.restaurantMatcher.domain.Trie;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantMatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantMatcherApplication.class, args);

		Trie restaurantsTrie = new Trie();
		restaurantsTrie.insert("Banana", 0);
		String word = "banana";
		String word2 = "maca";

		System.out.println("Check if "+ word+" exists in trie: "+ restaurantsTrie.searchPrefix(word2));
	}

}
