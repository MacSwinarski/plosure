(ns plosure.forclojure.treestables)

(def tt
  (fn [m]
    (into {}
      (for [[k v] m [k2 v2] v] [[k k2] v2]))))

(defn -main []
  (do
    (println "1: " (= (tt '{a {p 1, q 2} b {m 3, n 4}}) '{[a p] 1, [a q] 2 [b m] 3, [b n] 4}))
    (println "2: " (= (tt '{[1] {a b c d} [2] {q r s t u v w x}}) '{[[1] a] b, [[1] c] d, [[2] q] r, [[2] s] t, [[2] u] v, [[2] w] x}))
    (println "3: " (= (tt '{m {1 [a b c] 3 nil}}) '{[m 1] [a b c], [m 3] nil}))))