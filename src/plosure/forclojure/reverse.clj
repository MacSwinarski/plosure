(ns plosure.forclojure.reverse)


(def ri1
  (fn [coll n]
    (sort-by first
      (for [i (range n)]
        (for [j coll :when (= i (mod j n))]
          j)))))

(def maximental #(apply map list (partition %2 %1)))

(println "partition: " (partition 2 [1 2 3 4 5 6]))
(println "apply map list partition: " (apply map list (partition 2 [1 2 3 4 5 6])))


(def ri maximental)

(println (ri [1 2 3 4 5 6] 2))
(println (ri (range 9) 3))

(println "1: " (= (ri [1 2 3 4 5 6] 2) '((1 3 5) (2 4 6))))
(println "2: " (= (ri (range 9) 3) '((0 3 6) (1 4 7) (2 5 8))))
(println "3: " (= (ri (range 10) 5) '((0 5) (1 6) (2 7) (3 8) (4 9))))


(defn -main []
  (println "Reverse Interleave"))