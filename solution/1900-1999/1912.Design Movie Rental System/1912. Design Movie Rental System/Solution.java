import java.util.*;

class MovieRentingSystem {
    // Store unrented movies: movieId -> TreeSet of (price, shopId) pairs
    private Map<Integer, TreeSet<int[]>> unrented;
  
    // Store price lookup: "shopId,movieId" -> price
    private Map<String, Integer> shopAndMovieToPrice;
  
    // Store currently rented movies: TreeSet of (price, shopId, movieId) triples
    private TreeSet<int[]> rented;
  
    public MovieRentingSystem(int n, int[][] entries) {
        /**
         * Initialize the movie renting system.
         * 
         * @param n Number of shops
         * @param entries Array of [shop, movie, price] entries
         */
      
        // Initialize data structures
        this.unrented = new HashMap<>();
        this.shopAndMovieToPrice = new HashMap<>();
      
        // Custom comparator for unrented movies: sort by price, then by shop ID
        Comparator<int[]> unrentedComparator = (a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]); // Compare by price
            return Integer.compare(a[1], b[1]); // Compare by shop ID
        };
      
        // Custom comparator for rented movies: sort by price, then shop ID, then movie ID
        this.rented = new TreeSet<>((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]); // Compare by price
            if (a[1] != b[1]) return Integer.compare(a[1], b[1]); // Compare by shop ID
            return Integer.compare(a[2], b[2]); // Compare by movie ID
        });
      
        // Process initial entries
        for (int[] entry : entries) {
            int shop = entry[0];
            int movie = entry[1];
            int price = entry[2];
          
            // Add to unrented collection
            unrented.computeIfAbsent(movie, k -> new TreeSet<>(unrentedComparator))
                    .add(new int[]{price, shop});
          
            // Store price for quick lookup
            shopAndMovieToPrice.put(shop + "," + movie, price);
        }
    }
  
    public List<Integer> search(int movie) {
        /**
         * Search for the 5 cheapest shops that have an unrented copy of the given movie.
         * 
         * @param movie The movie ID to search for
         * @return List of up to 5 shop IDs with cheapest prices
         */
      
        List<Integer> result = new ArrayList<>();
      
        // Check if this movie exists in any shop
        if (!unrented.containsKey(movie)) {
            return result;
        }
      
        // Get up to 5 shops with cheapest prices
        int count = 0;
        for (int[] entry : unrented.get(movie)) {
            if (count >= 5) break;
            result.add(entry[1]); // Add shop ID
            count++;
        }
      
        return result;
    }
  
    public void rent(int shop, int movie) {
        /**
         * Rent a movie from a specific shop.
         * 
         * @param shop The shop ID
         * @param movie The movie ID to rent
         */
      
        // Get the price for this shop-movie combination
        int price = shopAndMovieToPrice.get(shop + "," + movie);
      
        // Remove from unrented inventory
        unrented.get(movie).remove(new int[]{price, shop});
      
        // Add to rented inventory
        rented.add(new int[]{price, shop, movie});
    }
  
    public void drop(int shop, int movie) {
        /**
         * Return a rented movie to a specific shop.
         * 
         * @param shop The shop ID
         * @param movie The movie ID to return
         */
      
        // Get the price for this shop-movie combination
        int price = shopAndMovieToPrice.get(shop + "," + movie);
      
        // Add back to unrented inventory
        unrented.get(movie).add(new int[]{price, shop});
      
        // Remove from rented inventory
        rented.remove(new int[]{price, shop, movie});
    }
  
    public List<List<Integer>> report() {
        /**
         * Get the 5 cheapest rented movies.
         * 
         * @return List of up to 5 [shop_id, movie_id] pairs
         */
      
        List<List<Integer>> result = new ArrayList<>();
      
        // Get up to 5 cheapest rented movies
        int count = 0;
        for (int[] entry : rented) {
            if (count >= 5) break;
            result.add(Arrays.asList(entry[1], entry[2])); // Add [shop_id, movie_id]
            count++;
        }
      
        return result;
    }
}
