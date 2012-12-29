(ns plosure.forclojure.longestincsub)


(def lis1
  (fn [coll]
    (vec (first
           (filter #(< 1 (count %))
             (sort-by #(- (count %))
               (reduce (fn [coll b]
                         (let [cs (vec (butlast coll))
                               c (last coll)
                               a (last c)]
                           (if (or (nil? a) (< a b))
                             (conj cs (conj c b))
                             (conj coll [b])))) [[]] coll)))))))

(def lis lis1)

(println "1: " [1 0 1 2 3 0 4 5])
(println "1: " (lis [1 0 1 2 3 0 4 5]))
(println "2: " [5 6 1 3 2 7])
(println "2: " (lis [5 6 1 3 2 7]))
(println "3: " [2 3 3 4 5])
(println "3: " (lis [2 3 3 4 5]))
(println "4: " [7 6 5 4])
(println "4: " (lis [7 6 5 4]))

(println (= (lis [1 0 1 2 3 0 4 5]) [0 1 2 3]))
(println (= (lis [5 6 1 3 2 7]) [5 6]))
(println (= (lis [2 3 3 4 5]) [3 4 5]))
(println (= (lis [7 6 5 4]) []))

(defn -main []
  (println "Longest Increasing Subsequence"))