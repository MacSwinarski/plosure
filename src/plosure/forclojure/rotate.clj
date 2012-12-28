(ns plosure.forclojure.rotate)

(def r1
  (fn rotate [n coll]
    (cond
      (< n 0) (rotate (inc n) (conj (apply list (butlast coll)) (last coll)))
      (> n 0) (rotate (dec n) (conj (apply vector (rest coll)) (first coll)))
      :default coll)))

(def r2
  (fn [n coll]
    (let [i (mod n (count coll))]
      (concat (drop i coll) (take i coll)))))

(def r r2)

(println "1: " (= (r 2 [1 2 3 4 5]) '(3 4 5 1 2)))
(println "2: " (= (r -2 [1 2 3 4 5]) '(4 5 1 2 3)))
(println "3: " (= (r 6 [1 2 3 4 5]) '(2 3 4 5 1)))
(println "4: " (= (r 1 '(:a :b :c)) '(:b :c :a)))
(println "5: " (= (r -4 '(:a :b :c)) '(:c :a :b)))

(defn -main []
  (println "rotate"))

;maximental
;(fn [n s] (#(concat (drop % s) (take % s)) (mod n (count s))))
